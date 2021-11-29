import axios from "axios";
import { FormTagsPlugin } from "bootstrap-vue";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "User",
  data() {
    return {
      users: [],
      newUser: "",
      userError: "",
      response: "",
      responseStatus: null,
      results: [],
      visible: false,
      currentUser: this.$store.state.currentUser,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
      user: null,
    };
  },

  methods: {
    showModal: function () {
      this.visible = true;
      this.userError = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    goBack() {
      this.$router.push("/edit");
    },

    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },

    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue("newPassword")) {
        callback("The two passwords do not match!");
      } else {
        callback();
      }
    },

    validateToNextPassword(rule, value, callback) {
      const form = this.form;
      if (value && this.confirmDirty) {
        form.validateFields(["confirm"], { force: true });
      }
      callback();
    },

    changePassword: function (username, newPassword, currentPassword) {
      console.log(username);
      AXIOS.put(
        "/api/user/change_password/?username=" +
          this.currentUser.username +
          "&newPass=" +
          newPassword +
          "&oldPass=" +
          currentPassword
      )
        .then((response) => {
          this.users.filter((user) => {
            return user.username;
          });
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.userError = errorMsg;
        });
    },

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.user = values;
          console.log("any object", this.user.email);
          AXIOS.put(
            "/api/user/change_password/?username=" +
              this.currentUser.username +
              "&newPass=" +
              this.user.newPassword +
              "&oldPass=" +
              this.user.password
          )
            .then((res) => {
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus === 200) {
                this.$store.commit("changeUser", res.data);
                this.userError = "";
                this.response = "Password changed";
                //this.$router.push("EditAccountDetails");
              }

              return res.status;
            })
            .catch((e) => {
              this.response = "";
              this.visible = true;
              var errorMsg = e.response.data;
              this.userError = errorMsg;
            });
        }
      });
    },
  },
};

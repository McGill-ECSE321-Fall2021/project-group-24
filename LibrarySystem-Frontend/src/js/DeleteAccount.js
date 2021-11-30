import axios from "axios";
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
      response: [],
      responseStatus: null,
      results: [],
      visible: false,
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
      user: null,
    };
  },

  methods: {
    showModal: function () {
      this.visible = true;
      this.error = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    goBack() {
      this.$router.push("/edit");
    },

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.user = values;
          console.log("any object", this.user.email);
          AXIOS.delete(
            "/api/patron/delete_patron/" +
              this.user.id +
              "/?currentUserId=" +
              this.currentUser.idNum
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;

              if (this.responseStatus == 200) {
                this.visible = true;
                this.responseStatus = res.status;

                window.sessionStorage.setItem(
                  "currentUser",
                  JSON.stringify(res.data)
                );

                this.$router.push("/");
              }
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.userError = errorMsg;
            });
        }
      });
    },
  },
};

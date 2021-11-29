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
  name: "users",
  data() {
    return {
      users: [],
      newUser: "",
      userError: "",
      response: [],
      responseStatus: null,
      results: [],
      visible: false,
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
    // blured-out option for password
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    // 2 methods below verify that entered passwords are identical
    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue("password")) {
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

    handleWebsiteChange(value) {
      let autoCompleteResult;
      if (!value) {
        autoCompleteResult = [];
      } else {
        autoCompleteResult = [".com", ".org", ".net"].map(
          (domain) => `${value}${domain}`
        );
      }
      this.autoCompleteResult = autoCompleteResult;
    },
    // sends http post request to backend to create a librarian when "Add Librarian" is pressed
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.user = values;
          console.log("any object", this.user.email);
          AXIOS.post(
            "/api/librarians/create/?currentUserId=admin&" +
            "&firstName=" +
            this.user.firstName +
            "&lastName=" +
            this.user.lastName +
            "&address=" +
            this.user.address +
            "&email=" +
            this.user.email +
            "&username=" +
            this.user.username +
            "&password=" +
            this.user.password 
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              console.log(res.data);

              console.log("THEN: ");
              // if new librarian is successfully created, go back to previous web page
              if (this.responseStatus == 200) {
                this.$router.replace({ name: "ViewLibrarians" });
              }
              return res.status;
            })
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              this.userError = errorMsg;
            });
        }
      });
    },
  },
};

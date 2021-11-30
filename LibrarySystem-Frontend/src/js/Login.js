import axios from "axios";
// author: Selena
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
    // initializing variables to return
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
    // modal to display error/message
    showModal: function () {
      this.visible = true;
      this.userError = "";
    },
    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },
    // collecting form inputs and calling the login function from the controller
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.user = values;
          // post request
          AXIOS.post(
            "/api/user/login" +
              "?username=" +
              this.user.username +
              "&password=" +
              this.user.password
          )
            .then((res) => {
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus == 200) {
                // if successful, set the current user of the session to be the user returned from the log in method
                window.sessionStorage.setItem(
                  "currentUser",
                  JSON.stringify(res.data)
                );
                // sends the user to the home page
                this.$router.replace({ name: "Homepage" });
                location.reload();
              }
              return res.status;
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

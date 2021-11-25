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

    login: function (username, password) {
      console.log(username);
      AXIOS.post(
        "/api/user/login/" + "?username=" + username + "&password=" + password
      )
        .then((response) => {
          this.users = this.users.filter((user) => {
            return user.username != username;
          });
          this.results = this.users;
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
          console.log("Received values of form: ", values);
          this.user = values;

          AXIOS.post(
            "/api/user/login" +
              "?username=" +
              this.user.username +
              "&password=" +
              this.user.password

          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              console.log(res.data);
              console.log("HI");
              this.$store.commit("changeUser", res.data);
              console.log("THEN: ");
              if(this.responseStatus==200){
                window.location.href = "http://127.0.0.1:8087/#/"

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

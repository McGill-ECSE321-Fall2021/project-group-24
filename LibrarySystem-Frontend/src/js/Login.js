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
  created: function () {
    console.log(JSON.parse(sessionStorage.getItem("currentUser")));
  },
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
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus == 200) {
                window.sessionStorage.setItem(
                  "currentUser",
                  JSON.stringify(res.data)
                );
                this.$router.replace({ name: "Homepage" });
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

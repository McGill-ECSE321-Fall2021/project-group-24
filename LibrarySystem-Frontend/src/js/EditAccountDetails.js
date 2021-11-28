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
      currentUser: this.$store.state.currentUser,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
      user: null,
      response: "",
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

    goHome() {
      this.$router.push("/");
    },

    goToChangePassword() {
      this.$router.push("/changePassword");
    },

    goToDeleteAccount() {
      this.$router.push("/deleteAccount");
    },

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.user = values;

          AXIOS.put(
            "/api/user/change_name/?username=" +
              this.currentUser.username +
              "&firstName=" +
              this.user.firstname +
              "&lastName=" +
              this.user.lastname
          )
            .then((res) => {
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus == 200 || this.responseStatus == 201) {
                AXIOS.put(
                  "/api/user/change_email/?username=" +
                    this.currentUser.username +
                    "&email=" +
                    this.user.emailinput
                )
                  .then((res) => {
                    this.visible = true;
                    this.responseStatus = res.status;
                    console.log(res.data);
                    console.log("THEN: ");
                    if (
                      this.responseStatus == 200 ||
                      this.responseStatus === 201
                    ) {
                      AXIOS.put(
                        "/api/user/change_address/?username=" +
                          this.currentUser.username +
                          "&address=" +
                          this.user.addressinput
                      )
                        .then((res) => {
                          this.visible = true;
                          this.responseStatus = res.status;
                          this.$store.commit("changeUser", res.data);
                          if (
                            this.responseStatus == 200 ||
                            this.responseStatus === 201
                          ) {
                            this.response = "User Information saved";
                            //this.$router.push("EditAccountDetails");
                          }
                          return res.status;
                        })
                        .catch((e) => {
                          console.log("CATCH: ");
                          this.visible = true;
                          console.log("line 109");
                          this.userError = e.response.data;
                        });
                    }
                  })
                  .catch((e) => {
                    console.log("CATCH: ");
                    this.visible = true;
                    var errorMsg = e.response.data;
                    console.log("line 118");
                    this.userError = errorMsg;
                  });
              }
            })
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              console.log(e.response.data);
              console.log("line 126");
              this.userError = errorMsg;
            });
        }
      });
    },
  },
};

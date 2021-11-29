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

    createPatron: function (firstName, lastName, isResident, address) {
      if ((isResident.value = "isResident")) {
        isResident = "true";
      } else if ((isResident.value = "isNotResident")) {
        isResident = "false";
      }

      AXIOS.post(
        "/api/patron/create_patron_irl/?first=" +
          firstName +
          "&last=" +
          lastName +
          "&isResident=" +
          isResident +
          "&address=" +
          address
      )
        .then((response) => {
          this.users = this.users.filter((user) => {
            return user.firstName != firstName;
          });
          this.results = this.users;
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.roomError = errorMsg;
        });
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

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          this.user = values;

          AXIOS.post(
            "/api/patron/create_patron_irl/?first=" +
              this.user.firstName +
              "&last=" +
              this.user.lastName +
              "&isResident=" +
              this.user.isResident +
              "&address=" +
              this.user.address
          )
            .then((res) => {
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus == 200) {
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

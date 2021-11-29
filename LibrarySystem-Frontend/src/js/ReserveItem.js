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
  name: "ReserveItem",
  data() {
    return {
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      ...this.$route.params,
      form: this.$form.createForm(this, { name: "coordinated" }),
      error: "",
      modalVisible: false,
      response: "",
    };
  },
  created: function () {},

  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          AXIOS.post(
            "api/itemReservations/create_reservation/?currentUserId=" +
              this.currentUser.idNum +
              "&idNum=" +
              (this.currentUser.isPatron
                ? this.currentUser.idNum
                : values.idNum) +
              "&itemNumber=" +
              this.item.itemNumber +
              "&isCheckedOut=false"
          )
            .then((res) => {
              this.response = "Successfully created Item resevation for patron";
              this.modalVisible = true;
              this.error = "";
            })
            .catch((e) => {
              console.log("failure");
              this.response = "";
              this.error = e.response.data;
              this.modalVisible = true;
            });
        }
      });
    },
  },
};

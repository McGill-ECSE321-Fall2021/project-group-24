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
  name: "CheckoutItemForPatron",
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
          AXIOS.put(
            "api/itemReservations/checkout_item/" +
              this.item.itemNumber +
              "/byPatron/" +
              values.idNum +
              "?currentUserId=" +
              this.currentUser.idNum
          )
            .then((res) => {
              this.response = "Successfully checked out for patron";
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

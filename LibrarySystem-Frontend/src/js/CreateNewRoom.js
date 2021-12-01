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
  name: "rooms",
  data() {
    return {
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      rooms: [],
      newRoom: "",
      roomError: "",
      response: [],
      responseStatus: null,
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
      room: [],
    };
  },

  methods: {
    //method to show the alert message
    showModal: function () {
      this.visible = true;
      this.roomError = "";
    },
    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },
    //when the user clicks on the create button
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.room = values;
          console.log("any object", this.room.capacity);
          AXIOS.post(
            "/api/rooms/create_room/" +
              this.room.roomNum +
              "?currentUserId=" +
              this.currentUser.idNum +
              "&capacity=" +
              this.room.capacity
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              this.room.roomNum = "";
              this.room.capacity = "";
              this.room.currentUserId = "";
              return res.status;
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.roomError = errorMsg;
            });
        }
      });
    },
  },
};

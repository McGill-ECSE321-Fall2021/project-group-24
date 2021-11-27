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
  name: "updateRoom",
  data() {
    return {
      currentUser: this.$store.state.currentUser,
      ...this.$route.params,
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
      error: "",
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

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.room.capacity = values.capacity;
          console.log("any object", this.room.roomNum);
          AXIOS.put(
            "/api/rooms/update_room/" +
              this.room.roomNum +
              "?currentUserId=" +
              this.currentUser.idNum +
              "&newCapacity=" +
              parseInt(this.room.capacity)
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.response = "Room Updated!";
              this.error = "";
              this.room.roomNum = "";
              this.room.capacity = "";
              this.room.currentUserId = "";
              this.$router.replace({ name: "BrowseAllRooms" });
            })
            .catch((e) => {
              console.log(e.response.data);
              this.visible = true;
              var errorMsg = e.response.data;
              this.error = errorMsg;
              this.response = "";
            });
        }
      });
    },
  },
};

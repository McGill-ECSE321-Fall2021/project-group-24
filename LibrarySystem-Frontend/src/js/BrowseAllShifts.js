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
  name: "shifts",
  data() {
    return {
      hours: [],
      currentUser: this.$store.state.currentUser,
    };
  },
  created: function () {
    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      this.hours = res.data;
      console.log(res.data);
    });
  },
  methods: {
    showModal: function () {
      this.visible = true;
      this.roomError = "";
    },
    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },
    search: function (query) {
      this.results = [];
      if (query.length == 0) {
        this.results = this.rooms;
      } else {
        this.results = this.rooms.filter((room) => {
          if (room.roomNum.toLowerCase().includes(query.toLowerCase())) {
            return true;
          }
        });
      }
    },
  },
};

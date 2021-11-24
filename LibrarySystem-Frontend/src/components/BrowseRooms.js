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
      rooms: [],
      newRoom: "",
      roomError: "",
      response: [],
      results: [],
      visible: false,
    };
  },
  created: function () {
    AXIOS.get("/api/rooms/view_all_rooms")
      .then((response) => {
        this.rooms = response.data;
        this.results = this.rooms;
      })
      .catch((e) => {
        this.visible = true;
        this.roomError = e;
        console.log(e);
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
    deleteRoom: function (roomNum, currentUserId) {
      console.log(roomNum);
      AXIOS.delete(
        "/api/rooms/delete_room/" + roomNum + "?currentUserId=" + currentUserId
      )
        .then((response) => {
          this.rooms = this.rooms.filter((room) => {
            return room.roomNum != roomNum;
          });
          this.results = this.rooms;
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.roomError = errorMsg;
        });
    },
  },
};

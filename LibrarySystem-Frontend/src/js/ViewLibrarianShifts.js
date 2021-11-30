import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});
/**
 * Returns the librarian's shifts so they can be displayed
 * */
export default {
  name: "ViewLibrarianShifts",
  data() {
    return {
      shifts: [],
      ...this.$route.params, // receives the librarian that has been "sent" from ViewLibrains webpage
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
    };
  },

  created: function () {
    console.log(JSON.parse(sessionStorage.getItem("currentUser")));

    AXIOS.get("api/shift/view_librarian_shifts?currentUserId=admin&librarianId="+
      this.librarian.idNum
    ).then((res) => {
      this.shifts = res.data;
      console.log(res.data);
    });
  },
};
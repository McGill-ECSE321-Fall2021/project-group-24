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
 * Returns the library hours so that they can be displayed on the homepage 
 * */ 
export default {
  name: "Homepage",
  data() {
    return { hours: [] };
  },
  created: function () {

    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      this.hours = res.data;
      console.log(res.data);
    });
  },
};
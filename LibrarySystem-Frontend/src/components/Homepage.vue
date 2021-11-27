<template>
  <div class="Homepage">
    <h3>Library Opening Hours</h3>
    <h5 v-if="hours.length == 0">No library hours</h5>
    <div v-for="hour in hours" :key="hour.dayOfWeek">
      <h5>
        {{ hour.dayOfWeek[0] + hour.dayOfWeek.slice(1).toLowerCase() }}:
        {{ hour.startTime.substring(0, 5) }} -
        {{ hour.endTime.substring(0, 5) }}
      </h5>
    </div>
  </div>
</template>

<script>
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
  name: "Homepage",
  data() {
    return { hours: [] };
  },
  created: function () {
    var daysOfWeek = [
      "MONDAY",
      "TUESDAY",
      "WEDNESDAY",
      "THURSDAY",
      "FRIDAY",
      "SATURDAY",
      "SUNDAY",
    ];
    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      this.hours = res.data.sort((day1, day2) => {
        if (
          daysOfWeek.indexOf(day1.dayOfWeek) >
          daysOfWeek.indexOf(day2.dayOfWeek)
        ) {
          return 1;
        } else {
          return -1;
        }
      });

      console.log(res.data);
    });
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: bold;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 10px;
  padding: 10px;
}
a {
  color: #42b987;
}
</style>

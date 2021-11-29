import axios from "axios";
var config = require("../../config");
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import moment from "moment";

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  components: { VueCal },
  name: "shifts",
  data() {
    return {
      libraryHours: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {} },
      events: [],
      currentUser: this.$store.state.currentUser,
    };
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
      this.daysToHide = [1, 2, 3, 4, 5, 6, 7];
      this.libraryHours = {
        1: {},
        2: {},
        3: {},
        4: {},
        5: {},
        6: {},
        7: {},
      };
      for (var i = 0; i < res.data.length; i++) {
        //starttime
        var startTime = res.data[i].startTime;
        var startTimeArray = startTime.split(":");
        var startTimeMinutes = +startTimeArray[0] * 60 + +startTimeArray[1];
        //endtime
        var endTime = res.data[i].endTime;
        var endTimeArray = endTime.split(":");
        var endTimeMinutes = +endTimeArray[0] * 60 + +endTimeArray[1];

        this.libraryHours[daysOfWeek.indexOf(res.data[i].dayOfWeek) + 1] = {
          from: startTimeMinutes,
          to: endTimeMinutes,
          class: "business-hours",
        };
      }
    });
    console.log("hi");
    AXIOS.get(
      "api/shift/view_all_shifts/?currentUserId=" + this.currentUser.idNum
    )
      .then((res) => {
        var todayDay = new Date().getDay();
        var librarians = [];
        var librarianStyles = {};
        this.events = [];
        for (var i = 0; i < res.data.length; i++) {
          var date = new Date();
          date.setDate(date.getDate());

          date.setDate(
            date.getDate() +
              daysOfWeek.indexOf(res.data[i].dayOfWeek) -
              todayDay +
              1
          );
          if (!librarians.includes(res.data[i].librarianId)) {
            librarians.push(res.data[i].librarianId);
          }
          this.events = [
            ...this.events,
            {
              start:
                moment(date).format("YYYY-MM-DD") +
                " " +
                res.data[i].startTime.substring(0, 5),
              end:
                moment(date).format("YYYY-MM-DD") +
                " " +
                res.data[i].endTime.substring(0, 5),
              title: res.data[i].librarianId,
              deletable: false,
              resizable: false,
              draggable: false,
              class:
                "librarian" + (librarians.indexOf(res.data[i].librarianId) % 7),
            },
          ];
          console.log(this.events[i - 1]);
        }
        console.log(this.events);
      })
      .catch((e) => {
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
  },
};

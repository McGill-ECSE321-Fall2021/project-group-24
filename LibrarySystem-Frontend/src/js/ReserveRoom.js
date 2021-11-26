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
  name: "ReserveRoom",
  data() {
    return {
      currentUser: this.$store.state.currentUser,
      ...this.$route.params,
      form: this.$form.createForm(this, { name: "coordinated" }),
      error: "",
      modalVisible: false,
      response: "",
      today: new Date().toJSON().slice(0, 10), //today
      daysToHide: [1, 2, 3, 4, 5, 6, 7],
      libraryHours: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {} },
      events: [],
      startTime: "",
      endTime: "",
    };
  },
  created: function () {
    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      console.log(res.data);
      var daysOfWeek = [
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
        "SUNDAY",
      ];
      for (var i = 0; i < res.data.length; i++) {
        this.daysToHide.splice(daysOfWeek.indexOf(res.data[i].dayOfWeek), 1);
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
    AXIOS.get(
      "api/roombookings/privateview_roombookings/room/" + this.room.roomNum
    )
      .then((res) => {
        // events: [
        //     {
        //       start: '2018-11-20 14:00',
        //       end: '2018-11-20 17:30',
        //       title: 'Boring event',
        //       content: '<i class="icon material-icons">block</i><br>I am not draggable, not resizable and not deletable.',
        //       class: 'blue-event',
        //       deletable: false,
        //       resizable: false,
        //       draggable: false
        //     },
        for (var i = 0; i < res.data.length; i++) {
          this.events = [
            ...this.events,
            {
              start:
                res.data[i].date + " " + res.data[i].startTime.substring(0, 5),
              end: res.data[i].date + " " + res.data[i].endTime.substring(0, 5),
              title: "booked",
              deletable: false,
              resizable: false,
              draggable: false,
            },
          ];
        }
      })
      .catch((e) => {
        console.log(e);
      });
  },
  methods: {
    moment,
    changeStartTime: function (newStartTime) {
      this.startTime = newStartTime;
    },
    changeEndTime: function (newEndTime) {
      this.endTime = newEndTime;
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          var startTime = new Date(this.startTime);
          startTime = startTime.getHours() + ":" + startTime.getMinutes();
          var endTime = new Date(this.endTime);
          endTime = endTime.getHours() + ":" + endTime.getMinutes();
          AXIOS.post(
            "api/roombookings/add_roombooking/?currentUserId=" +
              this.currentUser.idNum +
              "&idNum=" +
              (this.currentUser.isPatron
                ? this.currentUser.idNum
                : values.idNum) +
              "&roomNum=" +
              this.room.roomNum +
              "&date=" +
              this.date +
              "&startTime=" +
              startTime +
              "&endTime=" +
              endTime
          )
            .then((res) => {
              this.response = "Successfully created Room Booking for patron";
              this.modalVisible = true;
              this.error = "";
            })
            .catch((e) => {
              this.response = "";
              this.error = e.response.data;
              this.modalVisible = true;
            });
        }
      });
    },
    disabledEndDate(endValue) {
      const startValue = new Date();
      startValue.setDate(startValue.getDate() - 1);
      return startValue.valueOf() > endValue.valueOf();
    },
    changeStartTime: function (startTime) {
      this.startTime = startTime;
    },
    changeEndTime: function (endTime) {
      this.endTime = endTime;
    },
    changeDate: function (newDate) {
      this.date = newDate;
    },
  },
};

import axios from "axios";
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import moment from "moment";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  components: { VueCal },
  name: "roombookings",
  data() {
    return {
      today: new Date().toJSON().slice(0, 10),
      confirmLoading: false,
      loading: true,
      visible: false, // the modify booking model
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: this.$store.state.currentUser,
      form: this.$form.createForm(this, { name: "coordinated" }),
      libraryHours: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {} },
      daysToHide: [1, 2, 3, 4, 5, 6, 7],
      libraryHours: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {} },
      startTime: "",
      endTime: "",
      roombookingResults: [],
      roomResults: [],
    };
  },
  created: async function () {
    if (this.currentUser.isPatron) {
      await AXIOS.get(
        "api/roombookings/view_roombookings/patron/" +
          this.currentUser.idNum +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
          console.log("REPONSE");
          console.log(response.data);
          this.roombookings = response.data;
          this.roombookingResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    } else {
      await AXIOS.get(
        "api/roombookings/view_roombookings" +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
          console.log("HEELELLELELE");
          console.log(response.data);
          this.roombookings = response.data;
          this.roombookingResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    }
  },
  methods: {
    moment,
    changeStartTime: function (newStartTime) {
      this.startTime = newStartTime;
    },
    changeEndTime: function (newEndTime) {
      this.endTime = newEndTime;
    },
    showModal(roomNum) {
      AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
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
        console.log(res.data);
      });
      AXIOS.get("api/roombookings/privateview_roombookings/room/" + roomNum)
        .then((res) => {
          console.log("GETTING ROOOOM BOOKINGS");
          for (var i = 0; i < res.data.length; i++) {
            this.events = [
              ...this.events,
              {
                start:
                  res.data[i].date +
                  " " +
                  res.data[i].startTime.substring(0, 5),
                end:
                  res.data[i].date + " " + res.data[i].endTime.substring(0, 5),
                title: "booked",
                deletable: false,
                resizable: false,
                draggable: false,
              },
            ];
          }
          console.log(res.data);
        })
        .catch((e) => {
          console.log(e);
        });
      this.visible = true;
    },
    handleCancel(e) {
      console.log("Clicked cancel button");
      this.visible = false;
    },
    handleSubmit(timeSlotId, roomNum) {
      this.form.validateFields((err, values) => {
        if (!err) {
          var startTime = new Date(this.startTime);
          startTime =
            (startTime.getHours() < 10
              ? "0" + startTime.getHours()
              : startTime.getHours()) +
            ":" +
            (startTime.getMinutes() == 0 ? "00" : startTime.getMinutes());
          var endTime = new Date(this.endTime);
          endTime =
            (endTime.getHours() < 10
              ? "0" + endTime.getHours()
              : endTime.getHours()) +
            ":" +
            (endTime.getMinutes() == 0 ? "00" : endTime.getMinutes());
          var date = this.date;
          console.log(timeSlotId);
          console.log(roomNum);
          console.log(startTime);
          console.log(endTime);
          console.log(date);
          AXIOS.put(
            "/api/roombookings/update_roombooking" +
              "?currentUserId=" +
              this.currentUser.idNum +
              "&timeSlotId=" +
              timeSlotId +
              "&newDate=" +
              date +
              "&newStartTime=" +
              startTime +
              "&newEndTime=" +
              endTime +
              "&newRoomNum=" +
              roomNum
          )
            .then((res) => {
              console.log("UPDATING");
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
    search: function (query) {
      this.roombookingResults = [];
      this.roomResults = [];
      if (query.length == 0) {
        this.roombookingResults = this.roombookings;
        this.roomResults = this.rooms;
      } else {
        this.roombookingResults = this.roombookings.filter(
          (roombooking, index) => {
            if (roombooking.idNum.toLowerCase().includes(query.toLowerCase())) {
              this.roomResults.push(this.rooms[index]);
              return true;
            }
          }
        );
      }
    },
    cancelroombooking: function (currentUserId, timeSlotId, index) {
      AXIOS.delete(
        "/api/roombookings/delete_roombooking" +
          "?currentUserId=" +
          currentUserId +
          "&timeSlotId=" +
          timeSlotId
      )
        .then((response) => {
          this.visible = true;
          this.response = "Room Booking Cancelled";
          this.error = "";
          this.roombookings.splice(index, 1);
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data;
          this.error = errorMsg;
          this.response = "";
        });
    },
    disabledEndDate(endValue) {
      const startValue = new Date();
      startValue.setDate(startValue.getDate() - 1);
      var answer = startValue.valueOf() > endValue.valueOf();
      if (answer) {
        return answer;
      }
      var day = new Date(endValue).getDay();
      if (day == 0) {
        day = 7;
      }
      if (this.daysToHide.indexOf(day) != -1) {
        return true;
      }
      return answer;
    },
    changeStartTime: function (startTime) {
      this.startTime = startTime;
    },
    changeEndTime: function (endTime) {
      this.endTime = endTime;
    },
    changeDate: function (newDate) {
      this.date = moment(newDate).format("YYYY-MM-DD");
    },
  },
};

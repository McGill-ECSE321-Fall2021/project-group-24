import axios from "axios";
// author: Selena
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
      today: new Date().toJSON().slice(0, 10), // today's date
      confirmLoading: false,
      loading: true,
      visible: false, // the modify booking model
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      form: this.$form.createForm(this, { name: "coordinated" }),
      // variables for the calendar
      libraryHours: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {} },
      daysToHide: [1, 2, 3, 4, 5, 6, 7],
      startTime: "",
      endTime: "",
      date: "",
      // results of the search function
      roombookingResults: [],
      // list of all roombookings
      roombookings: [],
      roomNum: "",
      timeSlotId: "",
    };
  },
  created: async function () {
    // if the current user is a patron, calls the controller method for getting all the roombookings for that patron
    if (this.currentUser.isPatron) {
      await AXIOS.get(
        "api/roombookings/view_roombookings/patron/" +
          this.currentUser.idNum +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
          this.roombookings = response.data;
          this.roombookingResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    } else {
      // if the current user is a librarian, call the controller method for getting all the room bookings
      await AXIOS.get(
        "api/roombookings/view_roombookings" +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
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
    // methods for the date picker and time picker
    changeStartTime: function (newStartTime) {
      this.startTime = newStartTime;
    },
    changeEndTime: function (newEndTime) {
      this.endTime = newEndTime;
    },
    // when the user clicks on modify for a specific room booking
    showModal(roomNum, timeSlotId) {
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
        this.daysToHide = [1, 2, 3, 4, 5, 6, 7]; // hide the days without a library hour from the calendar to reduce clutter
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
          this.daysToHide.splice(
            this.daysToHide.indexOf(
              daysOfWeek.indexOf(res.data[i].dayOfWeek) + 1
            ),
            1
          );

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
      // getting existing room bookings and show them as "events" on the calendar to indicate unavaliability
      AXIOS.get("api/roombookings/privateview_roombookings/room/" + roomNum)
        .then((res) => {
          console.log("GETTING ROOOOM BOOKINGS");
          this.roomNum = roomNum;
          this.timeSlotId = timeSlotId;
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
    // method for modifying room booking
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        // console.log(values);
        if (!err) {
          // getting inputs from the form
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
          // calling controller method with inputs
          AXIOS.put(
            "/api/roombookings/update_roombooking" +
              "?currentUserId=" +
              this.currentUser.idNum +
              "&timeSlotId=" +
              this.timeSlotId +
              "&newDate=" +
              date +
              "&newStartTime=" +
              startTime +
              "&newEndTime=" +
              endTime +
              "&newRoomNum=" +
              this.roomNum
          )
            .then((res) => {
              this.response = "Successfully modified Room Booking";
              this.modalVisible = true;
              this.error = "";
              // reloading the page to show changes
              for (var i = 0; i < this.roombookings.length; i++) {
                if (this.roombookings[i].timeSlotId == this.timeSlotId) {
                  this.roombookings[i].startTime = this.startTime
                    .toString()
                    .substring(15, 23);
                  this.roombookings[i].endTime = this.endTime
                    .toString()
                    .substring(15, 23);
                  this.roombookings[i].date = date;
                }
              }
              this.search("");
            })
            .catch((e) => {
              this.response = "";
              this.error = e.response.data;
              this.modalVisible = true;
            });
        }
      });
    },
    // function for the search bar, users can search room bookings by room number, patrons idnum (for librarinas) and the date of the booking
    search: function (query) {
      this.roombookingResults = [];
      if (query.length == 0) {
        this.roombookingResults = this.roombookings;
      } else {
        this.roombookingResults = this.roombookings.filter(
          (roombooking, index) => {
            if (
              roombooking.roomNum.toLowerCase().includes(query.toLowerCase()) ||
              roombooking.idNum.toLowerCase().includes(query.toLowerCase()) ||
              roombooking.date.toLowerCase().includes(query.toLowerCase())
            ) {
              this.roombookingResults.push(roombooking);
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

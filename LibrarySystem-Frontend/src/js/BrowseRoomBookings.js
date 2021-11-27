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
  name: "roombookings",
  data() {
    return {
      ModalText: "Content of the modal",
      visible: false,
      confirmLoading: false,
      error: "",
      response: "",
      visible: false,
      rooms: [],
      roombookings: [],
      currentUser: this.$store.state.currentUser,
      loading: true,
      roombookingResults: [],
      roomResults: [],
    };
  },
  created: async function () {
    console.log(this.currentUser.idNum);
    if (this.currentUser.isPatron) {
      await AXIOS.get(
        "api/roombookings/view_roombookings/patron/" +
          this.currentUser.idNum +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
          console.log(response.data);
          this.roombookings = response.data;
          this.roombookingResults = response.data;
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
          console.log(response.data);
          this.roombookings = response.data;
          this.roombookingResults = response.data;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    }

    if (this.roombookings.length == 0) {
      this.loading = false;
    } else {
      console.log(this.roombookings[0].timeSlotId);
      var counter = 0;
      await Promise.all(
        this.roombookings.map((roombooking) =>
          AXIOS.get(
            // "api/roombookings/view_roombooking/RoomBooking-015:00:00study1" +
            //   "?currentUserId=" +
            //   this.currentUser.idNum
            "http://localhost:8080/api/roombookings/view_roombooking/RoomBooking-012:30:00study1?currentUserId=admin"
          ).then((response) => {
            console.log("hellllsladlfaf");
            console.log(response.data);
            this.rooms[counter] = { ...response.data };
            counter++;
            if (counter == this.roombookings.length) {
              this.loading = false;
            }
          })
        )
      );
      this.roomResults = this.rooms;
    }
  },
  methods: {
    showModal() {
      this.visible = true;
    },
    handleCancel(e) {
      console.log("Clicked cancel button");
      this.visible = false;
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

    renew: function (
      currentUserId,
      timeSlotId,
      newDate,
      newStartTime,
      newEndTime,
      newRoomNum
    ) {
      AXIOS.put(
        "/api/roombookings/update_roombooking" +
          "?currentUserId=" +
          currentUserId +
          "&timeSlotId=" +
          timeSlotId +
          "&newDate=" +
          newDate +
          "&newStartTime=" +
          newStartTime +
          "&newEndTime=" +
          newEndTime +
          "&newRoomNum=" +
          newRoomNum
      )
        .then((response) => {
          this.visible = true;
          this.$set(this.roombookings, index, response.data);
          this.response = "Room Booking Updated";
          this.error = "";
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data;
          this.error = errorMsg;
          this.response = "";
        });
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
  },
};

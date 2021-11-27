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
  name: "itemreservations",
  data() {
    return {
      error: "",
      response: "",
      visible: false,
      items: [],
      reservations: [],
      currentUser: this.$store.state.currentUser,
      loading: true,
      reservationResults: [],
      itemResults: [],
    };
  },
  created: async function () {
    console.log(this.currentUser.idNum);
    if (this.currentUser.isPatron) {
      await AXIOS.get(
        "api/itemReservations/patron/" +
          this.currentUser.idNum +
          "?currentUserId=" +
          this.currentUser.idNum
      )
        .then((response) => {
          this.reservations = response.data;
          this.reservationResults = response.data;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    } else {
      await AXIOS.get(
        "api/itemReservations/all" + "?currentUserId=" + this.currentUser.idNum
      )
        .then((response) => {
          this.reservations = response.data;
          this.reservationResults = response.data;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    }

    if (this.reservations.length == 0) {
      this.loading = false;
    } else {
      var counter = 0;
      await Promise.all(
        this.reservations.map((reservation) =>
          AXIOS.get("/api/items/" + reservation.itemNumber).then((response) => {
            this.items[counter] = { ...response.data };
            counter++;
            if (counter == this.reservations.length) {
              this.loading = false;
            }
          })
        )
      );
      this.itemResults = this.items;
    }
  },
  methods: {
    search: function (query) {
      this.reservationResults = [];
      this.itemResults = [];
      if (query.length == 0) {
        this.reservationResults = this.reservations;
        this.itemResults = this.items;
      } else {
        this.reservationResults = this.reservations.filter(
          (reservation, index) => {
            if (
              reservation.idNum.toLowerCase().includes(query.toLowerCase()) ||
              this.items[index].itemTitle
                .toLowerCase()
                .includes(query.toLowerCase()) ||
              this.items[index].itemNumber
                .toLowerCase()
                .includes(
                  query.toLowerCase() ||
                    reservation.itemReservationId
                      .toLowerCase()
                      .includes(query.toLowerCase())
                )
            ) {
              this.itemResults.push(this.items[index]);
              return true;
            }
          }
        );
      }
    },

    renew: function (itemReservationId, currentUserId, index) {
      AXIOS.put(
        "/api/itemReservations/renew/" +
          itemReservationId +
          "?currentUserId=" +
          currentUserId
      )
        .then((response) => {
          this.visible = true;
          this.$set(this.reservations, index, response.data);
          this.response = "Renewed!";
          this.error = "";
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data;
          this.error = errorMsg;
          this.response = "";
        });
    },

    cancelReservation: function (itemReservationId, currentUserId, index) {
      AXIOS.delete(
        "/api/itemReservations/cancel/" +
          itemReservationId +
          "?currentUserId=" +
          currentUserId
      )
        .then((response) => {
          this.visible = true;
          this.response = "Reservation Cancelled!";
          this.error = "";
          this.reservations.splice(index, 1);
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

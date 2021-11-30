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
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      loading: true,
      reservationResults: [],
      itemResults: [],
    };
  },
  created: async function () {
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
      await Promise.all(
        this.reservations.map((reservation) =>
          AXIOS.get("/api/items/" + reservation.itemNumber).then((response) => {
            this.items[this.reservations.indexOf(reservation)] = {
              ...response.data,
            };

            if (this.items.length == this.reservations.length) {
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
        this.itemResults = [];
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
    checkoutPressed: function (item) {
      this.$router.push({ name: "CheckoutItem", params: { item } });
    },

    cancelReservation: function (itemReservationId, currentUserId, index) {
      console.log(itemReservationId);
      console.log(index);
      console.log(this.itemResults);
      console.log(this.reservationResults);
      AXIOS.delete(
        "/api/itemReservations/cancel/" +
          itemReservationId +
          "?currentUserId=" +
          currentUserId
      )
        .then((response) => {
          console.log(itemReservationId);
          this.visible = true;
          this.response = "Reservation Cancelled!";
          this.error = "";
          this.reservations.splice(index, 1);
          // location.reload();
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

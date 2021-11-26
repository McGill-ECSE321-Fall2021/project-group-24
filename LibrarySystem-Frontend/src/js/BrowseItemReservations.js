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
      reservations: [],
      currentUser: this.$store.state.currentUser,
      loading: true,
    };
  },
  created: async function () {
    console.log(this.currentUser.idNum);
    await AXIOS.get(
      "api/itemReservations/patron/" +
        this.currentUser.idNum +
        "?currentUserId=" +
        this.currentUser.idNum
    )
      .then((response) => {
        this.reservations = response.data;

        return response.data;
      })
      .catch((e) => {
        console.log(e);
      });

    if (this.reservations.length == 0) {
      this.loading = false;
    } else {
      var counter = 0;
      await Promise.all(
        this.reservations.map((reservation) =>
          AXIOS.get("/api/items/" + reservation.itemNumber).then((response) => {
            this.reservations[counter] = { ...reservation, ...response.data };
            counter++;
            if (counter == this.reservations.length) {
              this.loading = false;
            }
          })
        )
      );
    }
  },
};

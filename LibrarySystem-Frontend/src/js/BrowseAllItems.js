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
  name: "BrowseAllItems",
  data() {
    return {
      items: [],
      newItem: "",
      error: "",
      response: "",
      results: [],
      visible: false,
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      today: new Date().toJSON().slice(0, 10),
    };
  },
  created: function () {
    //Use this to run the 'get' controller method. If you need to pass a parameter, do "/api/items/all", {params: {itemTitle}}
    AXIOS.get("/api/items/all")
      .then((response) => {
        this.items = response.data;
        this.results = this.items;
      })
      .catch((e) => {
        this.visible = true;
        this.error = e;
      });
  },
  //this is where you add custom functions / methods you want to use in your vue file. Here we have removeError to clear the error message, and createBook to create books
  methods: {
    showModal: function () {
      this.visible = true;
      this.error = "";
    },
    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },
    search: function (query) {
      this.results = [];
      if (query.length == 0) {
        this.results = this.items;
      } else {
        this.results = this.items.filter((item) => {
          if (
            item.itemTitle.toLowerCase().includes(query.toLowerCase()) ||
            (item.author &&
              item.author.toLowerCase().includes(query.toLowerCase())) ||
            item.description.toLowerCase().includes(query.toLowerCase()) ||
            item.genre.toLowerCase().includes(query.toLowerCase()) ||
            (item.publisher &&
              item.publisher.toLowerCase().includes(query.toLowerCase())) ||
            (item.issueNumber &&
              item.issueNumber.toLowerCase().includes(query.toLowerCase())) ||
            (item.movieCast &&
              item.movieCast.toLowerCase().includes(query.toLowerCase())) ||
            (item.productionCompany &&
              item.productionCompany
                .toLowerCase()
                .includes(query.toLowerCase())) ||
            (item.director &&
              item.director.toLowerCase().includes(query.toLowerCase())) ||
            (item.producer &&
              item.producer.toLowerCase().includes(query.toLowerCase())) ||
            (item.artist &&
              item.artist.toLowerCase().includes(query.toLowerCase())) ||
            (item.recordingLabel &&
              item.recordingLabel
                .toLowerCase()
                .includes(query.toLowerCase())) ||
            (item.type && item.type.toLowerCase().includes(query.toLowerCase()))
          ) {
            return true;
          }
        });
      }
    },
    deleteItem: function (itemNumber, currentUserId) {
      console.log(itemNumber);
      AXIOS.delete(
        "/api/items/delete/" + itemNumber + "?currentUserId=" + currentUserId
      )
        .then((response) => {
          this.items = this.items.filter((item) => {
            return item.itemNumber != itemNumber;
          });
          this.results = this.items;
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.error = errorMsg;
        });
    },
    checkoutPressed: function (item) {
      this.$router.push({ name: "CheckoutItem", params: { item } });
    },
    reservePressed: function (item) {
      this.$router.push({ name: "ReserveItem", params: { item } });
    },
    returnItem: function (itemNumber, currentUserId) {
      AXIOS.put(
        "/api/itemReservations/return_item/" +
          itemNumber +
          "?currentUserId=" +
          currentUserId
      )
        .then((response) => {
          console.log("returned");
          this.response = "returned!";
          this.visible = true;
          this.error = "";
        })
        .catch((e) => {
          console.log("error");
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.error = errorMsg ? errorMsg : "The item is not checked out";
          this.response = "";
        });
    },
  },
};

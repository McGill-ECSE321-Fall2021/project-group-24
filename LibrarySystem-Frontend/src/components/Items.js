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
  name: "items",
  data() {
    return {
      items: [],
      newItem: "",
      itemError: "",
      response: [],
    };
  },
  created: function (/* add parameters here if you need them */) {
    //Use this to run the 'get' controller method. If you need to pass a parameter, do "/api/items/all", {params: {itemTitle}}
    AXIOS.get("/api/items/all")
      .then((response) => {
        this.items = response.data;
        // this is how I added an example item to be shown. This is just because when we start the database it resets everything lol
        this.items = [...this.items, {itemTitle: 'this is an example item'}]
      })
      .catch((e) => {
        this.itemError = e;
        console.log(e);
      });
  },
  //this is where you add custom functions / methods you want to use in your vue file. Here we have removeError to clear the error message, and createBook to create books
  methods: {
    removeError: function () {
      this.itemError = "";
    },
    createBook: function (
      itemTitle,
      description,
      imageURL,
      publisher,
      author,
      genre,
      publishDate,
      isReservable,
      currentUserId
    ) {
      AXIOS.post("/api/items/create_book", null, {
        params: {
          itemTitle,
          description,
          imageURL,
          publisher,
          author,
          genre,
          publishDate,
          isReservable,
          currentUserId,
        },
      })
        .then((response) => {
          //this adds it to the list of items on the website
          this.items.push(response.data);
          this.itemError = "";
          this.newItem = "";
        })
        .catch((e) => {
          var errorMsg = e.response.data;
          console.log(e.response);
          this.itemError = errorMsg;
        });
    },
  },
};

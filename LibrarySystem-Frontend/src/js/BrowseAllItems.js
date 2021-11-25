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
      itemError: "",
      response: [],
      results: [],
      visible: false,
    };
  },
  created: function () {
    //Use this to run the 'get' controller method. If you need to pass a parameter, do "/api/items/all", {params: {itemTitle}}
    AXIOS.get("/api/items/all")
      .then((response) => {
        this.items = response.data;
        // this is how I added an example item to be shown. This is just because when we start the database it resets everything lol
        // this.items = [
        //   ...this.items,
        //   {
        //     itemTitle: "Percy Jackson and the Lightning Thief",
        //     itemNumber: "percy1",
        //     imageURL:
        //       "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1400602609l/28187.jpg",
        //     description:
        //       "Twelve-year-old Percy Jackson is on the most dangerous quest of his life. With the help of a satyr and a daughter of Athena, Percy must journey across the United States to catch a thief who has stolen the original weapon of mass destruction — Zeus’ master bolt. Along the way, he must face a host of mythological enemies determined to stop him. Most of all, he must come to terms with a father he has never known, and an Oracle that has warned him of betrayal by a friend.",
        //     publisher: "Disney Hyperion",
        //     author: "Rick Riordan",
        //     genre: "Seggs",
        //     publishDate: "2016-06-12",
        //   },
        //   {
        //     itemTitle: "music",
        //     itemNumber: "album1",
        //     description: "description of the album",
        //     genre: "horror",
        //     publishDate: "2021-09-22",
        //     artist: "nafis",
        //     recordingLabel: "arman",
        //   },
        // ];
        this.results = this.items;
      })
      .catch((e) => {
        this.visible = true;
        this.itemError = e;
        console.log(e);
      });
  },
  //this is where you add custom functions / methods you want to use in your vue file. Here we have removeError to clear the error message, and createBook to create books
  methods: {
    showModal: function () {
      this.visible = true;
      this.itemError = "";
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
              item.recordingLabel.toLowerCase().includes(query.toLowerCase()))
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
          this.itemError = errorMsg;
        });
    },
  },
};

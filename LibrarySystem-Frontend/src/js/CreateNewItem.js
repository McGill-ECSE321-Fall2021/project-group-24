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
  name: "item",
  data() {
    return {
      rooms: [],
      newRoom: "",
      roomError: "",
      response: [],
      responseStatus: null,
      results: [],
      visible: false,
      formLayout: "horizontal",
      formForBook: this.$form.createForm(this, { name: "coordinated" }),
      formForMovie: this.$form.createForm(this, { name: "coordinated" }),
      formForArchive: this.$form.createForm(this, { name: "coordinated" }),
      formForMusicAlbum: this.$form.createForm(this, { name: "coordinated" }),
      formForPrintedMedia: this.$form.createForm(this, { name: "coordinated" }),
      item: [],
      keys: "",
    };
  },

  methods: {
    callback(key) {
      console.log(key);
      this.keys = key;
      console.log("KEYS: " + this.keys);
    },

    showModal: function () {
      this.visible = true;
      this.roomError = "";
    },
    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    handleBookSubmit(e) {
      e.preventDefault();
      this.formForBook.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.item = values;
          console.log("RESERVABLE?: " + this.item.reserveGroupButtons);
          AXIOS.post(
            "/api/items/create_book" +
              "?itemTitle=" +
              this.item.itemTitle +
              "&description=" +
              this.item.description +
              "&imageURL=" +
              this.item.imageURL +
              "&publisher=" +
              this.item.publisher +
              "&author=" +
              this.item.author +
              "&genre=" +
              this.item.genre +
              "&publishDate=" +
              this.item.publishDate +
              "&isReservable=" +
              this.item.reserveGroupButtons +
              "&currentUserId=" +
              this.item.currentUserId
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              return res.status;
            })
            .catch((e) => {
              console.log("ERROR RESPONSE: " + e.response.data.error);
              this.visible = true;
              var errorMsg = e.response.data.error;
              this.roomError = errorMsg;
            });
        }
      });
    },

    handleMovieSubmit(e) {
      e.preventDefault();
      this.formForMovie.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.item = values;
          console.log("RESERVABLE?: " + this.item.reserveGroupButtons);
          AXIOS.post(
            "/api/items/create_movie" +
              "?itemTitle=" +
              this.item.itemTitle +
              "&description=" +
              this.item.description +
              "&imageURL=" +
              this.item.imageURL +
              "&genre=" +
              this.item.genre +
              "&publishDate=" +
              this.item.publishDate +
              "&isReservable=" +
              this.item.reserveGroupButtons +
              "&movieCast=" +
              this.item.movieCast +
              "&productionCompany=" +
              this.item.productionCompany +
              "&director=" +
              this.item.director +
              "&producer=" +
              this.item.producer +
              "&currentUserId=" +
              this.item.currentUserId
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              return res.status;
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.roomError = errorMsg;
            });
        }
      });
    },

    handleArchiveSubmit(e) {
      e.preventDefault();
      this.formForArchive.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.item = values;
          console.log("RESERVABLE?: " + this.item.reserveGroupButtons);
          AXIOS.post(
            "/api/items/create_archive" +
              "?itemTitle=" +
              this.item.itemTitle +
              "&description=" +
              this.item.description +
              "&imageURL=" +
              this.item.imageURL +
              "&genre=" +
              this.item.genre +
              "&publishDate=" +
              this.item.publishDate +
              "&isReservable=" +
              this.item.reserveGroupButtons +
              "&currentUserId=" +
              this.item.currentUserId
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              return res.status;
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.roomError = errorMsg;
            });
        }
      });
    },

    handleMusicAlbumSubmit(e) {
      e.preventDefault();
      this.formForMusicAlbum.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.item = values;
          console.log("RESERVABLE?: " + this.item.reserveGroupButtons);
          AXIOS.post(
            "/api/items/create_musicAlbum" +
              "?itemTitle=" +
              this.item.itemTitle +
              "&description=" +
              this.item.description +
              "&imageURL=" +
              this.item.imageURL +
              "&genre=" +
              this.item.genre +
              "&publishDate=" +
              this.item.publishDate +
              "&isReservable=" +
              this.item.reserveGroupButtons +
              "&currentUserId=" +
              this.item.currentUserId +
              "&artist=" +
              this.item.artist +
              "&recordingLabel=" +
              this.item.recordingLabel
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              return res.status;
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.roomError = errorMsg;
            });
        }
      });
    },

    handlePrintedMediaSubmit(e) {
      e.preventDefault();
      this.formForPrintedMedia.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.item = values;
          console.log("RESERVABLE?: " + this.item.reserveGroupButtons);
          AXIOS.post(
            "/api/items/create_printedMedia" +
              "?itemTitle=" +
              this.item.itemTitle +
              "&description=" +
              this.item.description +
              "&imageURL=" +
              this.item.imageURL +
              "&genre=" +
              this.item.genre +
              "&publishDate=" +
              this.item.publishDate +
              "&isReservable=" +
              this.item.reserveGroupButtons +
              "&currentUserId=" +
              this.item.currentUserId +
              "&issueNumber=" +
              this.item.issueNumber
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              return res.status;
            })
            .catch((e) => {
              this.visible = true;
              var errorMsg = e.response.data;
              this.roomError = errorMsg;
            });
        }
      });
    },
  },
};

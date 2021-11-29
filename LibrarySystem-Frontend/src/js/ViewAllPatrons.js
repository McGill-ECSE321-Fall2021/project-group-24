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
  name: "listofpatrons",
  data() {
    return {
      loading: true,
      visible: false, // the modify booking model
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      form: this.$form.createForm(this, { name: "coordinated" }),
      patronResults: [], // stores the patrons that match the search
      patrons: [], // list of all patrons
      // attributes of Patron
    };
  },
  // gets all patrons when the page is loaded
  created: async function () {
    if (!this.currentUser.isPatron) {
      await AXIOS.get("api/patron/all")
        .then((response) => {
          console.log(response.data);
          this.patrons = response.data;
          this.patronResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    }
  },
  methods: {
    verifyPatron() {}, // TODO
    deletePatron() {}, // TODO
    search: function (query) {
      this.patronResults = [];
      if (query.length == 0) {
        this.patronResults = this.patrons;
      } else {
        this.patronResults = this.patrons.filter((patron, index) => {
          if (
            patron.username.toLowerCase().includes(query.toLowerCase()) ||
            patron.email.toLowerCase().includes(query.toLowerCase()) ||
            patron.isVerfied.toLowerCase().includes(query.toLowerCase())
          ) {
            this.patronResults.push(patron);
            return true;
          }
        });
      }
    },
  },
};

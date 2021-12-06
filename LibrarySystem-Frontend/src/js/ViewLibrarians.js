import axios, { Axios } from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "ViewLibrarians",
  data() {
    return {
      loading: true,
      visible: false, // the modify booking model
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: this.$store.state.currentUser,
      form: this.$form.createForm(this, { name: "coordinated" }),
      librarianResults: [], // stores the librarians that match the search
      librarians: [], // array of all librarians
      idNum: "",
      librarian: "",
      // attributes of Librarian
    };
  },
  // gets all librarians when the page has loaded
  created: async function () {
    if (!this.currentUser.isPatron) {
      await AXIOS.get("/api/librarians/all?currentUserId=admin")
        .then((response) => {
          this.librarians = response.data;
          this.librarianResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e)
        });
    }
  },
  methods: {
    // sends the librarian that has been selected the ViewLibrarianShifts 
    viewShiftsPressed: function(librarian) {
      this.$router.push({ name: "ViewLibrarianShifts", params: { librarian }});
    },
    // very basic search by last name, username, or email.
    search: function (query) {
      this.librarianResults = [];
      if (query.length == 0) {
        this.librarianResults = this.librarians;
      } else {
        this.librarianResults = this.librarians.filter((librarian, index) => {
          if (
            librarian.username.toLowerCase().includes(query.toLowerCase()) ||
            librarian.email.toLowerCase().includes(query.toLowerCase()) ||
            librarian.lastName.toLowerCase().includes(query.toLowerCase())
          ) {
            this.librarianResults.push(librarian);
            return true;
          }
        });
      }
    },
  },
};

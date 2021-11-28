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
  name: "AddLibraryHours",
  data() {
    return {
      libraryHours: [],
      newLibraryHour: "",
      dayOfWeek: "", 
      startTime: "", 
      endTime: "",
      libraryHourError: "",
      response: [],
      responseStatus: null,
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  }, 

  methods: {
    showModal: function () {
      this.visible = true;
      this.userError = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    addLibraryHour: function (
      dayOfWeek,
      startTime, 
      endTime
    ) {

      AXIOS.post(
        "/api/libraryhour/add_libraryhour?currentUserId=admin" +
          "&dayOfWeek=" +
          dayOfWeek +
          "&startTime=" +
          startTime +
          "&endTime=" +
          endTime
      )
        .then((response) => {
          this.users = this.users.filter((user) => {
            return user.username != username;
          });
          this.results = this.users;
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data.error;
          this.roomError = errorMsg;
        });
    },

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.newLibraryHour = values;
          console.log("any object", this.newLibraryHour.dayOfWeek);
          AXIOS.post(
            "/api/libraryhour/add_libraryhour?currentUserId=admin" +
            "&dayOfWeek=" +
            this.dayOfWeek +
            "&startTime=" +
            this.startTime +
            "&endTime=" +
            this.endTime
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              console.log(res.data);
              // console.log("HI");
              // this.$store.commit("changeUser", res.data);
              console.log("THEN: ");
              if (this.responseStatus == 200) {
                this.$router.replace({ name: "Homepage" });
              }
              return res.status;
            })
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              this.userError = errorMsg;
            });
        }
      });
    },
  },
};

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
  name: "RemoveLibraryHours",
  data() {
    return {
      hours: [],
      oldLibraryHour: "",
      dayOfWeek: "",
      startTime: "",
      endTime: "",
      response: [],
      responseStatus: null,
      removeHourError: "",
      daysOfWeek: [],
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  },
  created: function () {
    // using array to determine which days of week exist
    var daysOfWeek = [false, false, false, false, false, false, false];
    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      this.hours = res.data;
      console.log("sdf ", this.hours);
      console.log("sdfdsf ", this.hours[0].dayOfWeek);
      // sets which days of week have a library hour.
      //Accounts for the possibility that the library is closed midweek (i.e. open Monday-Thursday & Saturday)
      for (var i = 0; i < this.hours.length; i++) {
        if (this.hours[i].dayOfWeek == "MONDAY") daysOfWeek[0] = true;
        if (this.hours[i].dayOfWeek == "TUESDAY") daysOfWeek[1] = true;
        if (this.hours[i].dayOfWeek == "WEDNESDAY") daysOfWeek[2] = true;
        if (this.hours[i].dayOfWeek == "THURSDAY") daysOfWeek[3] = true;
        if (this.hours[i].dayOfWeek == "FRIDAY") daysOfWeek[4] = true;
        if (this.hours[i].dayOfWeek == "SATURDAY") daysOfWeek[5] = true;
        if (this.hours[i].dayOfWeek == "SUNDAY") daysOfWeek[6] = true;
      }
      console.log("it exists: ", daysOfWeek[0]);
      console.log("it exists: ", daysOfWeek[3]);
      console.log("it exists: ", daysOfWeek[6]);
    });
  },

  methods: {
    showModal: function () {
      this.visible = true;
      this.removeHourError = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    // calls the delete controller method when the "remove library hour" button is pressed
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        console.log("Recieved values of form: ", values);
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.oldLibraryHour = values;
          console.log("any object", this.oldLibraryHour.startTime);
          AXIOS.delete(
            "/api/libraryhour/remove_library_hour?currentUserId=admin" +
              "&dayOfWeek=" +
              this.oldLibraryHour.dayOfWeek +
              "&startTime=" +
              this.oldLibraryHour.startTime +
              "&endTime=" +
              this.oldLibraryHour.endTime
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              if (this.responseStatus == 201) {
                this.$router.replace({ name: "Homepage" });
              }
              return res.status;
            })
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              this.removeHourError = errorMsg;
              alert(this.removeHourError);
            });
        }
      });
    },
  },
};

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
      RemoveHourError: "",
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  },   created: function () {
    // using array to determine which days of week exist  
    var daysOfWeek = [
      false, 
      false, 
      false,
      false,
      false,
      false,
      false
    ];
    AXIOS.get("api/libraryhour/view_library_hours").then((res) => {
      this.hours = res.data;
      for (var i=0; i<this.hours.length; i++)  {
        daysOfWeek[i] = true;
      }
      console.log(res.data);
    });
  }, 

  methods: {
    showModal: function () {
      this.visible = true;
      this.RemoveHourError = "";
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
              console.log(res.data);
              // console.log("HI");
              // this.$store.commit("changeUser", res.data);
              console.log("THEN: ");
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
            });
        }
      });
    },
  },
};

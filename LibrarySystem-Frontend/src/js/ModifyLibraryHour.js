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
  name: "ModifyLibraryHours",
  data() {
    return {
      hours: [],
      newLibraryHour: "",
      dayOfWeek: "", 
      startTime: "", 
      endTime: "",
      response: [],
      responseStatus: null,
      modifyHourError: "",
      daysOfWeek: [],
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
      console.log("sdf ", this.hours);
      console.log("sdfdsf ", this.hours[0].dayOfWeek); 
      // sets which days of week have a library hour. 
      //Accounts for the possibility that the library is closed midweek (i.e. open Monday-Thursday & Saturday)
      for (var i=0; i<this.hours.length; i++) {
            if (this.hours[i].dayOfWeek=="MONDAY") daysOfWeek[0] = true; 
            if (this.hours[i].dayOfWeek=="TUESDAY") daysOfWeek[1] = true; 
            if (this.hours[i].dayOfWeek=="WEDNESDAY") daysOfWeek[2] = true; 
            if (this.hours[i].dayOfWeek=="THURSDAY") daysOfWeek[3] = true; 
            if (this.hours[i].dayOfWeek=="FRIDAY") daysOfWeek[4] = true; 
            if (this.hours[i].dayOfWeek=="SATURDAY") daysOfWeek[5] = true; 
            if (this.hours[i].dayOfWeek=="SUNDAY") daysOfWeek[6] = true; 
            }

    });
  }, 

  methods: {
    showModal: function () {
      this.visible = true;
      this.modifyHourError = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },

    // calls the post controller method when the "modify library hour" button is pressed
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        console.log("Recieved values of form: ", values);
        if (!err) {
          console.log("Recieved values of form: ", values);
          this.newLibraryHour = values;
          console.log("any object", this.newLibraryHour.startTime);
          AXIOS.put(
            "/api/libraryhour/modify_library_hour?currentUserId=admin" +
            "&dayOfWeek=" +
            this.newLibraryHour.dayOfWeek +
            "&startTime=" +
            this.newLibraryHour.startTime +
            "&endTime=" +
            this.newLibraryHour.endTime
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
              this.modifyHourError = errorMsg;
              alert(this.modifyHourError); 
            });
        }
      });
    },
  },
};

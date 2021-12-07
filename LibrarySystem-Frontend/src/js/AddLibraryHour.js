import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

// "new" library hour and its attributes are exported
export default {
  name: "AddLibraryHours",
  data() {
    return {
      newLibraryHour: "",
      dayOfWeek: "", 
      startTime: "", 
      endTime: "",
      response: [],
      responseStatus: null,
      addHourError: "",
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  }, 

  methods: {
    // shows error as dialogue window in the browser
    showModal: function () {
      this.visible = true;
      this.addHourError = "";
    },
    // if no error, don't show anything
    handleOk: function (e) {
      this.visible = false;
    },

    // When submit button is pressed, post request is sent to the backend to add a hour. 
    // User is then taken to the homepage. 
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        // if no errors, send the post request to the backend api
        if (!err) {
          this.newLibraryHour = values;
          console.log("any object", this.newLibraryHour.startTime);
          AXIOS.post(
            "/api/libraryhour/add_library_hour?currentUserId=admin" +
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
              // if successfully added the hour, send user to homepage
              if (this.responseStatus == 201) {
                this.$router.replace({ name: "Homepage" });
              }
              return res.status;
            })
            // display errors in the dialogue window
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              this.addHourError = errorMsg;
              alert(this.addHourError);
            });
        }
      });
    },
  },
};

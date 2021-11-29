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
    showModal: function () {
      this.visible = true;
      this.addHourError = "";
    },

    handleOk: function (e) {
      console.log(e);
      this.visible = false;
    },


    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        console.log("Recieved values of form: ", values);
        if (!err) {
          console.log("Recieved values of form: ", values);
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
              if (this.responseStatus == 201) {
                this.$router.replace({ name: "Homepage" });
              }
              return res.status;
            })
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

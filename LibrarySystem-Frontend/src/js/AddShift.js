import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

// "new" shift and its attributes are exported
export default {
  name: "AddShift",
  data() {
    return {
      newShift: "",
      dayOfWeek: "", 
      startTime: "", 
      endTime: "",
      librarian: "",
      librarians: [],
      response: [],
      responseStatus: null,
      addShiftError: "",
      results: [],
      visible: false,
      formLayout: "horizontal",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  }, 
  created: function () {
    // gets list of librarians for the drop-down selector to display
    AXIOS.get("/api/librarians/all?currentUserId=admin").then((res) => {
      this.librarians = res.data;
      console.log("resdata: ", resdata);
    });
    console.log(this.librarians); 
  },

  methods: {
    // shows error as dialogue window in the browser
    showModal: function () {
      this.visible = true;
      this.addShiftError = "";
    },
    // if no error, don't show anything
    handleOk: function (e) {
      this.visible = false;
    },

    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        // if no errors, send the post request to the backend api
        if (!err) {
          this.newShift = values;
          AXIOS.post(
            "/api/shift/add_shift?currentUserId=admin&librarianId=" +
            this.newShift.librarian +
            "&dayOfWeek=" +
            this.newShift.dayOfWeek +
            "&startTime=" +
            this.newShift.startTime +
            "&endTime=" +
            this.newShift.endTime
          )
            .then((res) => {
              console.log("RESPONSE: " + res.status);
              this.visible = true;
              this.responseStatus = res.status;
              // if successfully added the shift, send user to "browse shifts" page
              if (this.responseStatus == 201) {
                this.$router.replace({ name: "BrowseAllShifts" });
              }
              return res.status;
            })
            // display errors in the dialogue window
            .catch((e) => {
              console.log("CATCH: ");
              this.visible = true;
              var errorMsg = e.response.data;
              this.addShiftError = errorMsg;
              alert(this.addShiftError);
            });
        }
      });
    },
  },
};

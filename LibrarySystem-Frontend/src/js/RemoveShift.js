import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});
/**
 * Returns the librarian's shifts so they can be displayed
 * */
export default {
  name: "RemoveShift",
  data() {
    return {
      shifts: [],
      ...this.$route.params, // receives the librarian that has been "sent" from ViewLibrarianShifts webpage
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      lib: "",
      shiftId: "",
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  },

  created: function () {
    console.log("HELLO");
    console.log(this.lib);

    AXIOS.get("api/shift/view_librarian_shifts?currentUserId=admin&librarianId="+
      this.librarian.idNum
    ).then((res) => {
      this.shifts = res.data;
      console.log(res.data);
    });
  },
  methods: {
  // send user back to view the librarian's shifts  
  viewShiftsPressed: function(librarian) {
    this.$router.push({ name: "ViewLibrarianShifts", params: { librarian }});
  },
  handleSubmit(e) {
    e.preventDefault();
    this.form.validateFields((err, values) => {
      // if no errors, send the delete request to the backend api
      if (!err) {
        this.shiftId = values;
        console.log("TIMESLOT ID : ", this.shiftId); 

        AXIOS.delete(
          "/api/shift/remove_shift?currentUserId=admin&timeSlotId=" +
            this.shiftId.shift
        )
          .then((res) => {
            console.log("RESPONSE: " + res.status);
            this.visible = true;
            this.responseStatus = res.status;
            // if successfully deleted the shift, go to view shifts
            if (this.responseStatus == 201) {
             this.$router.replace({ name: "BrowseAllShifts" });
            }
          })
          // display errors in the dialogue window
          .catch((e) => {
            console.log("CATCH: ");
            this.visible = true;
            var errorMsg = e.response;
            alert(errorMsg);
          });
      }
    });
  },
},
};
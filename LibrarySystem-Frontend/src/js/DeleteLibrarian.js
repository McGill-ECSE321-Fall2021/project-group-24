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
  name: "DeleteLibrarian",
  data() {
    return {
      visible: false, 
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: this.$store.state.currentUser,
      form: this.$form.createForm(this, { name: "coordinated" }),
      idNum: "",
      
    };
  },
  methods: {
  // handles what happens when submit button is pressed
  handleSubmit(e) {
    e.preventDefault();
    this.form.validateFields((err, values) => {
      console.log("id num : ",values); 
      // if no errors, send the delete request to the backend api
      if (!err) {
        this.idNum = values;
        console.log("ID NUM: ", this.idNum);
        AXIOS.delete(
          "/api/librarians/delete/"+this.idNum.idNumber+"/?currentUserId=admin" 
        )
          .then((res) => {
            console.log("RESPONSE: " + res.status);
            this.visible = true;
            this.responseStatus = res.status;
            // if successfully deleted the librarian, send user to view librarians
            if (this.responseStatus == 200) {
              this.$router.replace({ name: "ViewLibrarians" });
            }
            return res.status;
          })
          // display errors in the dialogue window
          .catch((e) => {
            console.log("CATCH: ");
            this.visible = true;
            var errorMsg = e.response.data;
            this.error = errorMsg;
            alert(this.error);
          });
      }
    });
  },
  },
};

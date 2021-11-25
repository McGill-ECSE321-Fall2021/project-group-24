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
  name: "ReserveRoom",
  data() {
    return {
      currentUser: this.$store.state.currentUser,
      ...this.$route.params,
      form: this.$form.createForm(this, { name: "coordinated" }),
      error: "",
      modalVisible: false,
      response: "",
    };
  },
  created: function () {},

  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          AXIOS.post(
            "api/roombookings/add_roombooking/?currentUserId=" +
              this.currentUser.idNum +
              "&idNum=" +
              (this.currentUser.isPatron
                ? this.currentUser.idNum
                : values.idNum) +
              "&roomNum=" +
              this.room.roomNum +
              "&date=" +
              values.date +
              "&startTime=" +
              values.startTime +
              "&endTime=" +
              values.endTime
          )
            .then((res) => {
              this.response = "Successfully created Room Booking for patron";
              this.modalVisible = true;
              this.error = "";
            })
            .catch((e) => {
              this.response = "";
              this.error = e.response.data;
              this.modalVisible = true;
            });
        }
      });
    },
  },
};

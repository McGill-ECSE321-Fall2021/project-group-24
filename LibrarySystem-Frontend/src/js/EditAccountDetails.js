import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});


export default{
    name: "User",
    data(){
        return {
            users: [],
            newUser: "",
            userError: "",
            response: [],
            responseStatus: null,
            results: [],
            visible: false,
            currentUser: this.$store.state.currentUser,
            formLayout: "horizontal",
            form: this.$form.createForm(this, { name: "coordinated" }),
            user: null,
          };
    },

    methods: {
        showModal: function () {
            this.visible = true;
            this.error = "";
        },

        handleOk: function (e) {
            console.log(e);
            this.visible = false;
        },

        goHome(){
            this.$router.push('/');
        },

        goToChangePassword(){
            this.$router.push('/changePassword');
        },
        
        goToDeleteAccount(){
            this.$router.push('/deleteAccount');
        },
    }


}
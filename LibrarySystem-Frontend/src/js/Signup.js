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
  name: "users",
  data() {
      return {
          users: [],
          newUser: "",
          userError: "",
          response: [],
          responseStatus: null,
          results: [],
          visible: false,
          formLayout: "horizontal",
          form: this.$form.createForm(this, { name: "coordinated" }),
          user: null,
        };
    },


    methods: {
        showModal: function () {
            this.visible = true;
            this.userError = "";
        },

        handleOk: function (e) {
            console.log(e);
            this.visible = false;
        },

        createPatron: function(username, password, firstName, lastName, isResident, address, email) {
            console.log(username);
            if(isResident.value="isResident"){
                isResident = "true";
            }else if(isResident.value="isNotResident"){
                isResident = "false";
            }

            AXIOS.post(
                "/api/patron/create_patron_online/?username="+
                username +
                "&password="+
                password + 
                "&first=" +
                firstName +
                "&last=" +
                lastName +
                "&isResident=" +
                isResident +
                "&address=" +
                address +
                "&email=" +
                email
            ).then((response) => {
                this.users = this.users.filter((user) => {
                    return user.username!=username;
                });
                this.results = this.users;
            })
            .catch((e)=>{
                this.visible=true;
                var errorMsg = e.response.data.error;
                this.roomError = errorMsg;
            });
        },


        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    console.log('Received values of form: ', values);
                }
            });
        },


        handleConfirmBlur(e) {
            const value = e.target.value;
            this.confirmDirty = this.confirmDirty || !!value;
        },


        compareToFirstPassword(rule, value, callback) {
            const form = this.form;
            if (value && value !== form.getFieldValue('password')) {
                callback('The two passwords do not match!');
            } else {
                callback();
            }
        },


        validateToNextPassword(rule, value, callback) {
            const form = this.form;
            if (value && this.confirmDirty) {
                form.validateFields(['confirm'], { force: true });
            }
            callback();
        },


       


        handleWebsiteChange(value) {
            let autoCompleteResult;
            if (!value) {
                autoCompleteResult = [];
            } else {
                autoCompleteResult = ['.com', '.org', '.net'].map(domain => `${value}${domain}`);
            }
            this.autoCompleteResult = autoCompleteResult;
        },

        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFields((err,values)=> {
                if(!err) {
                    console.log("Recieved values of form: ", values);
                    this.user = values;
                    console.log("any object", this.user.email);
                    AXIOS.post(
                        "/api/patron/create_patron_online/?username="+
                        this.user.username +
                        "&password="+
                        this.user.password + 
                        "&first=" +
                        this.user.firstName +
                        "&last=" +
                        this.user.lastName +
                        "&isResident=" +
                        this.user.isResident +
                        "&address=" +
                        this.user.address +
                        "&email=" +
                        this.user.email
                    ).then((res) => {
                        console.log("RESPONSE: "+res.status);
                        this.visible=true;
                        this.responseStatus = responseStatus;
                        this.user.username = "";
                        this.user.password = "";
                        this.user.firstName = "";
                        this.user.lastName = "";
                        this.user.isResident = "";
                        this.user.address = "";
                        this.user.email = "";
                        if(this.responseStatus==200){
                            window.location.href = "http://127.0.0.1:8087/#/"
                        }
                    })
                    .catch((e)=>{
                        this.visible = true;
                        var errorMsg = e.response.data;
                        this.roomError = errorMsg;
                    });
                }
            });
        },
     },
};

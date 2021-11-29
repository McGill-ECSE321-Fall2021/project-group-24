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
  name: "listofpatrons",
  data() {
    return {
      loading: true,
      visible: false, // the modify booking model
      modalVisible: false, // error modal
      error: "",
      response: "",
      events: [],
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
      form: this.$form.createForm(this, { name: "coordinated" }),
      patronResults: [], // stores the patrons that match the search
      patrons: [], // list of all patrons
      // attributes of Patron
    };
  },
  // gets all patrons when the page is loaded
  created: async function () {
    if (!this.currentUser.isPatron) {
      await AXIOS.get("api/patron/all")
        .then((response) => {
          console.log(response.data);
          this.patrons = response.data;
          this.patronResults = response.data;
          this.loading = false;
          return response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    }
  },
  methods: {
    verifyPatron(id) {
      this.form.validateFields((err, values) => {
        if(!err){
          console.log("Recieved values of form: ", values);
          this.user = values;
          AXIOS.post(
            "/api/librarians/verify/?idNum="+
            id
          ).then((res) => {
            console.log("RESPONSE: " + res.status);
            this.visible = true;
            this.responseStatus = res.status;
            console.log(res.data);
            // console.log("HI");
            // this.$store.commit("changeUser", res.data);
            console.log("THEN: ");
            if(this.responseStatus==200){
              this.visible = true;
              this.responseStatus = res.status;
              console.log(res.data);
              this.response = "Patron successfully verified.";
              this.error="";
              //need to refresh page somehow to show changes
              this.$router.go(0);
            }
          }
          ).catch((e) => {
            this.visible=true;
            this.error = e.response.data;
            this.response="";
          }
          );

        }

      });

    }, // TODO

    deletePatron(id) {
      this.form.validateFields((err, values) => {
        if(!err){
          console.log("Recieved values of form: ", values);
          this.user = values;
          AXIOS.delete(
            "/api/patron/delete_patron/"+
            id+
            "/?currentUserId="+
            this.currentUser.idNum
          ).then((res) => {
            console.log("RESPONSE: " + res.status);
            this.visible = true;
            this.responseStatus = res.status;
            console.log(res.data);
            // console.log("HI");
            // this.$store.commit("changeUser", res.data);
            console.log("THEN: ");
            if(this.responseStatus==200){
              this.visible = true;
              this.responseStatus = res.status;
              console.log(res.data);
              this.response = "Patron successfully deleted.";
              this.error="";
              this.$router.go(0);
              //need to refresh page somehow to show changes
              
            }
          }
          ).catch((e) => {
            this.visible=true;
            this.error = e.response.data;
            this.response="";
          }
          );

        }

      });
      
    },

    refreshPage(){
      this.$router.go(0);
    },


    search: function (query) {
      this.patronResults = [];
      if (query.length == 0) {
        this.patronResults = this.patrons;
      } else {
        this.patronResults = this.patrons.filter((patron, index) => {
          if (
            patron.username.toLowerCase().includes(query.toLowerCase()) ||
            patron.email.toLowerCase().includes(query.toLowerCase()) ||
            patron.isVerfied.toLowerCase().includes(query.toLowerCase())
          ) {
            this.patronResults.push(patron);
            return true;
          }
        });
      }
    },
  },
};

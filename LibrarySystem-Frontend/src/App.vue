<template>
  <div id="app">
    <router-link
      :to="{ name: 'Homepage' }"
      style="font-size: 60px; color: #000000"
      >Library System Application</router-link
    >
    <h4 v-if="this.$store.state.currentUser.username">
      Hello, {{ this.$store.state.currentUser.username }}
    </h4>

    <a-menu v-model="current" mode="horizontal">
      <a-menu-item key="Homepage">
        <router-link :to="{ name: 'Homepage' }">Home</router-link>
      </a-menu-item>
      <a-menu-item key="BrowseAllItems">
        <router-link :to="{ name: 'BrowseAllItems' }">Browse Items</router-link>
      </a-menu-item>
      <a-menu-item key="BrowseAllRooms">
        <router-link :to="{ name: 'BrowseAllRooms' }">
          Browse Rooms
        </router-link>
      </a-menu-item>

      <a-menu-item
        key="BrowseItemReservations"
        v-if="this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'BrowseItemReservations' }">
          Browse Reservations
        </router-link>
      </a-menu-item>
      <a-menu-item
        key="CreateNewItem"
        v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        "
        ><router-link :to="{ name: 'CreateNewItem' }">
          Create New Item
        </router-link></a-menu-item
      >
      <a-menu-item
        key="CreateNewRoom"
        v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        "
      >
        <router-link :to="{ name: 'CreateNewRoom' }">
          Create New Room
        </router-link>
      </a-menu-item>
      <a-menu-item
        key="LoginPage"
        v-if="!this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'LoginPage' }"> Sign in </router-link>
      </a-menu-item>
      <a-menu-item
        key="SignUpIRL"
        v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        "
      >
        <router-link :to="{ name: 'SignupIRL' }">
          Sign Up for Patron
        </router-link>
      </a-menu-item>
      <a-menu-item
        key="SignupPage"
        v-if="!this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'SignupPage' }"> Sign up </router-link>
      </a-menu-item>
      <a-menu-item
        key="BrowseRoomBookings"
        v-if="this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'BrowseRoomBookings' }">
          Browse Room Bookings
        </router-link>
      </a-menu-item>

      <a-menu-item key="EditAccountDetails" v-if="this.$store.state.currentUser.username">
        <router-link :to="{ name: 'EditAccountDetails' }">Settings</router-link>
      </a-menu-item>


      <a-menu-item key="Logout" v-if="this.$store.state.currentUser.username">
        <a @click="logout">Logout</a>
      </a-menu-item>
    </a-menu>
    <br />
    <router-view></router-view>
  </div>
</template>

<script>
import axios from "axios";
var config = require("../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});
export default {
  name: "app",
  methods: {
    logout: function () {
      //insert logout method here
      AXIOS.post("api/user/logout/" + this.$store.state.currentUser.username)
        .then((res) => {
          this.visible = true;
          this.responseStatus = res.status;
          if (this.responseStatus == 200) {
            this.$store.commit("changeUser", "null");
            this.$router.push({ name: "Homepage" });
          }
          return res.status;
        })
        .catch((e) => {
          this.visible = true;
          var errorMsg = e.response.data;
          this.userError = errorMsg;
        });
    },
  },
  data() {
    return {
      current: [this.$route.name],
    };
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

<style scoped>
/* a {
  font-size: 60px;
  color: #000000;
} */
</style>

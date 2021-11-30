<template>
  <!--Application header -->
  <div id="app">
    <router-link
      :to="{ name: 'Homepage' }"
      style="font-size: 60px; color: #000000"
      >Library System Application</router-link
    >
    <h4 v-if="currentUser.username">Hello, {{ currentUser.username }}</h4>
    <!--Navigation Menu starting with "Homepage" on the furthest left and other tabs to its right 
      Depending on the type of user accessing the website, different tabs in the menu are available.
      e.g. "View Librarians" is accessible only if the user is logged in as a librarian, 
      while "Browse Items" is accessible by any user (if those not logged-in)
  -->
    <!-- menu bar for logged in librarians/head librarian -->
    <div>
      <a-menu
        v-model="current"
        mode="horizontal"
        v-if="currentUser.username && !currentUser.isPatron"
      >
        <a-menu-item key="Homepage">
          <router-link :to="{ name: 'Homepage' }">Home </router-link>
        </a-menu-item>
        <a-sub-menu>
          <span slot="title" class="submenu-title-wrapper">Manage Items</span>
          <a-menu-item
            key="CreateNewItem"
            v-if="currentUser.username && !currentUser.isPatron"
            ><router-link :to="{ name: 'CreateNewItem' }">
              Create New Item
            </router-link></a-menu-item
          >
          <a-menu-item key="BrowseAllItems">
            <router-link :to="{ name: 'BrowseAllItems' }"
              >Browse Items</router-link
            >
          </a-menu-item>
          <a-menu-item key="BrowseItemReservations" v-if="currentUser.username">
            <router-link :to="{ name: 'BrowseItemReservations' }">
              Browse Reservations
            </router-link>
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu>
          <span slot="title" class="submenu-title-wrapper"> Manage Rooms</span>
          <a-menu-item
            key="CreateNewRoom"
            v-if="currentUser.username && !currentUser.isPatron"
          >
            <router-link :to="{ name: 'CreateNewRoom' }">
              Create New Room
            </router-link>
          </a-menu-item>
          <a-menu-item key="BrowseAllRooms">
            <router-link :to="{ name: 'BrowseAllRooms' }">
              Browse Rooms
            </router-link>
          </a-menu-item>
          <a-menu-item key="BrowseRoomBookings" v-if="currentUser.username">
            <router-link :to="{ name: 'BrowseRoomBookings' }">
              Browse Room Bookings
            </router-link>
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu>
          <span slot="title" class="submenu-title-wrapper">
            Manage Patrons</span
          >
          <a-menu-item
            key="ViewAllPatrons"
            v-if="currentUser.username && !currentUser.isPatron"
          >
            <router-link :to="{ name: 'ViewAllPatrons' }">
              View all Patrons
            </router-link>
          </a-menu-item>
          <a-menu-item
            key="SignUpIRL"
            v-if="currentUser.username && !currentUser.isPatron"
          >
            <router-link :to="{ name: 'SignupIRL' }">
              Sign Up for Patron
            </router-link>
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu
          v-if="currentUser.username && currentUser.username === 'admin'"
        >
          <span slot="title" class="submenu-title-wrapper">
            Manage Librarians</span
          >
          <a-menu-item
            key="ViewLibrarians"
            v-if="currentUser.username && !currentUser.isPatron"
          >
            <router-link :to="{ name: 'ViewLibrarians' }">
              View Librarians
            </router-link>
          </a-menu-item>
          <a-menu-item key="BrowseAllShifts">
            <router-link :to="{ name: 'BrowseAllShifts' }">
              Browse Shifts
            </router-link>
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item
          v-if="currentUser.username && currentUser.username != 'admin'"
          key="BrowseAllShifts"
        >
          <router-link :to="{ name: 'BrowseAllShifts' }">
            Browse Shifts
          </router-link>
        </a-menu-item>

        <a-menu-item key="LoginPage" v-if="!currentUser.username">
          <router-link :to="{ name: 'LoginPage' }"> Sign in </router-link>
        </a-menu-item>

        <a-menu-item key="SignupPage" v-if="!currentUser.username">
          <router-link :to="{ name: 'SignupPage' }"> Sign up </router-link>
        </a-menu-item>

        <a-menu-item key="EditAccountDetails" v-if="currentUser.username">
          <router-link :to="{ name: 'EditAccountDetails' }"
            >Settings</router-link
          >
        </a-menu-item>

        <a-menu-item key="Logout" v-if="currentUser.username">
          <a @click="logout">Logout</a>
        </a-menu-item>
      </a-menu>
    </div>

    <!-- menu bar for not logged in or a logged in patron -->
    <div>
      <a-menu
        v-model="current"
        mode="horizontal"
        v-if="
          !currentUser.username ||
          (currentUser.username && currentUser.isPatron)
        "
      >
        <a-menu-item key="Homepage">
          <router-link :to="{ name: 'Homepage' }">Home </router-link>
        </a-menu-item>
        <a-menu-item key="BrowseAllItems">
          <router-link :to="{ name: 'BrowseAllItems' }"
            >Browse Items</router-link
          >
        </a-menu-item>
        <a-menu-item key="BrowseAllRooms">
          <router-link :to="{ name: 'BrowseAllRooms' }">
            Browse Rooms
          </router-link>
        </a-menu-item>
        <a-menu-item key="BrowseItemReservations" v-if="currentUser.username">
          <router-link :to="{ name: 'BrowseItemReservations' }">
            Browse Reservations
          </router-link>
        </a-menu-item>
        <a-menu-item key="BrowseRoomBookings" v-if="currentUser.username">
          <router-link :to="{ name: 'BrowseRoomBookings' }">
            Browse Room Bookings
          </router-link>
        </a-menu-item>
        <a-menu-item key="LoginPage" v-if="!currentUser.username">
          <router-link :to="{ name: 'LoginPage' }"> Sign in </router-link>
        </a-menu-item>

        <a-menu-item key="SignupPage" v-if="!currentUser.username">
          <router-link :to="{ name: 'SignupPage' }"> Sign up </router-link>
        </a-menu-item>

        <a-menu-item key="EditAccountDetails" v-if="currentUser.username">
          <router-link :to="{ name: 'EditAccountDetails' }"
            >Settings</router-link
          >
        </a-menu-item>

        <a-menu-item key="Logout" v-if="currentUser.username">
          <a @click="logout">Logout</a>
        </a-menu-item>
      </a-menu>
    </div>
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
  watch: {
    $route(to, from) {
      this.current = [to.name];
    },
  },
  methods: {
    logout: function () {
      //When a user logouts they are redirected to the homepage
      AXIOS.post("api/user/logout/" + this.currentUser.username)
        .then((res) => {
          this.visible = true;
          this.responseStatus = res.status;
          if (this.responseStatus == 200) {
            window.sessionStorage.setItem(
              "currentUser",
              JSON.stringify({ username: null, password: null, isPatron: null })
            );
            this.currentUser = {
              username: null,
              password: null,
              isPatron: null,
            };
            this.$router.replace({ name: "Homepage" });
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
      currentUser: JSON.parse(sessionStorage.getItem("currentUser")),
    };
  },
  created: function () {
    if (this.currentUser == null || this.currentUser.username == null) {
      sessionStorage.setItem(
        "currentUser",
        JSON.stringify({ username: null, password: null, isPatron: null })
      );
      this.currentUser = sessionStorage.getItem("currentUser");
    }
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

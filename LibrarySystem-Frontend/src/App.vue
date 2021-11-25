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

    <a-menu :v-model="this.$route.name" mode="horizontal">
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
        key="CreateNewItem"
        v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        "
      >
        <router-link :to="{ name: 'CreateNewItem' }">
          Create new Item
        </router-link>
      </a-menu-item>
      <a-menu-item
        key="CreateNewRoom"
        v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        "
      >
        <router-link :to="{ name: 'CreateNewRoom' }">
          Create new Room
        </router-link>
      </a-menu-item>
      <a-menu-item
        key="LoginPage"
        v-if="!this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'LoginPage' }"> Sign in </router-link>
      </a-menu-item>
      <a-menu-item
        key="SignupPage"
        v-if="!this.$store.state.currentUser.username"
      >
        <router-link :to="{ name: 'SignupPage' }"> Sign up </router-link>
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
export default {
  name: "app",
  methods: {
    logout: function () {
      //insert logout method here
      this.$router.push({ name: "LoginPage" });
    },
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

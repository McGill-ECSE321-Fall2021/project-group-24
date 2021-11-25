<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> !-->
    <h1>Library System Application</h1>
    <h4 v-if="this.$store.state.currentUser.username">
      Hello, {{ this.$store.state.currentUser.username }}
    </h4>

    <a-menu :v-model="this.$route.name" mode="horizontal">
      <a-menu-item key="BrowseAllItems">
        <router-link :to="{ name: 'BrowseAllItems' }">Browse Items</router-link>
      </a-menu-item>
      <a-menu-item key="BrowseAllRooms">
        <router-link :to="{ name: 'BrowseAllRooms' }">
          Browse Rooms
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
    </a-menu>

    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
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

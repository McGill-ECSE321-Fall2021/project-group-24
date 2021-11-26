<template>
  <div class="Homepage">
    <br />
    <h2>Authentication</h2>
    <ul>
      <li>
        <router-link :to="{ name: 'LoginPage' }">Sign in</router-link>
        <router-link :to="{ name: 'SignupPage' }">Sign up</router-link>
      </li>
    </ul>
    <h2>Items</h2>
    <ul>
      <li>
        <router-link :to="{ name: 'BrowseAllItems' }" >Browse Items</router-link>
      </li>
    </ul>
    <ul>
      <li>
        <router-link :to="{ name: 'CreateNewItem' }" v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        ">Create Item</router-link>
        
      </li>
    </ul>
    <h2>Rooms</h2>
    <ul>
      <li>
        <router-link :to="{ name: 'BrowseAllRooms' }">Browse Rooms</router-link>
      </li>
    </ul>
    <ul>
      <li>
        <router-link :to="{ name: 'CreateNewRoom' }" v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron
        ">Create Rooms</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Homepage",
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: bold;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 10px;
  padding: 10px;
}
a {
  color: #42b987;
}
</style>

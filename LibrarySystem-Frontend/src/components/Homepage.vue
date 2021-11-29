<template>
<!-- Home page shows operating hours for the library.
     If the head librarian (admin) is logged in, 
     buttons appear that allow them to add, modify, and remove the hours.
-->
  <div class="Homepage">
    <h3>Library Opening Hours</h3>
    <h5 v-if="hours.length == 0">No library hours</h5>
    <div v-for="hour in hours" :key="hour.dayOfWeek">
      <h5>
        {{ hour.dayOfWeek[0] + hour.dayOfWeek.slice(1).toLowerCase() }}:
        {{ hour.startTime.substring(0, 5) }} -
        {{ hour.endTime.substring(0, 5) }}
      </h5>
    </div>
    <a-button key="AddLibraryHour" type="primary" v-if="
          this.$store.state.currentUser.username &&
          !this.$store.state.currentUser.isPatron &&
          !this.$store.state.currentUser.isLibrarian
        " >
        <router-link :to="{ name: 'AddLibraryHour' }">
                Add Library Hour
        </router-link>
    </a-button>

    <a-button key="RemoveLibraryHour" type="primary" v-if="
        this.$store.state.currentUser.username &&
        !this.$store.state.currentUser.isPatron &&
        !this.$store.state.currentUser.isLibrarian &&
        hours.length!=0
      " >
        <router-link :to="{ name: 'RemoveLibraryHour' }">
                Remove Library Hour
        </router-link>
    </a-button>

    <a-button key="ModifyLibraryHour" type="primary" v-if="
        this.$store.state.currentUser.username &&
        !this.$store.state.currentUser.isPatron &&
        !this.$store.state.currentUser.isLibrarian
      " >
        <router-link :to="{ name: 'ModifyLibraryHour' }">
                Modify Library Hour
        </router-link>
    </a-button>
  </div>
</template>
<script src="../js/Homepage.js"></script>

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

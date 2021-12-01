<template>
  <!-- Home page shows operating hours for the library. A featured item is also shown below the hours. 
     If the head librarian (admin) is logged in, 
     buttons appear that allow them to add, modify, and remove the hours.
-->
  <div class="Homepage" style="margin-top: 2.5%">
    <h3>Welcome to the Montreal Regional Library Official Site!</h3>

    <h4 style="margin-top: 5%">Library Opening Hours</h4>
    <h5 v-if="hours.length == 0">No library hours</h5>
    <div v-for="hour in hours" :key="hour.dayOfWeek">
      <h5>
        {{ hour.dayOfWeek[0] + hour.dayOfWeek.slice(1).toLowerCase() }}:
        {{ hour.startTime.substring(0, 5) }} -
        {{ hour.endTime.substring(0, 5) }}
      </h5>
    </div>
    <a-button
      key="AddLibraryHour"
      type="primary"
      v-if="
        currentUser.username &&
        !currentUser.isPatron &&
        !currentUser.isLibrarian
      "
    >
      <router-link :to="{ name: 'AddLibraryHour' }">
        Add Library Hour
      </router-link>
    </a-button>

    <a-button
      key="RemoveLibraryHour"
      type="primary"
      v-if="
        currentUser.username &&
        !currentUser.isPatron &&
        !currentUser.isLibrarian &&
        hours.length != 0
      "
    >
      <router-link :to="{ name: 'RemoveLibraryHour' }">
        Remove Library Hour
      </router-link>
    </a-button>

    <a-button
      key="ModifyLibraryHour"
      type="primary"
      v-if="
        currentUser.username &&
        !currentUser.isPatron &&
        !currentUser.isLibrarian &&
        hours.length != 0
      "
    >
      <router-link :to="{ name: 'ModifyLibraryHour' }">
        Modify Library Hour
      </router-link>
    </a-button>

    <!-- featured item -->
    <div
      v-if="item"
      style="margin-top: 5%; margin-left: 15%; margin-right: 15%"
    >
      <h4>Featured Item:</h4>
      <a-card style="align-items: center; margin-top: 20px" v-if="item">
        <a-card-grid
          style="width: 25%; text-align: center; align-self: center"
          :hoverable="false"
        >
          <img
            v-if="item.imageUrl"
            :alt="item.imageTitle"
            :src="item.imageUrl"
            style="width: 100%"
          />
          <img
            v-if="!item.imageUrl"
            :alt="item.imageTitle"
            style="width: 100%"
            src="https://islandpress.org/sites/default/files/default_book_cover_2015.jpg"
          />
        </a-card-grid>
        <a-card-grid
          :hoverable="false"
          :bordered="false"
          style="width: 75%; text-align: center"
        >
          <h4>{{ item.itemTitle }}</h4>
        </a-card-grid>
        <a-layout
          style="
            padding-left: 5%;
            padding-right: 5%;
            padding-top: 2.5%;
            width: 75%;
            background-color: white;
          "
        >
          <p style="text-align: left">{{ item.description }}</p>
          <p style="text-align: left">
            <span v-if="item.author">
              <br />
              <strong>Author:</strong> {{ item.author }}
            </span>
            <span v-if="item.artist">
              <br />
              <strong>Artist:</strong>
              {{ item.artist }}
            </span>
            <span v-if="item.publisher">
              <br />
              <strong>Publisher:</strong> {{ item.publisher }}
            </span>
            <span v-if="item.recordingLabel">
              <br />
              <strong>Recording Label:</strong>
              {{ item.recordingLabel }}
            </span>
            <span v-if="item.productionCompany">
              <strong>Production Company:</strong>
              {{ item.productionCompany }}
            </span>
            <span v-if="item.movieCase">
              <br />
              <strong>Movie Cast:</strong> {{ item.movieCase }}
            </span>
            <span v-if="item.director">
              <br />
              <strong>Director:</strong> {{ item.director }}
            </span>
            <span v-if="item.issueNumber">
              <br />
              <strong>Issue Number:</strong> {{ item.issueNumber }}
            </span>
            <span v-if="item.producer">
              <br />
              <strong>Producer:</strong> {{ item.producer }}
            </span>

            <span v-if="item.publisher">
              <br />
              <strong>Publisher:</strong> {{ item.publisher }}
            </span>
            <br />
            <strong>Genre:</strong> {{ item.genre }}
            <br />
            <strong>Publish date:</strong> {{ item.publishDate }}
            <br />
            <span v-if="item.isReservable"
              ><strong>Next Availabile date:</strong>
              {{ item.nextAvailableDate }}</span
            >
          </p>
        </a-layout>
      </a-card>

      <a-button
        @click="
          () => {
            this.$router.push({ name: 'BrowseAllItems' });
          }
        "
        >See more items</a-button
      >
    </div>
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

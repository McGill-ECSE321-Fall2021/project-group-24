<template>
  <!-- @author Saagar Arya
  This page allows anyone to browse through all the items.
It also features a search bar for them to filter items.
Logged in patrons can also reserve items from this screen
Librarians can also delete, checkout items, or return them from reservations.
 -->
  <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
  <div>
    <div id="searchbar">
      <!--This is searchbar from AntDesign-->
      <a-input-search
        placeholder="input search text"
        enter-button="Search"
        @search="search"
      />
    </div>
    <div style="align-self: center; margin: auto; width: 70%">
      <a-card
        v-for="item in results"
        :key="item.itemNumber"
        style="align-items: center; margin-top: 20px"
      >
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
            <span v-if="item.type">
              <br />
              <strong>Type:</strong> {{ item.type }}
            </span>

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
              <br />
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
          <div style="20%">
            <a-button
              v-if="currentUser.username && item.isReservable"
              @click="reservePressed(item)"
              >Reserve item</a-button
            >
            <a-button
              v-if="
                currentUser.isPatron == false &&
                item.isReservable &&
                item.nextAvailableDate == today
              "
              @click="checkoutPressed(item)"
            >
              Checkout Item for Patron
            </a-button>
            <a-button
              v-if="currentUser.isPatron == false && item.isReservable"
              @click="returnItem(item.itemNumber, currentUser.idNum)"
            >
              Return item from a reservation
            </a-button>

            <a-button
              type="danger"
              v-if="currentUser.isPatron == false && item.nextAvailableDate"
              @click="deleteItem(item.itemNumber, currentUser.idNum)"
            >
              Delete item
            </a-button>
          </div>
        </a-layout>
      </a-card>
    </div>
    <br />

    <div id="searchbar">
      <a-modal
        v-model="visible"
        :title="this.error ? 'Error' : 'Message'"
        :footer="null"
        :header="null"
      >
        <a-alert
          v-if="!this.error"
          :message="this.response"
          type="success"
          show-icon
        />
        <a-alert
          v-if="this.error"
          :message="this.error"
          type="error"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
<script src="../js/BrowseAllItems.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

<template>
  <div>
    <div v-if="!loading">
      <div id="searchbar">
        <!--This is searchbar from AntDesign-->
        <!-- <a-input-search
        placeholder="input search text"
        enter-button="Search"
        @search="search"
      /> -->
      </div>

      <div style="align-self: center; margin: auto; width: 70%">
        <a-card
          v-for="reservation in reservations"
          :key="reservation.itemReservationId"
          style="align-items: center; margin-top: 20px"
        >
          <a-card-grid
            style="width: 25%; text-align: center; align-self: center"
            :hoverable="false"
          >
            <img
              v-if="reservation.imageURL"
              :alt="reservation.imageTitle"
              :src="reservation.imageURL"
              style="width: 100%"
            />
            <img
              v-if="!reservation.imageURL"
              :alt="reservation.imageTitle"
              style="width: 100%"
              src="https://islandpress.org/sites/default/files/default_book_cover_2015.jpg"
            />
          </a-card-grid>
          <a-card-grid
            :hoverable="false"
            :bordered="false"
            style="width: 75%; text-align: center"
          >
            <h4>{{ reservation.itemTitle }}</h4>
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
            <p style="text-align: left">{{ reservation.description }}</p>
            <p style="text-align: left">
              <span v-if="reservation.author">
                <br />
                <strong>Author:</strong> {{ reservation.author }}
              </span>
              <span v-if="reservation.artist">
                <br />
                <strong>Artist:</strong>
                {{ reservation.artist }}
              </span>
              <span v-if="reservation.publisher">
                <br />
                <strong>Publisher:</strong> {{ reservation.publisher }}
              </span>
              <span v-if="reservation.recordingLabel">
                <br />
                <strong>Recording Label:</strong>
                {{ reservation.recordingLabel }}
              </span>
              <span v-if="reservation.productionCompany">
                <strong>Production Company:</strong>
                {{ reservation.productionCompany }}
              </span>
              <span v-if="reservation.movieCase">
                <br />
                <strong>Movie Cast:</strong> {{ reservation.movieCase }}
              </span>
              <span v-if="reservation.director">
                <br />
                <strong>Director:</strong> {{ reservation.director }}
              </span>
              <span v-if="reservation.issueNumber">
                <br />
                <strong>Issue Number:</strong> {{ reservation.issueNumber }}
              </span>
              <span v-if="reservation.producer">
                <br />
                <strong>Producer:</strong> {{ reservation.producer }}
              </span>

              <span v-if="reservation.publisher">
                <br />
                <strong>Publisher:</strong> {{ reservation.publisher }}
              </span>
              <br />
              <strong>Genre:</strong> {{ reservation.genre }}
              <br />
              <strong>Publish date:</strong> {{ reservation.publishDate }}
              <br />
              <strong>Reservation Start Date:</strong>
              {{ reservation.startDate }}
              <br />
              <strong>Reservation end date</strong> {{ reservation.endDate }}
              <br />
              <strong>Checked out?</strong> {{ reservation.isCheckedOut }}
              <br />
              <strong>Num of renewals left:</strong>
              {{ reservation.numOfRenewalsLeft }}
              <br />
              <strong>Reserved for:</strong> {{ reservation.idNum }}
              <br />
            </p>
            <div style="20%">
              <a-button type="dashed"> Renew </a-button>
            </div>
          </a-layout>
        </a-card>
      </div>
      <br />
      <div id="searchbar">
        <!-- <a-modal v-model="visible" title="Error" :footer="null" :header="null">
        <a-alert
          v-if="this.roomError"
          message=" "
          :description="this.roomError"
          type="error"
          show-icon
        />
        <a-alert
          v-if="!this.roomError"
          message=" "
          description="There was an unexpected error"
          type="error"
          show-icon
        />
      </a-modal> -->
      </div>
    </div>
    <div v-if="loading" style="align-self: center; margin: auto; width: 70%">
      <a-card style="align-items: center; margin-top: 20px"
        ><a-skeleton /><a-skeleton /></a-card
      ><a-card style="align-items: center; margin-top: 20px"
        ><a-skeleton /><a-skeleton
      /></a-card>
      <a-card style="align-items: center; margin-top: 20px"
        ><a-skeleton /><a-skeleton
      /></a-card>
    </div>
  </div>
</template>

<script src="../js/BrowseItemReservations.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

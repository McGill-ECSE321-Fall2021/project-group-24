<template>
  <div>
    <div v-if="!loading">
      <div id="searchbar">
        <!--This is searchbar from AntDesign-->
        <a-input-search
          placeholder="input search text"
          enter-button="Search"
          @search="search"
        />
      </div>

      <div style="align-self: center; margin: auto; width: 70%">
        <h1 v-if="reservationResults.length == 0">No reservations</h1>
        <div v-if="reservationResults.length != 0">
          <a-card
            v-for="(reservation, index) in reservationResults"
            :key="index"
            style="align-items: center; margin-top: 20px"
          >
            <a-card-grid
              style="width: 25%; text-align: center; align-self: center"
              :hoverable="false"
            >
              <img
                v-if="itemResults[index].imageURL"
                :alt="itemResults[index].imageTitle"
                :src="itemResults[index].imageURL"
                style="width: 100%"
              />
              <img
                v-if="!itemResults[index].imageURL"
                :alt="itemResults[index].imageTitle"
                style="width: 100%"
                src="https://islandpress.org/sites/default/files/default_book_cover_2015.jpg"
              />
            </a-card-grid>
            <a-card-grid
              :hoverable="false"
              :bordered="false"
              style="width: 75%; text-align: center"
            >
              <h4>{{ itemResults[index].itemTitle }}</h4>
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
              <p style="text-align: left">
                {{ itemResults[index].description }}
              </p>
              <p style="text-align: left">
                <span v-if="itemResults[index].author">
                  <br />
                  <strong>Author:</strong> {{ itemResults[index].author }}
                </span>
                <span v-if="itemResults[index].artist">
                  <br />
                  <strong>Artist:</strong>
                  {{ itemResults[index].artist }}
                </span>
                <span v-if="itemResults[index].publisher">
                  <br />
                  <strong>Publisher:</strong> {{ itemResults[index].publisher }}
                </span>
                <span v-if="itemResults[index].recordingLabel">
                  <br />
                  <strong>Recording Label:</strong>
                  {{ itemResults[index].recordingLabel }}
                </span>
                <span v-if="itemResults[index].productionCompany">
                  <strong>Production Company:</strong>
                  {{ itemResults[index].productionCompany }}
                </span>
                <span v-if="itemResults[index].movieCase">
                  <br />
                  <strong>Movie Cast:</strong>
                  {{ itemResults[index].movieCase }}
                </span>
                <span v-if="itemResults[index].director">
                  <br />
                  <strong>Director:</strong> {{ itemResults[index].director }}
                </span>
                <span v-if="itemResults[index].issueNumber">
                  <br />
                  <strong>Issue Number:</strong>
                  {{ itemResults[index].issueNumber }}
                </span>
                <span v-if="itemResults[index].producer">
                  <br />
                  <strong>Producer:</strong> {{ itemResults[index].producer }}
                </span>

                <span v-if="itemResults[index].publisher">
                  <br />
                  <strong>Publisher:</strong> {{ itemResults[index].publisher }}
                </span>
                <br />
                <strong>Genre:</strong> {{ itemResults[index].genre }}
                <br />
                <strong>Publish date:</strong>
                {{ itemResults[index].publishDate }}
                <br />
                <strong>Reservation Start Date:</strong>
                {{ reservation.startDate }}
                <br />
                <strong>Reservation end date</strong>
                {{ reservation.endDate }}
                <br />
                <strong>Checked out?</strong>
                {{ reservation.isCheckedOut }}
                <br />
                <strong>Num of renewals left:</strong>
                {{ reservation.numOfRenewalsLeft }}
                <br />
                <strong>Reserved for:</strong> {{ reservation.idNum }}
                <br />
              </p>
              <div style="20%">
                <a-button
                  type="dashed"
                  @click="
                    renew(
                      reservation.itemReservationId,
                      currentUser.idNum,
                      index
                    )
                  "
                >
                  Renew
                </a-button>
                <a-button
                  v-if="!reservation.isCheckedOut"
                  type="danger"
                  @click="
                    cancelReservation(
                      reservation.itemReservationId,
                      currentUser.idNum,
                      index
                    )
                  "
                >
                  Cancel Reservation
                </a-button>
              </div>
            </a-layout>
          </a-card>
        </div>
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
        ><a-skeleton active /><a-skeleton active /></a-card
      ><a-card style="align-items: center; margin-top: 20px"
        ><a-skeleton active /><a-skeleton active
      /></a-card>
      <a-card style="align-items: center; margin-top: 20px"
        ><a-skeleton active /><a-skeleton active
      /></a-card>
    </div>
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

<script src="../js/BrowseItemReservations.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

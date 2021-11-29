@@ -0,0 +1,87 @@
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
        <h1 v-if="patronResults.length == 0">
          <p />
          No patrons
        </h1>
        <div v-if="patronResults.length != 0">
          <a-card
            v-for="(patron, index) in patronResults"
            :key="index"
            style="align-rooms: center; margin-top: 20px"
            :title="patron.firstName + ' ' + patron.lastName"
          >
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
                <span>
                  <br />
                  <strong>Username:</strong>
                  {{ patron.username }}
                </span>
                <span v-if="!currentUser.isPatron">
                  <br />
                  <strong>Patron id:</strong>
                  {{ patron.idNum }}
                </span>
                <span>
                  <br />
                  <strong>First name:</strong>
                  {{ patron.firstName }}
                </span>
                <span>
                  <br />
                  <strong>Last name:</strong>
                  {{ patron.lastName }}
                </span>
                <span>
                  <br />
                  <strong>Address:</strong>
                  {{ patron.address }}
                </span>

                <span>
                  <br />
                  <strong>Registered Online:</strong>
                  {{ patron.registeredOnline }}
                </span>
                <span>
                  <br />
                  <strong>Verified:</strong>
                  {{ patron.verified }}
                </span>
                <span>
                  <br />
                  <strong>Resident:</strong>
                  {{ patron.resident }}
                </span>
              </p>
              <div style="20%">
                <a-button type="primary" @click="verfiyPatron()">
                  Verify Patron
                </a-button>
                <a-button type="danger" @click="deletePatron">
                  Delete Patron
                </a-button>
              </div>
            </a-layout>
          </a-card>
        </div>
      </div>
      <br />
    </div>
    <div v-if="loading" style="align-self: center; margin: auto; width: 70%">
      <a-card style="align-rooms: center; margin-top: 20px"
        ><a-skeleton active /><a-skeleton active /></a-card
      ><a-card style="align-rooms: center; margin-top: 20px"
        ><a-skeleton active /><a-skeleton active
      /></a-card>
      <a-card style="align-rooms: center; margin-top: 20px"
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

<script src="../js/ViewAllPatrons.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

<template>
  <div>
    <!-- author: Selena and Nafis
    this page allows librarians and head librarian to see the list of patrons (in person and online), and allows them to delete a patron or verify a patron -->
    <div v-if="!loading">
      <div id="searchbar">
        <!--This is searchbar from AntDesign-->
        <a-input-search
          placeholder="input search text"
          enter-button="Search"
          @search="search"
        />
      </div>
      <!-- shows no patrons when there's no patrons -->
      <div style="align-self: center; margin: auto; width: 70%">
        <h1 v-if="patronResults.length == 0">
          <p />
          No patrons
        </h1>
        <!-- display list of patrons as list of Cards
        each Card has information about the patron such as username, first and last name, address, whether or not the patron is verified,
        password is hidden from the libraian for security purposes -->
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
                  <strong>User ID:</strong>
                  {{ patron.idNum }}
                </span>
                <span>
                  <br />
                  <strong>First Name:</strong>
                  {{ patron.firstName }}
                </span>
                <span>
                  <br />
                  <strong>Last Name:</strong>
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
              <!-- buttons for verifying and deleting a patron -->
              <div style="20%">
                <a-button type="primary" @click="verifyPatron(patron.idNum)">
                  Verify Patron
                </a-button>

                <a-popconfirm
                  title="Are you sure you want to delete this patron?"
                  ok-text="Yes"
                  cancel-text="No"
                  @confirm="deletePatron(patron.idNum)"
                >
                  <a-button type="danger"> Delete Patron </a-button>
                </a-popconfirm>
              </div>
            </a-layout>
          </a-card>
        </div>
      </div>
      <br />
    </div>
    <!-- skeleton for loading the patrons from the database -->
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

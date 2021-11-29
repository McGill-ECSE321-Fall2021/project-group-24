<template>
  <!-- Shows all librarians. 
The user (a librarian or the head-librarian) can either scroll through the cards or use the search bar
Librarians cannot view each other's usernames or id, but the head-librarian can.
Head-librarian also has buttons to add and delete librarians. 
Every librarian can view each other's shifts
-->
  <div>
    <div v-if="!loading">
      <div id="searchbar">
        <a-input-search
          placeholder="input search text"
          enter-button="Search"
          @search="search"
        />
      </div>

      <div style="align-self: center; margin: auto; width: 70%">
        <h1 v-if="librarianResults.length == 0">
          <p />
          There Are No Librarians
        </h1>
        <div v-if="librarianResults.length != 0">
          <a-card
            v-for="(librarian, index) in librarianResults"
            :key="index"
            style="align-rooms: center; margin-top: 20px"
            :title="librarian.firstName + ' ' + librarian.lastName"
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
                <span v-if="!currentUser.isLibrarian">
                  <br />
                  <strong>Username:</strong>
                  {{ librarian.username }}
                </span>
                <span v-if="!currentUser.isLibrarian">
                  <br />
                  <strong>Librarian id:</strong>
                  {{ librarian.idNum }}
                </span>
                <span>
                  <br />
                  <strong>First name:</strong>
                  {{ librarian.firstName }}
                </span>
                <span>
                  <br />
                  <strong>Last name:</strong>
                  {{ librarian.lastName }}
                </span>
                <span>
                  <br />
                  <strong>Address:</strong>
                  {{ librarian.address }}
                </span>

                <span>
                  <br />
                  <strong>Email :</strong>
                  {{ librarian.email }}
                </span>
              </p>
              <div style="20%">
                <a-button type="primary">
                  <router-link :to="{ name: 'BrowseAllShifts' }">
                    View Shifts
                  </router-link>
                </a-button>
                <a-button
                  v-if="!currentUser.isLibrarian"
                  type="danger"
                  @click="deleteLibrarian()"
                >
                  Delete Librarian
                </a-button>
              </div>
            </a-layout>
          </a-card>
        </div>
      </div>
      <br />
      <div>
        <a-button v-if="!currentUser.isLibrarian" type="primary">
          <router-link :to="{ name: 'AddLibrarian' }">
            Add Librarian
          </router-link>
        </a-button>
      </div>
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
<script src="../js/ViewLibrarians.js"></script>

<template>
  <!-- @Author Saagar
  In this view, anyone can browse rooms to see information about them.
Librarians can also delete or modify rooms. -->
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
        v-for="room in results"
        :key="room.roomNum"
        style="align-items: center; margin-top: 20px"
      >
        <h4>{{ room.roomNum }}</h4>

        <p><strong>Capacity: </strong>{{ room.capacity }}</p>

        <div style="20%">
          <a-button v-if="currentUser.username" @click="reservePressed(room)">
            Reserve room
          </a-button>
          <a-button
            type="dashed"
            @click="updatePressed(room)"
            v-if="currentUser.username && !currentUser.isPatron"
            >Update room</a-button
          >
          <a-button
            type="danger"
            v-if="currentUser.username && !currentUser.isPatron"
            @click="deleteRoom(room.roomNum, currentUser.username)"
          >
            Delete room
          </a-button>
        </div>
      </a-card>
    </div>
    <br />
    <div id="searchbar">
      <a-modal v-model="visible" title="Error" :footer="null" :header="null">
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
      </a-modal>
    </div>
  </div>
</template>
<script src="../js/BrowseAllRooms.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

<template>
  <div id="form">
    <div id="input">
      <h2>Create Rooms</h2>
    </div>

    <div id="form">
      <a-form
        :form="form"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleSubmit"
      >
        <a-form-item label="Room Number">
          <a-input
            v-decorator="[
              'roomNum',
              {
                rules: [{ required: true, message: 'Please input your note!' }],
              },
            ]"
          />
        </a-form-item>
        <a-form-item label="Capacity">
          <a-input
            v-decorator="[
              'capacity',
              {
                rules: [{ required: true, message: 'Please input your note!' }],
              },
            ]"
          />
        </a-form-item>
        <a-form-item label="Current User ID">
          <a-input
            v-decorator="[
              'currentUserId',
              {
                rules: [{ required: true, message: 'Please input your note!' }],
              },
            ]"
          />
        </a-form-item>

        <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
          <a-button type="primary" html-type="submit"> Submit </a-button>
        </a-form-item>
      </a-form>
    </div>
    <div id="input">
      <router-link :to="{ name: 'BrowseAllRooms' }"
        >Go To Browse Rooms!</router-link
      >
    </div>
    <div id="searchbar">
      <a-modal v-model="visible" title=" " :footer="null" :header="null">
        <a-alert
          v-if="this.responseStatus == '200'"
          message="CREATED ROOM"
          type="success"
          show-icon
        />
        <a-alert
          v-if="this.responseStatus != '200'"
          :message="this.roomError"
          type="error"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
<script src="../js/CreateNewRoom.js"></script>
<style>
#input {
  justify-content: center;
  align-items: center;
  align-content: center;
  justify-items: center;
}
#title {
  font-size: 100%;
}
#form {
  justify-content: center;
  align-items: center;
  align-content: center;
  justify-items: center;
  margin-left: 100px;
}
</style>

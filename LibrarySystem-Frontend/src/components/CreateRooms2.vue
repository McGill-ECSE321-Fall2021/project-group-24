<template>
  <div>
    <div id="input">
      <h2>Create Rooms</h2>
    </div>

    <div>
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
    <div>
      <li>
        <router-link :to="{ name: 'BrowseRooms' }"
          >Go To Browse Rooms!</router-link
        >
      </li>
    </div>
    <div id="searchbar">
      <a-spin v-model="visible" spinning="this.responseStatus == ''" />
      <a-modal v-model="visible" title="Alert" :footer="null" :header="null">
        <a-alert
          v-if="this.responseStatus == '200'"
          message="CREATED ROOM"
          type="success"
          show-icon
        />
        <a-alert
          v-if="this.responseStatus != '200'"
          message="Wrong"
          type="error"
          show-icon
        />
        <a-alert
          v-if="this.roomError"
          message=""
          description="There was an unexpected error"
          type="error"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
<script src="./CreateRooms.js"></script>
<style>
#input {
  padding-left: 20%;
  padding-right: 20%;
}
#title {
  font-size: 100%;
}
</style>

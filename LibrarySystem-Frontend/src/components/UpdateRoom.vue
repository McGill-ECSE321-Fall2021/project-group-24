<template>
  <div>
    <h1>Update Room:</h1>
    <a-card :key="room.roomNum" style="align-items: center; margin-top: 20px">
      <h4>{{ room.roomNum }}</h4>

      <p><strong>Capacity: </strong>{{ room.capacity }}</p>
    </a-card>

    <div style="padding-right: 20%; padding-left: 20%">
      <a-form :form="form" @submit="handleSubmit">
        <a-form-item>
          <a-input
            :disabled="this.currentUser.isPatron"
            :placeholder="'Room Capacity'"
            v-decorator="[
              'capacity',
              {
                rules: [
                  {
                    required: true,
                    message: 'Input room capacity!',
                  },
                ],
              },
            ]"
          >
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button html-type="submit" type="primary"
            >Confirm Room Update</a-button
          >
        </a-form-item>
      </a-form>
      <a-modal
        v-model="visible"
        :title="this.error ? 'Error' : 'Message'"
        :footer="null"
        :header="null"
      >
        <a-alert
          v-if="this.error"
          message=" "
          :description="this.error"
          type="error"
          show-icon
        />
        <a-alert
          v-if="this.response"
          message=" "
          :description="this.response"
          type="success"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>
;

<script src="../js/UpdateRoom.js"></script>
<style>
.business-hours {
  background-color: rgba(169, 255, 140, 0.2);
  border: solid rgba(169, 255, 140, 0.6);
  border-width: 2px 0;
}
.vuecal__event {
  background-color: rgba(255, 148, 148, 0.35);
}
</style>

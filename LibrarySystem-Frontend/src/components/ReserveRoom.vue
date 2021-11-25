<template>
  <div>
    <h1>Reserve room:</h1>
    <a-card :key="room.roomNum" style="align-items: center; margin-top: 20px">
      <h4>{{ room.roomNum }}</h4>

      <p><strong>Capacity: </strong>{{ room.capacity }}</p>
    </a-card>
    <h1>For patron:</h1>
    <div style="padding-right: 20%; padding-left: 20%">
      <a-form :form="form" @submit="handleSubmit">
        <a-form-item>
          <a-input
            :disabled="this.currentUser.isPatron"
            :placeholder="
              this.currentUser.isPatron
                ? this.currentUser.idNum
                : 'Patron\'s idNumber'
            "
            v-decorator="[
              'idNum',
              {
                rules: [
                  {
                    required: !this.currentUser.isPatron,
                    message: 'Input patron idNum!',
                  },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="user" />
          </a-input>
        </a-form-item>
        <h1>For date:</h1>
        <a-form-item>
          <a-input
            placeholder="Booking Date"
            v-decorator="[
              'date',
              {
                rules: [
                  {
                    required: true,
                    message: 'Input date!',
                  },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="date" />
          </a-input>
        </a-form-item>
        <h1>Start time:</h1>
        <a-form-item>
          <a-input
            placeholder="Start time"
            v-decorator="[
              'startTime',
              {
                rules: [
                  {
                    required: true,
                    message: 'Input start time!',
                  },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="clock" />
          </a-input>
        </a-form-item>
        <h1>End time:</h1>
        <a-form-item>
          <a-input
            placeholder="End time"
            v-decorator="[
              'endTime',
              {
                rules: [
                  {
                    required: true,
                    message: 'Input end time!',
                  },
                ],
              },
            ]"
          >
            <a-icon slot="prefix" type="clock" />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button html-type="submit">Confirm room booking</a-button>
        </a-form-item>
      </a-form>
      <a-modal
        v-model="modalVisible"
        title="Error"
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

<script src="../js/ReserveRoom.js"></script>
;

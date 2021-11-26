<template>
  <div>
    <h1>Reserve room:</h1>
    <a-card :key="room.roomNum" style="align-items: center; margin-top: 20px">
      <h4>{{ room.roomNum }}</h4>

      <p><strong>Capacity: </strong>{{ room.capacity }}</p>
    </a-card>

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
        <vue-cal
          :disable-views="['day', 'years', 'year', 'month']"
          hide-view-selector
          style="height: 500px"
          :minDate="this.today"
          :special-hours="this.libraryHours"
          :hide-weekdays="this.daysToHide"
          :events="this.events"
          :editable-events="{
            title: true,
            drag: false,
            resize: true,
            delete: true,
            create: false,
          }"
        />
        <a-date-picker @change="changeDate" :disabled-date="disabledEndDate" />
        <a-time-picker
          :default-open-value="moment('00:00:00', 'HH:mm:ss')"
          use12-hours
          format="h:mm a"
          @change="changeStartTime"
          :minute-step="15"
        />
        <a-time-picker
          :default-open-value="moment('00:00:00', 'HH:mm:ss')"
          use12-hours
          format="h:mm a"
          @change="changeEndTime"
          :minute-step="15"
        />
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

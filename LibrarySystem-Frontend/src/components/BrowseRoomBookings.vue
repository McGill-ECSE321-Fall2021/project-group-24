<template>
  <!-- author: Selena
This page allows patrons to browse their own room bookings,
while librarians can see all room bookings. -->
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
      <!-- display all roombookings as a list of Cards -->
      <div style="align-self: center; margin: auto; width: 70%">
        <!-- when there are no room bookings -->
        <h1 v-if="roombookingResults.length == 0">
          <p />
          No room bookings
        </h1>
        <div v-if="roombookingResults.length != 0">
          <!-- each card contains information about one roombooking, such as room number, date, start and end time  -->
          <a-card
            v-for="(roombooking, index) in roombookingResults"
            :key="index"
            style="align-rooms: center; margin-top: 20px"
            :title="'Room Booking ' + (index + 1)"
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
                  <strong>Room:</strong>
                  {{ roombooking.roomNum }}
                </span>
                <span v-if="!currentUser.isPatron">
                  <br />
                  <strong>Patron id:</strong>
                  {{ roombooking.idNum }}
                </span>
                <span>
                  <br />
                  <strong>Date:</strong>
                  {{ roombooking.date }}
                </span>
                <span>
                  <br />
                  <strong>Start time:</strong>
                  {{ roombooking.startTime.substring(0, 5) }}
                </span>
                <span>
                  <br />
                  <strong>End time:</strong>
                  {{ roombooking.endTime.substring(0, 5) }}
                </span>

                <span>
                  <br />
                  <strong>Day:</strong> {{ roombooking.dayOfWeek }}
                </span>
              </p>
              <!-- modify and delete buttons -->
              <div style="20%">
                <a-button
                  type="primary"
                  @click="
                    showModal(roombooking.roomNum, roombooking.timeSlotId)
                  "
                >
                  Modify
                </a-button>
                <!-- modify button brings up a modal that shows the avaliability of the room, and input fields for data, start time and end time -->
                <a-modal
                  title="Modify Room booking"
                  :footer="null"
                  :visible="visible"
                  :confirm-loading="confirmLoading"
                  @cancel="handleCancel"
                >
                  <a-form :form="form" @submit="handleSubmit">
                    <vue-cal
                      :disable-views="['day', 'years', 'year', 'month']"
                      hide-view-selector
                      style="height: 500px"
                      :special-hours="libraryHours"
                      :minDate="today"
                      :hide-weekdays="daysToHide"
                      :events="events"
                      :editable-events="{
                        title: false,
                        drag: false,
                        resize: true,
                        delete: true,
                        create: false,
                      }"
                    />
                    <!-- data and time picker from AntDesign -->
                    <a-date-picker
                      @change="changeDate"
                      :disabled-date="disabledEndDate"
                    />
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
                      <a-button html-type="submit"
                        >Modify room booking</a-button
                      >
                    </a-form-item>
                    <!-- hidden elements are used to send information to the js file -->
                    <a-form-item
                      style="
                        width: 0;
                        margin-top: 0;
                        margin-bottom: 0;
                        height: 0;
                        opacity: 0;
                      "
                    >
                      <a-input
                        disabled
                        v-decorator="[
                          'timeSlotId',
                          {
                            rules: [{ required: false, message: '' }],
                          },
                        ]"
                        :placeholder="roombooking.timeSlotId"
                      />
                    </a-form-item>
                    <a-form-item
                      style="
                        width: 0;
                        margin-top: 0;
                        margin-bottom: 0;
                        height: 0;
                        opacity: 0;
                      "
                    >
                      <a-input
                        disabled
                        v-decorator="[
                          'roomNum',
                          {
                            rules: [{ required: false, message: '' }],
                          },
                        ]"
                        :placeholder="roombooking.roomNum"
                      />
                    </a-form-item>
                  </a-form>
                  <!-- modal for error message or confirmation that a roombooking is modified -->
                  <a-modal
                    v-model="modalVisible"
                    :title="error ? 'Error' : 'Message'"
                    :footer="null"
                    :header="null"
                  >
                    <a-alert
                      v-if="error"
                      message=" "
                      :description="error"
                      type="error"
                      show-icon
                    />
                    <a-alert
                      v-if="response"
                      message=" "
                      :description="response"
                      type="success"
                      show-icon
                    />
                  </a-modal>
                </a-modal>
                <!-- button for deleting room booking -->
                <a-button
                  type="danger"
                  @click="
                    cancelroombooking(
                      currentUser.idNum,
                      roombooking.timeSlotId,
                      index
                    )
                  "
                >
                  Cancel
                </a-button>
              </div>
            </a-layout>
          </a-card>
        </div>
      </div>
      <br />
    </div>
    <!-- graphics for loading data -->
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

<script src="../js/BrowseRoomBookings.js"></script>
<style>
/* styles for the search bar and colors on the calendar */
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
.business-hours {
  background-color: rgba(169, 255, 140, 0.2);
  border: solid rgba(169, 255, 140, 0.6);
  border-width: 2px 0;
}
.vuecal__event {
  background-color: rgba(255, 148, 148, 0.35);
}
</style>

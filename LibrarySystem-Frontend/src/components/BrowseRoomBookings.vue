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
        <h1 v-if="roombookingResults.length == 0">
          <p />
          No room bookings
        </h1>
        <div v-if="roombookingResults.length != 0">
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
                <span>
                  <br />
                  <strong>Date:</strong>
                  {{ roombooking.date }}
                </span>
                <span>
                  <br />
                  <strong>Start time:</strong>
                  {{ roombooking.startTime }}
                </span>
                <span>
                  <br />
                  <strong>End time:</strong>
                  {{ roombooking.endTime }}
                </span>
                <span>
                  <br />
                  <strong>Day:</strong> {{ roombooking.dayOfWeek }}
                </span>
              </p>
              <div style="20%">
                <div>
                  <a-button type="primary" @click="showModal">
                    Modify
                  </a-button>
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
                        :special-hours="this.libraryHours"
                        :minDate="this.today"
                        :hide-weekdays="this.daysToHide"
                        :events="this.events"
                        :editable-events="{
                          title: false,
                          drag: false,
                          resize: true,
                          delete: true,
                          create: false,
                        }"
                      />
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
                          >Confirm room booking</a-button
                        >
                      </a-form-item>
                    </a-form>
                    <a-modal
                      v-model="modalVisible"
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
                  </a-modal>
                </div>
                <!-- <a-button
                  type="dashed"
                  @click="
                    renew(
                      roombooking.roomroombookingId,
                      currentUser.idNum,
                      index
                    )
                  "
                >
                  Modify
                </a-button> -->
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
      <div id="searchbar">
        <!-- <a-modal v-model="visible" title="Error" :footer="null" :header="null">
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
      </a-modal> -->
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

<script src="../js/BrowseRoomBookings.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

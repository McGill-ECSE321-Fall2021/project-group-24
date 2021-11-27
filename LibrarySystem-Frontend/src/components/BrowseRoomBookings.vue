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
                    @ok="handleOk"
                    @cancel="handleCancel"
                  >
                    <a-form
                      :form="form"
                      :label-col="{ span: 6 }"
                      :wrapper-col="{ span: 12 }"
                      @submit="handleSubmit"
                    >
                      <a-form-item label="new date">
                        <!-- TODO: add date picker -->
                        <!-- TODO: add time picker -->
                        <!-- TODO: add room selector -->

                        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
                          <a-button type="primary" html-type="submit">
                            Log In
                          </a-button>
                        </a-form-item>
                      </a-form-item>
                    </a-form>
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

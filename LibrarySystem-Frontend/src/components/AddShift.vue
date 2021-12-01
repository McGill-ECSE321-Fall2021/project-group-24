<template>
  <div>
    <!-- Head-librarian selects the librarian and day of week from the given options, then they input a start and end time. 
        If there's no errors, they will be sent back to the "BrowseAllShifts" webpage. 
        Otherwise, an alert will pop up to notify them of the error.
      -->

    <a-form
      :form="form"
      :label-col="{ span: 7 }"
      :wrapper-col="{ span: 12 }"
      @submit="handleSubmit"
    >
      <!-- Select Day of Week -->
      <a-form-item label="Day Of Week">
        <a-select
          v-decorator="[
            'dayOfWeek',
            { rules: [{ required: true, message: 'Please select day.' }] },
          ]"
          placeholder="Please choose an option."
        >
          <a-select-option value="MONDAY"> MONDAY </a-select-option>
          <a-select-option value="TUESDAY"> TUESDAY </a-select-option>
          <a-select-option value="WEDNESDAY"> WEDNESDAY </a-select-option>
          <a-select-option value="THURSDAY"> THURSDAY </a-select-option>
          <a-select-option value="FRIDAY"> FRIDAY </a-select-option>
          <a-select-option value="SATURDAY"> SATURDAY </a-select-option>
          <a-select-option value="SUNDAY"> SUNDAY </a-select-option>
        </a-select>
      </a-form-item>

      <!-- Select Librarian -->

      <a-form-item label="Select Librarian">
        <a-select
          v-decorator="[
            'librarian',
            {
              rules: [{ required: true, message: 'Please select Librarian.' }],
            },
          ]"
          placeholder="Please choose a librarian."
        >
          <a-select-option
            v-for="librarian in librarians"
            v-bind:key="librarian.idNum"
            :value="librarian.idNum"
          >
            {{ librarian.firstName + " " + librarian.lastName }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="Starting Time">
        <a-input
          v-decorator="[
            'startTime',
            {
              rules: [
                { required: true, message: 'Please enter the starting time.' },
              ],
            },
          ]"
        />
      </a-form-item>

      <a-form-item label="Ending Time">
        <a-input
          v-decorator="[
            'endTime',
            {
              rules: [
                { required: true, message: 'Please enter the ending time.' },
              ],
            },
          ]"
        />
      </a-form-item>
      <!-- Submit button -->
      <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
        <a-button type="primary" html-type="submit"> Add Shift </a-button>
      </a-form-item>
    </a-form>
    <!-- Button to go back to the "View Librarians" webpage without submitting anything-->
    <div>
      <router-link :to="{ name: 'ViewLibrarians' }">
        Back to View Librarians
      </router-link>
    </div>
  </div>
</template>
<script src="../js/AddShift.js"></script>
<style scoped>
.white--text /deep/ label {
  color: white;
}
</style>

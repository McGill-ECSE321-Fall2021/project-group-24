<template>
  <div>
    <!-- Displays all of the selected librarian's shifts -->
    <h5 id="special header">
      {{ librarian.firstName + " " + librarian.lastName + "'s" + " Shifts" }}
    </h5>

    <h6 v-if="shifts.length == 0">No Shifts</h6>
    <div v-for="shift in shifts" :key="shift.timeSlotId">
      <h6>
        {{ shift.dayOfWeek[0] + shift.dayOfWeek.slice(1).toLowerCase() }}:
        {{ shift.startTime.substring(0, 5) }} -
        {{ shift.endTime.substring(0, 5) }}
      </h6>
    </div>

    <!-- Head-librarian has buttons enabling them to add & delete shifts -->
    <div>
      <a-button
        type="primary"
        key="AddShift"
        v-if="currentUser.username=='admin'"
      >
        <router-link :to="{ name: 'AddShift' }"> Add Shift </router-link>
      </a-button>
      <a-button
        type="primary"
        key="RemoveShift"
        v-if="currentUser.username=='admin'"
        @click="removeShiftPressed(librarian)"
      >
        Remove Shift
      </a-button>
    </div>

    <!-- Link to go back to previous webpage -->
    <div>
      <router-link :to="{ name: 'ViewLibrarians' }"
        >Back to View Librarians
      </router-link>
    </div>
  </div>
</template>
<style scoped>
#specialheader {
  padding-bottom: 10px;
}
</style>
<script src="../js/ViewLibrarianShifts.js"></script>

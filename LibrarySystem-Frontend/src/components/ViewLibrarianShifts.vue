<template>
    <div> 
        <!-- Displays all of the librarian's shifts -->
        <h5 v-if="shifts.length == 0">No Shifts</h5>
        <div v-for="shift in shifts" :key="shift.timeSlotId">
            <h5>
                {{ shift.dayOfWeek[0] + shift.dayOfWeek.slice(1).toLowerCase() }}:
                {{ shift.startTime.substring(0, 5) }} -
                {{ shift.endTime.substring(0, 5) }}
            </h5>
        </div>

        <!-- Head-librarian can add, delete, & modify shifts -->
        <div> 
            <a-button type="primary" key="AddShift" v-if="currentUser.username && !currentUser.isLibrarian"> 
                <router-link :to="{ name: 'AddShift' }">
                    Add Shift
                </router-link>
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
<script src="../js/ViewLibrarianShifts.js"></script>

<template>
    <div >
    <!-- Head-librarian selects the shift they want to delete for the librarian they previously selected     -->
    
    <h5>Remove {{librarian.firstName + " " +librarian.lastName + "'s" + " Shifts"}} </h5>
        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >   
            <!-- Select Shift to Remove -->    
            <a-form-item label="Select Shift to Remove">
                <a-select
                    v-decorator="[
                        'shift',
                        { rules: [{ required: true, message: 'Please Select Shift.' }] },
                    ]"
                    
                    placeholder="Please choose a shift."
                >
                    <a-select-option v-for="shift in shifts" v-bind:key="shift.timeSlotId" :value="shift.timeSlotId"  >
                        {{shift.dayOfWeek + "  " + shift.startTime + "-" + shift.endTime}}
                    </a-select-option>
                </a-select>
            </a-form-item>  

            <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
                <a-button type="primary" html-type="submit"> Remove Shift </a-button>
            </a-form-item>
        </a-form>

        <!-- Back to the librarian's shifts button -->
        <a  @click="viewShiftsPressed(librarian)">
            View Shifts
        </a>

    </div>
</template>
<script src="../js/RemoveShift.js"></script>
<style scoped>
    .a {
        color: blue;
        text-decoration: underline; 
    }
    a:hover {
        color: blue; 
        text-decoration: underline; 
    }
    .h5 {
        padding-bottom: 10px;
    }
</style> 
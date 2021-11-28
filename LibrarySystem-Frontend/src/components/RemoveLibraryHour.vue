<template>
    <div >
    <!--Form allows user to only select days for which a library hour exists (can't remove what doesn't exist) -->
    
        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >   
                <a-form-item label="Day Of Week">
                <a-select
                    v-decorator="[
                        'dayOfWeek',
                        { rules: [{ required: true, message: 'Please select day.' }] },
                    ]"
                    
                    placeholder="Please choose an option."
                   
                >
                    <a-select-option v-if="daysOfWeek[0]==true" value= "MONDAY" >
                        MONDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[1]==true" value= "TUESDAY">
                       TUESDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[2]==true" value= "WEDNESDAY">
                        WEDNESDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[3]==true" value= "THURSDAY">
                       THURSDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[4]==true" value= "FRIDAY">
                        FRIDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[5]==true" value= "SATURDAY">
                       SATURDAY
                    </a-select-option>
                    <a-select-option v-if="daysOfWeek[6]==true" value= "SUNDAY">
                       SUNDAY
                    </a-select-option>
                </a-select>
            </a-form-item>  

            <a-form-item label="Opening Time" >
                <a-input
                    v-decorator="[
                        'startTime',
                            {
                                rules: [{ required: true, message: 'Please enter the opening time.' }],
                            },
                    ]"
                />
            </a-form-item>

            <a-form-item label="Closing Time" >
                <a-input
                    v-decorator="[
                        'endTime',
                            {
                                rules: [{ required: true, message: 'Please enter the closing time.' }],
                            },
                    ]"
                />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
                <a-button type="primary" html-type="submit"> Add Library Hour </a-button>
            </a-form-item>
        </a-form>

    </div>
</template>
<script src="../js/RemoveLibraryHour.js"></script>
<style scoped>
    .white--text /deep/ label {
    color: white;
    }
</style>
<template>
    <div >
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->

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
                    
                    placeholder="Select Day of Week."
                   
                >
                    <a-select-option value= "MONDAY" >
                        MONDAY
                    </a-select-option>
                    <a-select-option value= "TUESDAY">
                       TUESDAY
                    </a-select-option>
                    <a-select-option value= "WEDNESDAY">
                        WEDNESDAY
                    </a-select-option>
                    <a-select-option value= "THURSDAY">
                       THURSDAY
                    </a-select-option>
                    <a-select-option value= "FRIDAY">
                        FRIDAY
                    </a-select-option>
                    <a-select-option value= "SATURDAY">
                       SATURDAY
                    </a-select-option>
                    <a-select-option value= "SUNDAY">
                       SUNDAY
                    </a-select-option>
                </a-select>
            </a-form-item>  


            <a-form-item label="Opening Time" >
                <a-input
                    v-decorator="[
                        'startTime',
                            {
                                rules: [{ required: true, message: 'Please enter the new opening time.' }],
                            },
                    ]"
                />
            </a-form-item>


            <a-form-item label="Closing Time" >
                <a-input
                    v-decorator="[
                        'endTime',
                            {
                                rules: [{ required: true, message: 'Please enter the new closing time.' }],
                            },
                    ]"
                />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
                <a-button type="primary" html-type="submit"> Modify Library Hour </a-button>
            </a-form-item>
        </a-form>
        <div> 
            <router-link :to="{ name: 'Homepage' }" >
                Back to homepage
            </router-link>
        </div>

    </div>
</template>
<script src="../js/ModifyLibraryHour.js"></script>
<style scoped>
    .white--text /deep/ label {
    color: white;
    }
</style>
<template>
    <div >
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->

        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >
            <a-form-item label="Day Of Week" >
                <a-input
                    v-decorator="[
                        'dayOfWeek',
                            {
                                rules: [{ required: true, message: 'Please enter the day of week.' }],
                            },
                    ]"
                />
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
<script src="../js/AddLibraryHour.js"></script>
<template>
    <div>
        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >

            <a-form-item label="Current Password" has-feedback >
                <a-input-password placeholder="Please enter your current password."
                    v-decorator="[
                        'password',
                            {
                                rules: [{ required: true, message: 'Please input your password!'} , {validator: validateToNextPassword}],
                            },
                    ]"
                    type="password"
                />
            </a-form-item>

            <a-form-item label="New Password" has-feedback >
                <a-input-password placeholder="Please enter new password."
                    v-decorator="[
                        'newPassword',
                            {
                                rules: [{ required: true, message: 'Please input a new password!'} , {validator: validateToNextPassword}],
                            },
                    ]"
                    type="password"
                />
            </a-form-item>


            <a-form-item label="Confirm New Password"  has-feedback>
                 <a-input-password placeholder="Please confirm your new password."
                    v-decorator="[
                        'confirm',
                            {
                                rules: [{required: true, message: 'Please confirm your new password!'}, {validator: compareToFirstPassword}],
                            },
                    ]"
                    type="password"
                    @blur="handleConfirmBlur"
                />
            </a-form-item>

            <div>
                
                <a-form-item :wrapper-col="{ span: 12, offset: 6}">
                <div>
                    <a-button @click="goBack()"> Cancel </a-button>
                    <a-button type="primary" html-type="submit"> Submit </a-button>
                </div>

                </a-form-item>


            </div>    

        </a-form>

        <div>
            <a-modal v-model="visible" title="Error" :footer="null" :header="null">
                <a-alert
                    v-if="this.userError"
                    message=" "
                    :description="this.userError"
                    type="error"
                    show-icon
                />
                <a-alert
                    v-if="!this.userError"
                    message=" "
                    description="There was an unexpected error"
                    type="error"
                    show-icon
                />
            </a-modal>
        </div>
    </div>    
</template>


<script src="../js/ChangePassword.js"></script>
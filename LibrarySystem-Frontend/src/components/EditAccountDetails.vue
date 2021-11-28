<template>
    <div>
       
        
        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >
            <h4>Change Basic Info: </h4>

            <a-form-item :wrapper-col="{ span: 12, offset: 12}">
                <div>
                    <a-button @click="goHome()"> Back </a-button>
                    <a-button type="primary" html-type="submit"> Save </a-button>
                </div>
            </a-form-item>

            <a-form-item label="First Name" >
                <a-input
                    v-decorator="[
                        'firstname',
                            {   
                                initialValue: currentUser.firstName,
                                rules: [{ required: true, message: 'Please enter your first name.' }],
                            },
                             
                    ]"
                />
            </a-form-item>

            <a-form-item label="Last Name" >
                <a-input
                    v-decorator="[
                        'lastname',
                            {
                                initialValue: currentUser.lastName,
                                rules: [{ required: true, message: 'Please enter your last name.' }],
                            },
                    ]"
                />
            </a-form-item>

            <a-form-item label="E-mail" >
                <a-input
                    v-decorator="[
                        'emailinput',
                        {
                            initialValue: currentUser.email,
                            rules: [{type: 'email', message: 'The input is not valid E-mail!'}, {required: true, message: 'Please input your E-mail!'}]
                        }
                    ]"
                />
            </a-form-item>

            <a-form-item label="Address" >
                <a-input
                    v-decorator="[
                        'addressinput',
                        {
                            initialValue: currentUser.address,
                            rules: [{required:true, message: 'This field cannot be empty.'}]
                        }
                    
                    ]"
                />
            </a-form-item>  



            <h4>Security Settings:</h4>
            <a-form-item :wrapper-col="{ span: 12, offset: 6}">
                
                <a-button @click="goToChangePassword()"> Change Password </a-button>
                 
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 6}">
                
                <a-button type="danger" @click="goToDeleteAccount()"> Delete Account </a-button> 

                 
            </a-form-item>

            
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


<script src="../js/EditAccountDetails.js"></script>

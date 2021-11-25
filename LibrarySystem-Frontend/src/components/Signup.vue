
 
<!-- <div style="background-image: url(../static/images/image_1.jpg);"> -->
<template>
  <div style="background-image: url(../static/images/image_1.jpg);">
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
    <div >
        <a-form
            :form="form"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
        >
            <a-form-item label="First Name" class="white--text">
                <a-input
                    v-decorator="[
                        'firstName',
                            {
                                rules: [{ required: true, message: 'Please enter your first name.' }],
                            },
                    ]"
                />
            </a-form-item>


            <a-form-item label="Last Name" class="white--text">
                <a-input
                    v-decorator="[
                        'lastName',
                            {
                                rules: [{ required: true, message: 'Please enter your last name.' }],
                            },
                    ]"
                />
            </a-form-item>


            <a-form-item label="Username" class="white--text">
                <a-input
                    v-decorator="[
                        'username',
                            {
                                rules: [{ required: true, message: 'Please enter a valid username.' }],
                            },
                    ]"
                />
            </a-form-item>


            <a-form-item label="Password" class="white--text" has-feedback >
                <a-input-password
                    v-decorator="[
                        'password',
                            {
                                rules: [{ required: true, message: 'Please input your password!'} , {validator: validateToNextPassword}],
                            },
                    ]"
                    type="password"
                />
            </a-form-item>

            
            <a-form-item label="Confirm Password" class="white--text" has-feedback>
                 <a-input-password
                    v-decorator="[
                        'confirm',
                            {
                                rules: [{required: true, message: 'Please confirm your password!'}, {validator: compareToFirstPassword}],
                            },
                    ]"
                    type="password"
                    @blur="handleConfirmBlur"
                />
            </a-form-item>


            <a-form-item label="E-mail" class="white--text">
                <a-input
                    v-decorator="[
                        'email',
                        {
                        rules: [{type: 'email', message: 'The input is not valid E-mail!'}, {required: true, message: 'Please input your E-mail!'}]
                        }
                    ]"
                />
            </a-form-item>

            <a-form-item label="Address" class="white--text">
                <a-input
                    v-decorator="[
                        'address',
                        {
                            rules: [{required:true, message: 'This field cannot be empty.'}]
                        }
                    
                    ]"
                />
            </a-form-item>        

            
            <a-form-item label="Residency" class="white--text">
                <a-select
                    v-decorator="[
                        'isResident',
                        { rules: [{ required: true, message: 'Please choose an option.' }] },
                    ]"
                    
                    placeholder="Please choose an option."
                   
                >
                    <a-select-option value="true">
                        Is a resident.
                    </a-select-option>
        
                    <a-select-option value="false">
                        Is not a resident.
                    </a-select-option>
                </a-select>
            </a-form-item>  



            <a-form-item :wrapper-col="{ span: 12, offset: 7 }">
                <a-button type="primary" html-type="submit"> Sign Up </a-button>
            </a-form-item>
        </a-form>

    </div>

    <div>
      <router-link :to="{ name: 'LoginPage' }"
        >Don't have an account? Sign up here</router-link
      >
    </div>
    
  </div>
</template>

<script src="../js/Signup.js"></script>

<style scoped>
    .white--text /deep/ label {
    color: white;
    }
</style>

<!-- <div style="background-image: url(../static/images/image_1.jpg);"> -->
<template>
  <div>
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
    <div>
      <a-form
        :form="form"
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleSubmit"
      >
        <a-form-item label="First Name">
          <a-input
            v-decorator="[
              'firstName',
              {
                rules: [
                  { required: true, message: 'Please enter your first name.' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item label="Last Name">
          <a-input
            v-decorator="[
              'lastName',
              {
                rules: [
                  { required: true, message: 'Please enter your last name.' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item label="Username">
          <a-input
            v-decorator="[
              'username',
              {
                rules: [
                  { required: true, message: 'Please enter a valid username.' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item label="Password" has-feedback>
          <a-input-password
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: 'Please input your password!' },
                  { validator: validateToNextPassword },
                ],
              },
            ]"
            type="password"
          />
        </a-form-item>

        <a-form-item label="Confirm Password" has-feedback>
          <a-input-password
            v-decorator="[
              'confirm',
              {
                rules: [
                  { required: true, message: 'Please confirm your password!' },
                  { validator: compareToFirstPassword },
                ],
              },
            ]"
            type="password"
            @blur="handleConfirmBlur"
          />
        </a-form-item>

        <a-form-item label="E-mail">
          <a-input
            v-decorator="[
              'email',
              {
                rules: [
                  { type: 'email', message: 'The input is not valid E-mail!' },
                  { required: true, message: 'Please input your E-mail!' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item label="Address">
          <a-input
            v-decorator="[
              'address',
              {
                rules: [
                  { required: true, message: 'This field cannot be empty.' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item label="Residency">
          <a-select
            v-decorator="[
              'isResident',
              {
                rules: [
                  { required: true, message: 'Please choose an option.' },
                ],
              },
            ]"
            placeholder="Please choose an option."
          >
            <a-select-option value="true">
              I'm a resident of Montreal.
            </a-select-option>

            <a-select-option value="false">
              I'm not a resident of Montreal.
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="primary" html-type="submit"> Sign Up </a-button>
        </a-form-item>
      </a-form>
    </div>

    <div>
      <router-link :to="{ name: 'LoginPage' }"
        >Already have an account? Sign in here</router-link
      >
    </div>

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

<script src="../js/Signup.js"></script>

<style scoped>
.white--text /deep/ label {
  color: white;
}
</style>

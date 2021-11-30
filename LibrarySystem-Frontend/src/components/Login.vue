<template>
  <!-- author: Selena
      this page allowed patrons, librarian and head librarian to sign in -->
  <div>
    <div>
      <!-- form to collect username and password as inputs -->
      <a-form
        :form="form"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleSubmit"
      >
        <a-form-item label="Username">
          <a-input
            v-decorator="[
              'username',
              {
                rules: [
                  { required: true, message: 'Username cannot be empty' },
                ],
              },
            ]"
          />
        </a-form-item>
        <a-form-item label="Password">
          <a-input-password
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: 'Password cannot be empty' },
                ],
              },
            ]"
          />
        </a-form-item>

        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="primary" html-type="submit"> Log In </a-button>
        </a-form-item>
      </a-form>
    </div>
    <!-- a link to the sign in page for patrons that don't have an account -->
    <div>
      <router-link :to="{ name: 'SignupPage' }"
        >Don't have an account? Sign up here</router-link
      >
    </div>
    <!-- error modal: shows the specific error from the backend to inform the user what went wrong, if the backend is not connected, showes "There was an unexpected error" -->
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

<!-- javascript for calling he controlller method -->
<script src="../js/Login.js"></script>

<style scoped></style>

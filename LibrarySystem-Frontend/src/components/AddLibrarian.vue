<template>
  <div key="ADDLIB">
    <!-- Page for the Head-librarian to add a librarian (e.g. a new hire). Inputs are account info -->
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
                  { required: true, message: 'Please enter their first name.' },
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
                  { required: true, message: 'Please enter their last name.' },
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
                  { required: true, message: 'Please input their password!' },
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
                  { required: true, message: 'Please confirm their password!' },
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
                  { required: true, message: 'Please input their E-mail!' },
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
        <!-- Button to submit info when done inputting -->
        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="primary" html-type="submit"> Add Librarian </a-button>
        </a-form-item>
      </a-form>
    </div>
    <!-- Button to go back to the "View Librarians" page -->
    <div>
      <router-link :to="{ name: 'ViewLibrarians' }"
        >Back to View Librarians
      </router-link>
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

<script src="../js/AddLibrarian.js"></script>

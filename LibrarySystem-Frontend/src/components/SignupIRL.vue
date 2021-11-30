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
              Is a resident of Montreal.
            </a-select-option>

            <a-select-option value="false">
              Is not a resident of Montreal.
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="primary" html-type="submit"> Sign Up Patron</a-button>
        </a-form-item>
      </a-form>
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

<script src="../js/SignupIRL.js"></script>

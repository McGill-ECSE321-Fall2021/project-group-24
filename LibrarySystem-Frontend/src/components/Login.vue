<template>
  <div>
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
    <div>
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
                rules: [{ required: true, message: 'Username' }],
              },
            ]"
          />
        </a-form-item>
        <a-form-item label="Password">
          <a-input-password
            v-decorator="[
              'password',
              {
                rules: [{ required: true, message: 'Password' }],
              },
            ]"
          />
        </a-form-item>

        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="primary" html-type="submit"> Log In </a-button>
        </a-form-item>
      </a-form>
    </div>
    <div>
      <router-link :to="{ name: 'SignupPage' }"
        >Don't have an account? Sign up here</router-link
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



  </div>
</template>
<script src="../js/Login.js"></script>

<style scoped></style>

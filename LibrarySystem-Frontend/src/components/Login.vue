<template>
  <div>
    <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
    <div>
      <a-form
        :form="form"
        :label-col="{ span: 5 }"
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

        <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
          <a-button type="primary" html-type="submit"> Log In </a-button>
        </a-form-item>
      </a-form>
    </div>
    <div>
      <router-link :to="{ name: 'SignupPage' }"
        >Don't have an account? Sign up here</router-link
      >
    </div>
  </div>
</template>
<script src="../js/Login.js"></script>

<style scoped></style>

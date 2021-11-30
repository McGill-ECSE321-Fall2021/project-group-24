<template>
  <div>
    <a-form
      :form="form"
      :label-col="{ span: 7 }"
      :wrapper-col="{ span: 12 }"
      @submit="handleSubmit"
    >
      <a-form-item label="User ID">
        <a-input
          placeholder="Please enter your ID number"
          v-decorator="[
            'id',
            {
              rules: [
                {
                  required: true,
                  message: 'Please enter your ID number to continue!',
                },
              ],
            },
          ]"
        />
      </a-form-item>
      <h4>Are you sure you want to delete your account?</h4>
      <div>
        <a-button @click="goBack()"> No, go back </a-button>
        <a-button type="primary" html-type="submit">
          Yes, delete account
        </a-button>
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

<script src="../js/DeleteAccount.js"></script>

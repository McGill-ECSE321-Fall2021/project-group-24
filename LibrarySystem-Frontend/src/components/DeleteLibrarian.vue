<template>
  <div key="DELLIB">
    <!--AHead-Librarian deletes a librarian by entering the desired ID number-->
    <div>
      <a-form
        :form="form"
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleSubmit"
      >
        <a-form-item label="ID Number of librarian to be deleted">
          <a-input
            v-decorator="[
              'idNumber',
              {
                rules: [
                  { required: true, message: 'Please enter their ID number.' },
                ],
              },
            ]"
          />
        </a-form-item>
        <!-- Submit button -->
        <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
          <a-button type="danger" html-type="submit">
            Delete Librarian
          </a-button>
        </a-form-item>
      </a-form>
    </div>
    <!-- Button to return to the "View Librarians" webpage without doing anything -->
    <div>
      <router-link :to="{ name: 'ViewLibrarians' }"
        >Back to View Librarians
      </router-link>
    </div>

    <div>
      <a-modal v-model="visible" title="Error" :footer="null" :header="null">
        <a-alert
          v-if="this.error"
          message=" "
          :description="this.error"
          type="error"
          show-icon
        />
        <a-alert
          v-if="!this.error"
          message=" "
          description="There was an unexpected error"
          type="error"
          show-icon
        />
      </a-modal>
    </div>
  </div>
</template>

<script src="../js/DeleteLibrarian.js"></script>

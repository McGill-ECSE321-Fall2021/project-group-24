<script src="../js/RemoveLibraryHour.js"></script>

<template>
  <div>
    <!--Form allows head-librarian to only select days for which a library hour exists (can't remove what doesn't exist) -->
    <a-form
      :form="form"
      :label-col="{ span: 7 }"
      :wrapper-col="{ span: 12 }"
      @submit="handleSubmit"
    >
      <a-form-item label="Remove the Library Hour for the Following Day">
        <a-select
          v-decorator="[
            'dayOfWeek',
            { rules: [{ required: true, message: 'Please select day.' }] },
          ]"
          placeholder="Please choose an option."
        >
          <a-select-option value="MONDAY"> MONDAY </a-select-option>
          <a-select-option value="TUESDAY"> TUESDAY </a-select-option>
          <a-select-option value="WEDNESDAY"> WEDNESDAY </a-select-option>
          <a-select-option value="THURSDAY"> THURSDAY </a-select-option>
          <a-select-option value="FRIDAY"> FRIDAY </a-select-option>
          <a-select-option value="SATURDAY"> SATURDAY </a-select-option>
          <a-select-option value="SUNDAY"> SUNDAY </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 12, offset: 6 }">
        <a-button type="primary" html-type="submit">
          Remove Library Hour
        </a-button>
      </a-form-item>
    </a-form>
    <!--Button to return to Homepage without doing anything -->
    <div>
      <router-link :to="{ name: 'Homepage' }"> Back to homepage </router-link>
    </div>
  </div>
</template>
<style scoped></style>

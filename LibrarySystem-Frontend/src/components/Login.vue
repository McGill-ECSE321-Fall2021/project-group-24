<template>
  <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
  <div>
    <a-button
      type="primary"
      @click="
        createBook(
          'title',
          'deccy',
          'url',
          'pubby',
          'authy',
          'genre',
          '2001-09-22',
          true,
          'admin'
        )
      "
    >
      Sign Up
    </a-button>
    <a-button
      type="primary"
      @click="
        createBook(
          'title',
          'deccy',
          'url',
          'pubby',
          'authy',
          'genre',
          '2001-09-22',
          true,
          'admin'
        )
      "
    >
      Log In
    </a-button>
  </div>
</template>
<script src="./Items.js"></script>

<style></style>

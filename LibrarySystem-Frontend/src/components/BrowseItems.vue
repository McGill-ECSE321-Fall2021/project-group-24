<template>
  <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
  <div>
    <div id="searchbar">
      <!--This is searchbar from AntDesign-->
      <a-input-search
        placeholder="input search text"
        enter-button="Search"
        size="medium"
        @search="onSearch"
      />
    </div>
    <!--This will create a "li" element for each "item" in the "items" array from Items.js-->
    <ul>
      <li v-for="item in items" :key="item.itemNumber">
        itemTitle: {{ item.itemTitle }}
      </li>
    </ul>
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
      Create Book
    </a-button>
    <div id="searchbar">
      <a-alert
        v-if="itemError"
        message="Error Text"
        :description="this.itemError"
        type="error"
        closable
        @close="removeError()"
        show-icon
      />
    </div>
  </div>
</template>
<script src="./Items.js"></script>
<style>
#searchbar {
  padding-left: 20%;
  padding-right: 20%;
}
</style>

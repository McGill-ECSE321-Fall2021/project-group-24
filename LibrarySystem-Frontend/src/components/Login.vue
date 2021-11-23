<template>
  <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
  <div id="loginInputs">
    <div class="inputs">
      <a-input placeholder="Username"> </a-input>
    </div>
    <div class="inputs">
      <a-input placeholder="Password"> </a-input>
    </div>
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

<style>
.inputs {
  margin: 10px auto;
  position: relative;
  align-self: center;
  width: 20%;
}

#loginInputs {
  /* padding-left: 30%;
  padding-right: 30%; */
  position: relative;
  width: 100%;
  margin: auto;
  align-content: center;
  align-self: center;
}
</style>

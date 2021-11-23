<template>
  <!--Make sure you only have one element in your template, or you will have an error. Wrap everything with a div -->
  <div id="loginInputs">
    <div id="usernameinput">
      <a-input placeholder="Username"> </a-input>
    </div>
    <div id="passwordinput">
      <a-input placeholder="Password"> </a-input>
    </div>
    <a-button
      type="primary"
      @click="
        login(
          document.getElementById('usernameinput').value,
          document.getElementById('passwordinput').value
        )
      "
    >
      Log In
    </a-button>
  </div>
</template>
<script src="./Login.js"></script>

<style>
#usernameinput,
#passwordinput {
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

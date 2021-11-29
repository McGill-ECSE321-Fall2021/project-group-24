<template>
  <div style="margin-left: 15%; margin-right: 15%; margin-top: 2.5%">
    <h4>If you can see red, then a library hour is not covered by a shift!</h4>
    <vue-cal
      :disable-views="['day', 'years', 'year', 'month']"
      hide-view-selector
      style="height: 500px"
      hide-title-bar
      :special-hours="libraryHours"
      :events="events"
      :editable-events="{
        title: false,
        drag: false,
        resize: true,
        delete: true,
        create: false,
      }"
    />
  </div>
</template>
<script src="../js/BrowseAllShifts.js"></script>
<style>
.librarian0 {
  background-color: rgb(255, 123, 0);
  border: 1px solid rgba(233, 136, 46, 1);
  color: #fff;
}
.librarian1 {
  background-color: rgb(32, 99, 255);
  border: 1px solid rgba(46, 67, 255, 1);
  color: #fff;
}
.librarian2 {
  background-color: rgb(255, 36, 237);
  border: 1px solid rgba(224, 46, 255, 1);
  color: #fff;
}
.librarian3 {
  background-color: rgb(83, 156, 0);
  border: 1px solid rgb(47, 148, 0);
  color: #fff;
}
.librarian4 {
  background-color: rgb(143, 44, 255);
  border: 1px solid rgba(189, 46, 255, 1);
  color: #fff;
}
.librarian5 {
  background-color: rgb(30, 107, 0);
  border: 1px solid rgb(5, 153, 0);
  color: #fff;
}
.librarian6 {
  background-color: rgb(146, 124, 0);
  border: 1px solid rgb(133, 124, 0);
  color: #fff;
}
.business-hours {
  background-color: rgba(255, 124, 141, 0.2);
  border: solid rgba(248, 96, 96, 0.6);
  border-width: 2px 0;
}
</style>

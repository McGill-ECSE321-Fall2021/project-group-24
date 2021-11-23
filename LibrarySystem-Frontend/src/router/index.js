import Vue from "vue";
import Router from "vue-router";
import Hello from "@/components/Hello";
// Import your component here
import BrowseItems from "@/components/BrowseItems";
import LoginPage from "@/components/Login.vue";
import SignupPage from "@/components/Signup";
import BrowseRooms from "@/components/BrowseRooms";

Vue.use(Router);

export default new Router({
  routes: [
    // http://127.0.0.1:8087/#/
    {
      path: "/",
      name: "Hello",
      component: Hello
    },
    //Copy this same format for routing your path
    // http://127.0.0.1:8087/#/browse_items
    {
      path: "/browse_items",
      name: "BrowseItems",
      component: BrowseItems
    },
    {
      path: "/sign_in",
      name: "LoginPage",
      component: LoginPage
    },
    {
      path: "/sign_up",
      name: "SignupPage",
      component: SignupPage
    },
    {
      path: "/browse_rooms",
      name: "BrowseRooms",
      component: BrowseRooms
    }
  ]
});

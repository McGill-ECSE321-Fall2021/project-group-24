import Vue from "vue";
import Router from "vue-router";
import Homepage from "@/components/Homepage";
// Import your component here
import BrowseAllItems from "@/components/BrowseAllItems";
import CreateNewRoom from "@/components/CreateNewRoom";
import BrowseAllRooms from "@/components/BrowseAllRooms";

import LoginPage from "@/components/Login.vue";
import SignupPage from "@/components/Signup";

Vue.use(Router);

export default new Router({
  routes: [
    // http://127.0.0.1:8087/#/
    {
      path: "/",
      name: "Homepage",
      component: Homepage,
    },
    //Copy this same format for routing your path
    // http://127.0.0.1:8087/#/browse_items
    {
      path: "/browse_items",
      name: "BrowseAllItems",
      component: BrowseAllItems,
    },
    //create rooms page
    //http://127.0.0.1:8087/#/create_rooms
    {
      path: "/create_rooms",
      name: "CreateNewRoom",
      component: CreateNewRoom,
      name: "BrowseAllItems",
      component: BrowseAllItems,
    },
    {
      path: "/sign_in",
      name: "LoginPage",
      component: LoginPage,
    },
    {
      path: "/sign_up",
      name: "SignupPage",
      component: SignupPage,
    },
    {
      path: "/browse_rooms",
      name: "BrowseAllRooms",
      component: BrowseAllRooms,
    },
  ],
});

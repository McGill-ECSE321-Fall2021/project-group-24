import Vue from "vue";
import Router from "vue-router";
import Homepage from "@/components/Homepage";
// Import your component here
import BrowseAllItems from "@/components/BrowseAllItems";
import CreateNewRoom from "@/components/CreateNewRoom";
import BrowseAllRooms from "@/components/BrowseAllRooms";
import CreateNewItem from "@/components/CreateNewItem";
import CheckoutItem from "@/components/CheckoutItemForPatron";
import ReserveItem from "@/components/ReserveItem";
import ReserveRoom from "@/components/ReserveRoom";
import BrowseItemReservations from "@/components/BrowseItemReservations";
import UpdateRoom from "@/components/UpdateRoom";

import LoginPage from "@/components/Login.vue";
import SignupPage from "@/components/Signup";
import SignupIRL from "@/components/SignupIRL";
import AddLibraryHour from "@/components/AddLibraryHour"; 
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
    //http://127.0.0.1:8087/#/create_item
    {
      path: "/create_item",
      name: "CreateNewItem",
      component: CreateNewItem,
    },
    {
      path: "/add_library_hour",
      name: "AddLibraryHour",
      component: AddLibraryHour,
    },
    {
      path: "/sign_up_irl",
      name: "SignupIRL",
      component: SignupIRL,
    },
    {
      path: "/checkout_item",
      name: "CheckoutItem",
      component: CheckoutItem,
    },
    {
      path: "/reserve_item",
      name: "ReserveItem",
      component: ReserveItem,
    },
    {
      path: "/reserve_room",
      name: "ReserveRoom",
      component: ReserveRoom,
    },
    {
      path: "/update_room",
      name: "UpdateRoom",
      component: UpdateRoom,
    },
    {
      path: "/browse_reservations",
      name: "BrowseItemReservations",
      component: BrowseItemReservations,
    },
  ],
});

import Vue from "vue";
import Router from "vue-router";
import Homepage from "@/components/Homepage";
// Import your component here
import BrowseItems from "@/components/BrowseItems";
import CreateItems from "@/components/CreateItems";
import CreateRooms from "@/components/CreateRooms";

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
      name: "BrowseItems",
      component: BrowseItems,
    },
    //create items page
    //http://127.0.0.1:8087/#/create_items
    {
      path: "/create_items",
      name: "CreateItems",
      component: CreateItems,
    },
    //create rooms page
    //http://127.0.0.1:8087/#/create_rooms
    {
      path: "/create_rooms",
      name: "CreateRooms",
      component: CreateRooms,
    },
  ],
});

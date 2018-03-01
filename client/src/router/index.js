import Vue from "vue";
import Router from "vue-router";

import EmployeesPage from "@/components/admin/EmployeesPage.vue";

Vue.use(Router);

const routes = [
  {
    path: "/admin/employees",
    component: EmployeesPage
  }
];

export default new Router({
  routes
});

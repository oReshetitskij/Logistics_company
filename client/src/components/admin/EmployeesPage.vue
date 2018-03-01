<template>
<div class="row">
    <h1 class="text-center">Employees</h1>
    <div class="col-lg-10 col-md-10 col-md-offset-1 col-lg-offset-1">
        <div class="well">
            <h3>Search filters</h3>
            <div class="form-horizontal">
                <div class="form-group">
                    <label for="first-name" class="control-label">First Name</label>
                    <input type="text" class="form-control" id="first-name"
                        v-model="searchForm.firstName" />
                </div>
                <div class="form-group">
                    <label for="last-name" class="control-label">Last Name</label>
                    <input type="text" class="form-control" id="last-name"
                        v-model="searchForm.lastName"/>
                </div>
                <div class="form-group">
                    <label for="role" class="control-label">Role</label>
                    <br />
                    <select id="role" v-model="searchForm.roleIds" multiple>
                        <option v-for="role in roles"
                            :value="role.roleId"
                            :key="role.roleId">
                            {{ role.roleName }}
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="control-label">Registration date interval</label>
                    <div class="form-inline">
                        <input type="date" class="form-control"
                            id="from-date" title="From"
                            v-model="searchForm.from"/>
                        <input type="date" class="form-control"
                            id="to-date" title="To"
                            v-model="searchForm.to"/>
                    </div>
                </div>
                <button class="btn btn-primary" @click="onSearchClick">
                    <a href="#" class="glyphicon glyphicon-search"></a>
                    Search
                </button>
            </div>
        </div>
        <div class="has-error"
            v-if="requestFailed" >
            <h3 class="help-block text-center">
                Error during request.
            </h3>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Registration Date</th>
                        <th>E-mail</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="emp in employees" :key="emp.id">
                        <td>{{ emp.id }}</td>
                        <td>{{ emp.contact.firstName }}</td>
                        <td>{{ emp.contact.lastName }}</td>
                        <td>{{ emp.registrationDate }}</td>
                        <td>{{ emp.contact.email }}</td>
                        <td>{{ emp.contact.phoneNumber }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data() {
    return {
      searchForm: {
        firstName: "",
        lastName: "",
        roleIds: [],
        from: "",
        to: ""
      },
      employees: [],
      roles: [],
      requestFailed: false
    };
  },
  methods: {
    ...mapMutations(["changeLoading"]),
    onSearchClick(e) {
      if (e) e.preventDefault();

      fetch("http://localhost:8090/api/employees/search", {
        method: "POST",
        body: JSON.stringify(this.searchForm),
        headers: {
          "Content-Type": "application/json"
        }
      })
        .then(res => {
          if (res.status === 200) return res.json();
          throw Error("Invalid status code");
        })
        .then(emps => {
          this.changeLoading(false);
          this.requestFailed = false;
          this.employees = emps;
        })
        .catch(err => {
          this.changeLoading(false);
          this.requestFailed = true;
        });
    }
  },
  mounted() {
    // Load all roles
    this.changeLoading(true);
    fetch("http://localhost:8090/api/employees/roles")
      .then(res => res.json())
      .then(roles => {
        this.changeLoading(false);
        this.roles = roles;
      })
      .catch(err => {
        this.changeLoading(false);
      });

    // Load all employees
    this.changeLoading(true);
    fetch("http://localhost:8090/api/employees")
      .then(res => res.json())
      .then(emps => {
        this.changeLoading(false);
        this.requestFailed = false;
        this.employees = emps;
      })
      .catch(err => {
        this.changeLoading(false);
        this.requestFailed = true;
      });
  }
};
</script>

<style>
@media (min-width: 768px) {
  #from-date {
    margin-right: 5px;
  }
}

@media (max-width: 767px) {
  #from-date {
    margin-bottom: 5px;
  }
}

.glyphicon-search {
  color: white;
}
</style>

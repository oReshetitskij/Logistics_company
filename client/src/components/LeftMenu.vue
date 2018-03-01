<template>
    <div class="side-menu">
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <div class="brand-wrapper">
                    <button class="navbar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
            </div>
        <div class="side-menu container">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#"><span class="glyphicon glyphicon-off"></span>Log out</a>
                    <router-link to="/admin/employees">
                        List employees
                    </router-link>
                </li>
            </ul>
        </div>
      </nav>
  </div>
</template>

<script>
export default {};
</script>


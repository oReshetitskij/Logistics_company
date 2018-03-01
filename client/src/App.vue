<template>
  <div id="app"  class="row">
    <left-menu></left-menu>
    <div class="container-fluid">
      <div class="side-body">
        <router-view></router-view>
      </div>
    </div>
    <transition name="fade">
      <div v-if="loading" class="overlay">
        <img src="/static/img/spinner.gif" />
      </div>
    </transition>
  </div>
</template>

<script>
import { mapState } from "vuex";
import LeftMenu from "./components/LeftMenu.vue";

export default {
  name: "App",
  computed: {
    ...mapState(["loading"])
  },
  components: { LeftMenu }
};
</script>

<style>
.overlay {
  position: fixed;

  left: 0;
  top: 0;

  width: 100%;
  height: 100%;

  background: white;
  opacity: 0.9;
}

.overlay img {
  height: 64px;
  width: 64px;

  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>

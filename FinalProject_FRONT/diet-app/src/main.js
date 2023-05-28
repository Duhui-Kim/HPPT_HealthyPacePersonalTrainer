import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// //fontAwesome설정관련
// import { createApp } from "vue";
// import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// import { library } from "@fortawesome/fontawesome-svg-core";
// import { fas } from "@fortawesome/free-solid-svg-icons";

// library.add(fas);

// const app = createApp(App);
// app.component("font-awesome-icon", FontAwesomeIcon);
// app.mount("#app");
// ///

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");

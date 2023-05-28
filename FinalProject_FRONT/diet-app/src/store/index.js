import Vue from "vue";
import Vuex from "vuex";
import hui from "./hui";
import hwi from "./hwi";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    hui,
    hwi,
  },
});

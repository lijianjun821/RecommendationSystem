import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: [] //用户名
  },
  mutations: {
    setUsername(state, user) {
      state.user = user
    }
  },
  actions: {}
})

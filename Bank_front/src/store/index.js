import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || null,
    expireTime: localStorage.getItem('expireTime') || null,
    loanAccount: localStorage.getItem('loanAccount') || null
  },
  mutations: {
    setLoanAccount (state, row) {
      localStorage.setItem('loanAccount', row)
      state.loanAccount = row
    },
    login (state, data) {
      localStorage.setItem('token', data.token)
      localStorage.setItem('expireTime', JSON.stringify(data.expireTime))
      state.expireTime = data.expireTime
      state.token = data.token
    },
    logout (state) {
      // 移除token
      localStorage.removeItem('token')
      localStorage.removeItem('expireTime')
      state.expireTime = null
      state.token = null
    }
  },
  actions: {
  }
})

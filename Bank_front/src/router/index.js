import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login'
import Transaction from '@/components/Transaction'
import store from '../store'
import LoanAccount from '../components/LoanAccount'
import Bill from '../components/Bill'

Vue.use(Router)

export const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/transaction',
      name: 'Transaction',
      component: Transaction,
      meta: {
        requireAuth: true // 需要登录权限  !warning：前端测试时有时会设置为false，记得改回来
      }
    },
    {
      path: '/hello',
      name: 'HelloWord',
      component: HelloWorld
    },
    {
      path: '/loanAccount',
      name: 'LoanAccount',
      component: LoanAccount
    },
    {
      path: '/bill',
      name: 'Bill',
      component: Bill
    }
  ]
})

// 前端登录拦截
router.beforeEach(function (to, from, next) {
  // 判断该路由是否需要登录权限
  if (to.matched.some(record => record.meta.requireAuth)) {
    if (store.state.token) {
      // 判断缓存里面是否有 userName
      // 在登录的时候设置它的值
      next()
    } else {
      next({
        path: '/login', // 将跳转的路由path作为参数，登录成功后跳转到该路由
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
})

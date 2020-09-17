import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/', //登录页面的路由要定义为/，这样在项目运行时就可以直接加载
      name: 'login',
      component: () => import(/* webpackChunkName: "about" */ './views/login.vue')
    },
    {
      path: '/register', //登录页面的路由要定义为/，这样在项目运行时就可以直接加载
      name: 'register',
      component: () => import(/* webpackChunkName: "about" */ './views/register.vue')
    },
    {
      path: '/home',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    }
  ]
})

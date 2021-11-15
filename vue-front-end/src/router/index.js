import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import VueAxiosGet from '@/components/VueAxiosGet'
import VueSM2EncryptTest from '@/components/VueSM2EncryptTest'
import VueRSAEncryptTest from '@/components/VueRSAEncryptTest'

Vue.use(Router)

export default new Router({
  routes: [
    /*{
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },*/
    {
      path: '/get',
      name: 'VueAxiosGet',
      component: VueAxiosGet
    },
    {
      path: '/sm2',
      name: 'VueSM2EncryptTest',
      component: VueSM2EncryptTest
    },
    {
      path: '/rsa',
      name: 'VueRSAEncryptTest',
      component: VueRSAEncryptTest
    }
  ]
})

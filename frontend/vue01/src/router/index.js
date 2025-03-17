import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/LoginView'
import TestView from '../views/TestView'
import UserView from '../views/userView'
import HomeView from '../views/homeView'
import UserIfo from '../views/UserIfo'
import GalleryView from '../views/galleryView'
import MyShare from  '../views/myShare'
import MyStore from '../views/myStore'
import MessageView from '../views/messageView'
import MessageStore from '../views/message/messageStore.vue'
import MessageSystem from '../views/message/messageSystem'
import MessageMy from '../views/message/messageMy'
import ToolsView from '../views/toolsView'
import MyInfo from '../views/myInfo'
import Instruction from '../views/instructionView.vue'
import Register from '../views/registerView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: TestView,
    redirect:'/home',
    children:[
      {
        path:'message',
        component:MessageView,
        redirect:'/message/store',
        children:[
          {
            path:'store',
            component:MessageStore
          },
          {
            path:'system',
            component:MessageSystem
          },
          {
            path:'my',
            component:MessageMy,
          },
        ]
      },
      {
        path:'tools',
        component:ToolsView,
      },
      {
        path:'user',
        component:UserView
      },
      {
        path:'home',
        component:HomeView
      },
      {
        path:'userIfo',
        component:UserIfo,
        redirect:'/userIfo/myStore',
        children:[
          {
            path:'myShare',
            component:MyShare
          },
          {
            path:'myStore',
            component:MyStore

          }
        ]
      },
      {
        path:'gallery',
        component:GalleryView

      }
    ]
  },


  {
    path:'/login',
    component:Login
  },

  {
    path:'/register',
    component:Register
  },

  {
    path:'/instruction',
    component:Instruction,
    name:'instruction'
  },

  {
    path:'/myInfo',
    component:MyInfo
  },

]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  console.log('即将进入路由：', to.fullPath);
  // 继续路由跳转
  next();
});

export default router



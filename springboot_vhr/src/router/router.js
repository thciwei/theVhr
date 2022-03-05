import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login"
import Home from '../views/Home'
import FriendChat from "../views/chat/FriendChat"
import HrInfo from "@/views/HrInfo";

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: "Login",
    component: Login,
    hidden: true
},
    {
        path: '/home',
        name: '导航一',
        component: Home,
        hidden: true,
        meta: {
            roles: ['admin', 'user']
        },
        children: [
            {
                path: '/chat',
                name: '在线聊天',
                component: FriendChat,
                hidden: true
            },
            {
                path: '/HrInfo',
                name: '个人中心',
                component: HrInfo,
                hidden: true
            }
        ]

    },
]

const router = new VueRouter({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router

import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/index'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {postRequest} from './utils/api'
import {postKeyValueRequest} from './utils/api'
import {putRequest} from './utils/api'
import {getRequest} from './utils/api'
import {deleteRequest} from './utils/api'
import {initMenu} from './utils/menus'
import 'font-awesome/css/font-awesome.min.css'
Vue.config.devtools = true;
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.getRequest = getRequest
Vue.prototype.deleteRequest = deleteRequest
Vue.prototype.postKeyValueRequest = postKeyValueRequest

Vue.use(ElementUI,{size:'small'});
Vue.config.productionTip = false
router.beforeEach((to, from, next) => {
    if (to.path == '/') {
        next();
    } else {
        if(window.sessionStorage.getItem("user")){
            initMenu(router, store);
            next();
        }else{
            //登陆页输入地址，打印出to的 console.log(to)
            // next('/')
            next('/?redirect='+to.path)

        }

    }
    // console.log(to);
    // console.log(from);
    // next();

})
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
# vue三种不同方式实现页面跳转



## 注册路由

```js
import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../layout/index'

Vue.use(Router)

export const constantRouterMap = [
  { path: '/login',
    meta: { title: '登录', noCache: true },
    component: () => import('@/views/login'),
    hidden: true
  },
  // 新注册的路由
  {
    path: '/indexDetail',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'center',
        component: () => import('@/views/orderModel/goodsReceiveList/indexDetail'),
        name: '总单详情',
        meta: { title: '总单详情' }
      }
    ]
  }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

```



## 使用路由实现跳转

### Vue：router-lin

```html
 <router-link to="/">[跳转到主页]</router-link>
 <router-link to="/login">[登录]</router-link>
 <router-link to="/logout">[登出]</router-link>
 <router-link to="/indexDetail">[详情]</router-link>

详解
1. 不带参数
<router-link :to="{name:'home'}"> 
<router-link :to="{path:'/home'}"> //name,path都行, 建议用name 
// 注意：router-link中链接如果是'/'开始就是从根路由开始，如果开始不带'/'，则从当前路由开始。
    
 2.带参数
 <router-link :to="{name:'home', params: {id:1}}"> 
// params传参数 (类似post)
// 路由配置 path: "/home/:id" 或者 path: "/home:id" 
// 不配置path ,第一次可请求,刷新页面id会消失
// 配置path,刷新页面id会保留
// html 取参 $route.params.id
// script 取参 this.$route.params.id
<router-link :to="{name:'home', query: {id:1}}"> 
// query传参数 (类似get,url后面会显示参数)
// 路由可不配置
// html 取参 $route.query.id
// script 取参 this.$route.query.id
```



### this.$router.push("/");

```js
<button @click="goHome">[跳转到主页]</button>
export default {
   name: "App",
   methods: {
     // 跳转页面方法
       goHome() {
       this.$router.push("/");
	   //this.$router.push("/indexDetail");
    },
}
    
    
// 详解
1. 不带参数，直接传入将要跳转的名字即可，方式有下面三种
this.$router.push('/home')//路由路径
this.$router.push({name:'home'}) //路由的名字
this.$router.push({path:'/home'})//路由的路径

2. query传参 
this.$router.push({name:'home',query: {id:'1'}})
this.$router.push({path:'/home',query: {id:'1'}})
// html 取参 $route.query.id
// script 取参 this.$route.query.id

3. params传参
 this.$router.push({name:'home',params: {id:'1'}}) // 只能用 name
  
// 路由配置 path: "/home/:id" 或者 path: "/home:id" ,
// 不配置path ,第一次可请求,刷新页面id会消失
// 配置path,刷新页面id会保留
// html 取参 $route.params.id
// script 取参 this.$route.params.id
4. query和params区别
query类似 get, 跳转之后页面 url后面会拼接参数,类似?id=1, 非重要性的可以这样传, 密码之类还是用params刷新页面id还在
 params类似 post, 跳转之后页面 url后面不会拼接参数 , 但是刷新页面id 会消失
```



### this.$router.go(1);

向前或者向后跳转n个页面，n可为正整数或负整数

```html
     <button @click="upPage">[上一页]</button>
     <button @click="downPage">[下一页]</button>
     upPage() {
      //  后退一步记录，等同于 history.back()
      this.$router.go(-1);
    },

    downPage() {
      // 在浏览器记录中前进一步，等同于 history.forward()
      this.$router.go(1);
    }
```

代码示例：

```html
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view/>
    <router-link to="/">[跳转到主页]</router-link>
    <router-link to="/login">[登录]</router-link>
    <router-link to="/logout">[登出]</router-link>
    <!-- javascript跳转页面 -->
    <button @click="goHome">[跳转到主页]</button>
    <!-- 回到上一页 -->
    <button @click="upPage">[上一页]</button>
    <button @click="downPage">[下一页]</button>
    <!-- 回到下一页 -->
  </div>
</template>
<script>
  export default {
    name: "App",
    methods: {
      // 跳转页面方法
      goHome() {
        this.$router.push("/");
      },
      upPage() {
        //  后退一步记录，等同于 history.back()
        this.$router.go(-1);
      },
      downPage() {
        // 在浏览器记录中前进一步，等同于 history.forward()
        this.$router.go(1);
      }
    }
  };
</script>
```

 

# 区别

```
this.$router.push
```

跳转到指定url路径，并想history栈中添加一个记录，点击后退会返回到上一个页面

```
this.$router.replace
```

跳转到指定url路径，但是history栈中不会有记录，点击返回会跳转到上上个页面 (就是直接替换了当前页面)

```
this.$router.go(n)
```

向前或者向后跳转n个页面，n可为正整数或负整数
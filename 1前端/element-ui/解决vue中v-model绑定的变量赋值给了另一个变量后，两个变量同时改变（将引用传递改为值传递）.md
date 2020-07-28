# 解决vue中v-model绑定的变量赋值给了另一个变量后，两个变量同时改变（将引用传递改为值传递）



```
data() {
      return {
        data1：[],
        data2:'[]
        },
    }
	
// 在 method 中将后台请求回来的数据分别赋值给变量 data1 和 data2；
methods: {
    myMethod（）{
    	// 模拟后台返回的数据
        var a = {name：'zhangsan'}
        this.data1 = a
        this.data2 = this.data1
    }
}
// 若修改 data2 中的数据，那么 data1 中的数据也会被修改，因为 data2 是依赖 data1 的，因此 data1 中的数据会被修改
// 若要实现 修改data2中的数据，但是不修改 data1 的数据，就要将两个变量解除依赖
```

**根本原因：这是因为在Object赋值的时候，传递的不是值，而是引用，他们指向了同一个空间！**



解决方式：解除依赖，改为值传递

第一种：利用 JSON.parse 和 JSON.stringify

```
this.submitForm = JSON.parse( JSON.stringify(this.formData) )
1
```

第二种：ES6 的解析语法

```
this.submitForm = { ...this.formData }
```
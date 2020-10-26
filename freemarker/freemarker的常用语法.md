# 1、循环遍历（json字符串转json对象）

代码如下：goods.sku表示获得后台传过来的good对象中的sku属性的属性值，该属性值是一个json对象，本质是一个数组，如：[{"price":"0.85","property":"颜色：红色，","count":"22"}, {"price":"0.85","property":"颜色：红色","count":"22"}]；

 

代码解析：

第一行：获取到goods对象的sku属性的属性值，该属性值为json字符串，并赋值给text

第二行：将text转换为json对象，即第二行代码中的json，该json对象为数组（若是使用modelAndView那种返回的对象则不用转）

第三行：遍历json对象，得到数组中的每一个元素item

第四行：依次获得item对象的每一个值

```html
<#assign text>${goods.sku}</#assign>  
<#assign json=text?eval />  
<#list json as item>  
<tr>
		<td>
			${item.property}
		</td>
		<td>
			<span class="textbox"></span>
		</td>
		<td>
			<span class="textbox"></span>
		</td>								  
	</tr>  
</#list>
```



# 2、设定默认值、定时跳转

```html
Controller接口：
@GetMapping("/index")
public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                  Map<String, Object> map) {
    if (!StringUtils.isEmpty(productId)) {
        ProductInfo productInfo = productService.findOne(productId);
        map.put("productInfo", productInfo);
		map.put(“msg”:”成功信息”);
    }
    return new ModelAndView("product/index", map);
}

                      
Freemarker页面：
<html>
<head>
    <meta charset="utf-8">
    <title>成功提示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
     <div class="alert alert-dismissable alert-success">
          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
          <h4>
              成功!
          <#--若msg为空，则设为默认值空字符串，注意此处的msg只是一个字段；
同理若productInfo为空则设为空字符串，productInfo是对象，要用括号括起来，如下-->
          </h4> <strong>${msg!""}</strong><a href="${url}" class="alert-link">3s后自动跳转</a>
<input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
     </div>
</body>
<script>
//定时跳转
    setTimeout('location.href="${url}"', 3000);
</script>
</html>
```

 

# 3、引模块、if、弹窗、列表循环、数字循环、上下页、取数据

```html
<html>
<body>
<div id="wrapper" class="toggled">
    <#--引入边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content（将生成的代码都放到该目录下）-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>支付状态</th>
<!--遍历出来的模块分为两列-->
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        
//从orderDTOPage对象中取出content（列表），并循环
                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.getPayStatusEnum().message}</td>
                            <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                            <td>
                                //加一个if判断，若订单状态为已取消，则不显示
                                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    //若当前页为第一页，则将上一页灰化掉
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>
                    
//数字循环
                    //orderDTOPage.getTotalPages()获得总页码的值，因为总页码的值是一个数字，1.. 表示遍历1到该页码值，若页码为3，即1到3
                    <#list 1..orderDTOPage.getTotalPages() as index>
                        //遍历的时候，遇到当前页，就将当前页的页码灰化掉（接口返回的时候会带当前页码）
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    //若当前页为最后一页（即等于总页数），则下一页会灰化掉
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>

<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>

<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var websocket = null;
    if('WebSocket' in window) {
        websocket = new WebSocket('ws://sell.natapp4.cc/sell/webSocket');
    }else {
        alert('该浏览器不支持websocket!');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }

    websocket.onclose = function (event) {
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        console.log('收到消息:' + event.data)
        //弹窗提醒, 播放音乐
        $('#myModal').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

</script>

</body>
</html>
```



# 4、 判断是否存在

```html
<div class="form-group">
    <label>类目</label>
    <select name="categoryType" class="form-control">
        <#list categoryList as category>
            <option value="${category.categoryType}"
<!--如果双问号表示存在，&&表示并且，如下：productInfo.categoryType存在并且等于categoryType时候，成立-->
                    <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                        selected
                    </#if>
                >${category.categoryName}
            </option>
        </#list>
    </select>
</div>
```

 

5、 

 

 

 
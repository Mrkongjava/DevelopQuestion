# 1、在springBoot中使用freemarker

```java
Controller代码：
/**
 * 生成预支付订单
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,@RequestParam("returnUrl") String returnUrl,Map<String, Object> map) {
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 发起支付（创建预支付订单）
        PayResponse payResponse = payService.create(orderDTO);

		//使用参数中定义的map
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        
		//返回一个modelAndView，参数1是跳转地址，参数二是携带的数据
        return new ModelAndView("pay/create", map);
    }
}
```

```html
Freemarker模板页面：

<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${payResponse.appId}",//公众号名称，由商户传入（获得controller中的payResponse对象的appid属性）
                    "timeStamp":"${payResponse.timeStamp}",         //时间戳，自1970年以来的秒数
                    "nonceStr":"${payResponse.nonceStr}", //随机串
                    "package":"${payResponse.packAge}",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":"${payResponse.paySign}" //微信签名
                },
                function(res){
//                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {

//                    }     
// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
//获得controller中返回的returnUrl变量值
                    location.href = "${returnUrl}";
                }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>
```


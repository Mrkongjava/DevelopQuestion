package com.group.jww.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group.core.annotation.ActionAnnotation;
import com.group.core.commons.SysCode;
import com.group.core.controller.BaseController;
import com.group.core.exception.ServiceException;
import com.group.jww.mapper.JwwRechargeOrderMapper;
import com.group.jww.model.JwwCustomer;
import com.group.jww.model.JwwCustomerExample;
import com.group.jww.model.JwwRechargeOrder;
import com.group.jww.model.JwwRechargeOrderExample;
import com.group.jww.service.BaseService;
import com.group.jww.service.UserCenterService;
import com.group.jww.service.WxService;
import com.group.jww.util.CheckUtil;
import com.group.jww.util.MessageUtil;
import com.group.utils.HttpUtils;
import com.group.utils.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 微信相关接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("wx")
public class WxController extends BaseController{

	String appid = "wxfe19480979014ade";
	String appsecret = "f7671e15e8e9c1421f8f21f42bb4362c";
	
    // 获取access_token的接口地址（GET） 限200(次/天)  
    String access_token_url = "https://api.weixin.qq.com/cgi-bin/token";
    
    //获取ticket的接口地址（POST）
    String ticket_url =  "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
    
    
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private WxService wxServiceImpl;
    @Autowired
    private BaseService baseServiceImpl;
    @Autowired
    private JwwRechargeOrderMapper jwwRechargeOrderMapper;
    
	/**
	 * 验证微信公众平台基本设置中的token并接收微信的推送信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * http://localhost:8080/jww/wx/adverts?token=jwwToken&signature=B96C3FDC8E055D9A136D7CABED1D19292A599500&timestamp
	 */
	@RequestMapping("adverts")
	public void adverts(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
        	//接收微信推送的信息
       
            // xml请求解析  ，本地方法
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号，即开发者微信号 
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  enent
            String msgType = requestMap.get("MsgType");
            //消息创建时间（整型）
            String CreateTime = requestMap.get("CreateTime");
            //事件类型Event，SCAN
            String Event = requestMap.get("Event");
            //EventKey，事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
            String EventKey = requestMap.get("EventKey");
            //Ticket,二维码的ticket，可用来换取二维码图片
            String ticket = requestMap.get("Ticket");
            
            System.out.println("扫码者："+fromUserName);
            System.out.println("开发者"+toUserName);
            System.out.println("消息类型"+msgType);
            System.out.println("消息创建时间"+CreateTime);
            System.out.println("事件类型"+Event);
            System.out.println("事件key值EventKey:"+EventKey);
            System.out.println("ticket值"+ticket);

		

			
            List list = userCenterService.getUserInfo(fromUserName);
            
            //数据库中没有该用户，为该用户添加到数据库中
            if(list.size()!=1){
            	//拼接获取access_token的get请求参数
            	String accessTokenParam = "grant_type=client_credential&appid="+appid+"&secret="+appsecret;
            	
            	//获取access_token
            	String accessToken = HttpUtils.sendGet(access_token_url, accessTokenParam,"text/html");
            	//转为jsonObject
            	JSONObject jsonObject = JSON.parseObject(accessToken);
            	
            	String access_token = jsonObject.getString("access_token");
            	
            	Map<String, Object> Map = wxServiceImpl.getWxUserInfo(fromUserName, access_token);           	
            }
            
             String orderId = null;           
            //用户未关注，获得订单id
            if(Event.equals("SCAN")){
            	orderId = EventKey;
            	
            }
            
            //用户已关注，获得订单id
            if(Event.equals("subscribe")){
            	orderId = EventKey.substring(7);
            }
            
            //查询订单是否存在
            List RechargeList = baseServiceImpl.getRechargeOrder(orderId);
            
            //订单不存在
            if(RechargeList.size()==0){
            	throw new ServiceException("2003","订单不存在");
            	
            //订单存在,并且游戏币已经领取
            }else if (RechargeList.size()==1){
            	JwwRechargeOrder rechareOrder = (JwwRechargeOrder) RechargeList.get(0);
            	String state = rechareOrder.getState();
            	
            	if("已领取".equals(state)){
            		throw new ServiceException("2005","游戏币已领取");
            	}
            	
            	//订单存在，但是游戏币还没领取，将游戏币打到用户账号中并状态改为已领取
            	if("已支付".equals(state)){
            		JwwRechargeOrderExample example = new JwwRechargeOrderExample();
            		example.or().andIdEqualTo(Integer.parseInt(orderId));
            		
            		JwwRechargeOrder jww = new JwwRechargeOrder();
            		jww.setState("已领取");
            		
            		jwwRechargeOrderMapper.updateByExampleSelective(jww, example);
            		
            		//获得游戏币个数
            		int number = rechareOrder.getRechargeCount();
            		
            		JwwCustomer customer = new JwwCustomer();
            		customer.setGameCurrency(number);
            		
            		JwwCustomerExample example2 = new JwwCustomerExample();
            		example2.or().andWxUuidEqualTo(fromUserName);
            		
            		renderJson(request, response, SysCode.SUCCESS, null);
            	}
            	
            	if("待付款".equals(state)){
            		
            		throw new ServiceException("2008","该订单未支付！");
            	}
            	
            }
                       

            System.out.println("扫码者："+fromUserName);
            System.out.println("开发者"+toUserName);
            System.out.println("消息类型"+msgType);
            System.out.println("消息创建时间"+CreateTime);
            System.out.println("事件类型"+Event);
            System.out.println("事件key值EventKey:"+EventKey);
            System.out.println("ticket值"+ticket);
            

		// 自定义的token
		String token = "jwwToken";
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(token, signature, timestamp, nonce)) {
			System.out.println("check ok");
			out.print(echostr);

		}
		out.close();
                  
    }  
	
	/**
	 * 生成并返回带参数二维码的url
	 * @param request
	 * @param response
	 * 
	 * http://localhost:8080/jww/wx/getQRcode
	 */
    @RequestMapping("getQRcode")
    @ActionAnnotation(params={"orderId"})
	public void getQRcode(HttpServletRequest request, HttpServletResponse response){
    	
        Map<String,String> params = (Map<String,String>) request.getAttribute("params");
        String orderId = params.get("orderId");
        Assert.hasLength(orderId,"code不能为空");
		
    	//拼接获取access_token的get请求参数
    	String accessTokenParam = "grant_type=client_credential&appid="+appid+"&secret="+appsecret;
    	
    	//获取access_token
    	String accessToken = HttpUtils.sendGet(access_token_url, accessTokenParam,"text/html");
    	//转为jsonObject
    	JSONObject jsonObject = JSON.parseObject(accessToken);
    	
    	String access_token = jsonObject.getString("access_token");
    	Integer expires_in = jsonObject.getInteger("expires_in");
    	    
        if(access_token==null)
        {
        	throw new ServiceException("2001","获取 access_token 出错");
        }

        
        
        //拼接获取ticket的接口地址       
        String ticketUrl = ticket_url.replace("TOKENPOST", access_token);  
        
        HashMap<String,String> sceneMap = new HashMap<>();
        sceneMap.put("scene_id", orderId);
        
        HashMap<String, Object> infoMap = new HashMap<>();
        infoMap.put("scene", sceneMap);
        
        HashMap<Object, Object> map = new HashMap<>();
        map .put("expire_seconds", 2592000);
        map.put("action_name", "QR_SCENE");
        map.put("action_info", infoMap);
                              
        JSONObject json = (JSONObject) JSON.toJSON(map); 
        String ticketParam = json.toString();
        		        		        		        
        //获取ticket
        String ticketResult = HttpUtils.sendPost(ticketUrl, ticketParam, "text/html");      
        //转为jsonObject
        JSONObject jsonObjectTicket = JSON.parseObject(ticketResult);
        
    	String ticket = jsonObjectTicket.getString("ticket");
    	Integer expire_seconds = jsonObjectTicket.getInteger("expire_seconds");
    	String url = jsonObjectTicket.getString("url");
                  
    	renderJson(request, response, SysCode.SUCCESS, ticket);
	}


    /**
     * 获取微信接入参数
     * @param request
     * @param response
     */
    @RequestMapping("getWXConfig")
    @ActionAnnotation(params={"fullUrl"})
    public void getWXConfig(HttpServletRequest request,HttpServletResponse response){
        Map<String, String> params = (Map<String,String>) request.getAttribute("params");

        String fullUrl = params.get("fullUrl");
        Assert.hasLength(fullUrl,"接入的地址不能为空");

        try {
            //获取access_token（调用接口凭证）
            String url = "https://api.weixin.qq.com/cgi-bin/token";
            String param = "grant_type=client_credential&appid="+appid+"&secret="+appsecret;
            String reuslt = HttpUtils.sendGet(url, param, null);
            JSONObject jsonObject = JSON.parseObject(reuslt);
            String access_token = jsonObject.getString("access_token");

            //获取jsapi_ticket
            url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
            param = "access_token="+access_token+"&type=jsapi";
            reuslt = HttpUtils.sendGet(url, param, null);
            jsonObject = JSON.parseObject(reuslt);
            String jsapi_ticket = jsonObject.getString("ticket");

            //签名SignUtils
            Map<String, String> map = SignUtils.sign(jsapi_ticket, fullUrl);
            map.put("appId", appid);
            map.put("access_token", access_token);
            map.put("jsapi_ticket", jsapi_ticket);

            renderJson(request, response, SysCode.SUCCESS, map);
        } catch (Exception e) {
            logger.error("获取微信接入参数失败:"+e.getMessage(), e);
            renderJson(request, response, SysCode.SYS_ERR, e);
        }
    }

    /**
     * 创建自定义菜单
     * @param request
     * @param response
     */
    @RequestMapping("createMenue")
    @ActionAnnotation(params={""})
    public void createMenue(HttpServletRequest request,HttpServletResponse response){
        //获取access_token（调用接口凭证）
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String param = "grant_type=client_credential&appid="+appid+"&secret="+appsecret;
        String reuslt = HttpUtils.sendGet(url, param, null);
        JSONObject jsonObject = JSON.parseObject(reuslt);
        String access_token = jsonObject.getString("access_token");

        //删除菜单
        String url3 = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+access_token;
        String result3 = HttpUtils.sendGet(url3,null,null);

        String url2 = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;

        //一级菜单
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        map.put("button",list);

        Map<String, Object> map1 = new HashMap<>();

        List<Map<String, Object>> sub_button = new ArrayList<>();
        Map<String, Object> map1_sub1 = new HashMap<>();
        map1_sub1.put("type","view");
        map1_sub1.put("name","输入编码启动");
        map1_sub1.put("url","http://wwj.apemoon.com/wawaji/code_login.html");
        sub_button.add(map1_sub1);
        Map<String, Object> map1_sub2 = new HashMap<>();
        map1_sub2.put("type","view");
        map1_sub2.put("name","扫码启动");
        map1_sub2.put("url","http://wwj.apemoon.com/wawaji/scan.html");
        sub_button.add(map1_sub2);

        map1.put("name","点击启动");
        map1.put("sub_button",sub_button);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("type","view");
        map2.put("name","优惠券");
        map2.put("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe19480979014ade&redirect_uri=http%3a%2f%2fwwj.apemoon.com%2fwawaji%2fcoupon.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("type","view");
        map3.put("name","个人中心");
        map3.put("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe19480979014ade&redirect_uri=http%3a%2f%2fwwj.apemoon.com%2fwawaji%2fperson.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        list.add(map3);

        String result = HttpUtils.sendPost(url2,JSON.toJSONString(map),null);
        renderJson(request, response, SysCode.SUCCESS, result);
    }
}

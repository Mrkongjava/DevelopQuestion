package com.group.jww.controller;

import com.group.jww.util.CheckUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;



/**
 * 微信相关接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("wx")
public class WxController {

	/**
	 * 验证微信公众平台基本设置中的token
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * http://localhost:8080/wx/adverts?token=jwwToken&signature=B96C3FDC8E055D9A136D7CABED1D19292A599500&timestamp
	 */
	@RequestMapping("adverts")
	public void adverts(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//自定义的token
		String token = "jwwToken";
		System.out.println("进来了Controller");
        String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");  
          
        PrintWriter out = response.getWriter();  
        if (CheckUtil.checkSignature(token,signature, timestamp, nonce)){  
            System.out.println("check ok");  
            out.print(echostr);  
              
        }  
        out.close();  
    }  
	
}

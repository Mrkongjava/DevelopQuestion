package baidumaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
  
public class Maps {  

    private static String ak = "MlH7O5oPseelguc3WhF1UTEUElr9mPSG";  
      //post方式实现connection形式的http请求
    
//    public static Map<String, String> testPost(String x, String y) throws IOException {  
//    	  
//         //构造url字符串
//        URL url = new URL("http://api.map.baidu.com/geocoder/v2/?"+"ak="+ak+"&callback=renderReverse&"+   
//                 "location="+ x + "," + y + "&output=json&pois=1");  
//        
//		  //打开该url对应的链接    
//        URLConnection connection = url.openConnection();  
//        /** 
//         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。 
//         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做： 
//         */  
//        connection.setDoOutput(true);  
//        OutputStreamWriter out = new OutputStreamWriter(connection  
//                .getOutputStream(), "utf-8");  
////        remember to clean up  
//        out.flush();  
//        out.close();  
////        一旦发送成功，用以下方法就可以得到服务器的回应：  
//        String res;  
//        InputStream l_urlStream;  
//        l_urlStream = connection.getInputStream();  
//        BufferedReader in = new BufferedReader(new InputStreamReader(  
//                l_urlStream,"UTF-8"));  
//        StringBuilder sb = new StringBuilder("");  
//        while ((res = in.readLine()) != null) {  
//            sb.append(res.trim());  
//        }  
//        String str = sb.toString();  
//        System.out.println(str);  
//        Map<String,String> map = null;  
//        if(StringUtils.isNotEmpty(str)) {  
//            int addStart = str.indexOf("formatted_address\":");  
//            int addEnd = str.indexOf("\",\"business");  
//            if(addStart > 0 && addEnd > 0) {  
//                String address = str.substring(addStart+20, addEnd);  
//                map = new HashMap<String,String>();  
//                map.put("address", address);  
//                return map;       
//            }  
//        }  
//          
//        return null;  
//          
//    }  
    
    
    //测试方法
    public static void main(String[] args) throws IOException {  
//        Map<String, String> json = Maps.testPost("39.983424", "116.322987");  
//        System.out.println("address :" + json.get("address")); 
    	
    	String result = sendGet("39.983424", "116.322987");
    	System.out.println(result);
    }  
    
    
    
    
    //get方式实现connection形式的http请求
    public static String sendGet(String a,String b)
    {
    	
     String result = "";
     try{
      //构造字符串URL
      String urlName ="http://api.map.baidu.com/geocoder/v2/?"+"ak="+ak+"&callback=renderReverse&"+   
              "location="+ a + "," + b + "&output=json&pois=1";

      //根据url字符串新建URL对象
      URL U = new URL(urlName);
      
      //创建一个URLConnection实例表示的远程对象引用的URL连接。
      URLConnection connection = U.openConnection();
      
      //打开一个通信链接到这个网址引用的资源
      connection.connect();
     
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = in.readLine())!= null)
      {
       result += line;
      }
      in.close();   
     }catch(Exception e){
      System.out.println("出错了！"+e);
     }
     return result;
    }
}  

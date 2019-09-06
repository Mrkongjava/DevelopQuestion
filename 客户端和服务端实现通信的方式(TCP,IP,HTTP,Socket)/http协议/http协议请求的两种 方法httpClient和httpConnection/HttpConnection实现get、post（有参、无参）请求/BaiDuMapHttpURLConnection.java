package baidu.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaiDuMapHttpURLConnection 
{
	static String ak="MlH7O5oPseelguc3WhF1UTEUElr9mPSG";
	
	static String result = "";
	
	public static String baiDuMap(String a ,String b) throws IOException
	{
		String url = "http://api.map.baidu.com/geocoder/v2/?ak="+ak+
				"&callback=renderReverse&location="+a+","+b+"&output=json&pois=1";
		
		URL getUrl = new URL(url);
		
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		
		connection.connect();
		
		InputStream input = connection.getInputStream();
		
		InputStreamReader  inputReader = new InputStreamReader(input);
		
		BufferedReader reader = new BufferedReader(inputReader);
		
        String lines = null;  
        
        while ((lines = reader.readLine()) != null) {  
            
        	result+=lines;
        }  
        reader.close();  
        // 断开连接  
        connection.disconnect(); 
		
		return result;
	}
	
	public static void main(String[] args) throws IOException
	{
		String str = baiDuMap("32.883424","116.322987");
		System.out.println(str);
	}
}

package baidu.map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class BaiDuMapHttpClient 
{
	static String ak="MlH7O5oPseelguc3WhF1UTEUElr9mPSG";
			
	public static String doGet(String a ,String b) throws ClientProtocolException, IOException
	{
		String url = "http://api.map.baidu.com/geocoder/v2/?ak="+ak+
				"&callback=renderReverse&location="+a+","+b+"&output=json&pois=1";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet get = new HttpGet(url);
		
		CloseableHttpResponse response = httpClient.execute(get);
		
		HttpEntity entity = response.getEntity();
		
		String str = EntityUtils.toString(entity,"utf-8");
		
		//关闭httpclient
		response.close();
		httpClient.close();
		
		System.out.println(str);
		return str;
	}
			
	@Test		
	public static void main(String[] args) throws ClientProtocolException, IOException 
	{
		String s = doGet("32.883424","116.322987");
		
		System.out.println(s);
	}
}

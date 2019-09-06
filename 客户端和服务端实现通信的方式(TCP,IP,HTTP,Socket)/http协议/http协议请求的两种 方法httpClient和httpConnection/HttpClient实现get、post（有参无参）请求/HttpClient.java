package com.taotao.httpClientTest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpClient
{
	//不带参数的get请求
	@Test
	public void doGet() throws Exception {
		//创建一个httpclient对象，使用HttpClients工具类创建httpClients对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		//创建一个GET对象（相当于通过浏览器访问网址）
		HttpGet get = new HttpGet("http://www.sogou.com");
		
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		
		//取响应的结果
        //statusCode表示响应的状态码，若是200表示正常
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		
        //entity表示响应的内容（主体），用apache的httpEntity
		HttpEntity entity = response.getEntity();
		
         //使用工具类EntityUtils将响应的主体信息写到一个Sting对象里，编码格式为utf-8
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	
	
	//带参数的get请求
	@Test
	public void doGetWithParam() throws Exception{
		//创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个uri对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
        //添加查询的参数,还有一种添加参数的方法就是直接在上面的网址上加上  ?参数1=xx&参数2=xx&......
		uriBuilder.addParameter("query", "花千骨");
		HttpGet get = new HttpGet(uriBuilder.build());
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//取响应的结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	
	
	
	//不带参数的post请求，想要模拟不带参数的post请求就先必须要在controller层写一个接收post请求的controller
	@Test
	public void doPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
	 
		//创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
		//执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		String string = EntityUtils.toString(response.getEntity());
		System.out.println(string);
		response.close();
		httpClient.close();
		
	}
	
	
	/*
	 * 带参数的post请求，这里进行post请求的时候要先动一下我们的代码再保存一下，重新编译一下该类的代码；因为进行
	 * clean操作是将该类编译好的东西都被clean掉了。。
	 */
    @Test
	public void doPostWithParam() throws Exception{
		//创建一个httpClient
         CloseableHttpClient httpClient = HttpClients.createDefault();
		  
		//创建一个post对象,模拟post请求服务端。
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
		//创建一个list。模拟一个表单提交的内容
		List<NameValuePair> kvList = new ArrayList<>();
		kvList.add(new BasicNameValuePair("username", "zhangsan"));
//		kvList.add(new BasicNameValuePair("password", "123"));
		
		//将包含要提交的内容的list集合包装成一个Entity对象
		StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
		//将entity设置到请求的内容中
		post.setEntity(entity);
		
		//执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
         //获得请求返回的实体.
		String string = EntityUtils.toString(response.getEntity());
		System.out.println(string);
		response.close();
		httpClient.close();
	}
    
    public static void main(String[] args) 
    {
		System.out.println();
	}
}

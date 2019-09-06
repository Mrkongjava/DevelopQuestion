package com.itheima.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 天气查询socket服务器
 */
public class WeatherServer {

	public static void main(String[] args) {
		
		try {
			// 创建一个Socket服务
			// 参数：服务的端口号，建议大一些0-1024是系统留用端口
			ServerSocket serverSocket = new ServerSocket(12345);
			System.out.println("服务端已启动。。。。。");
			
			/*
			*创建一个死循环，让服务器可以实现持续服务能力，也就是说可以一直等待客户端的连接建立,当一个
			*客户端连接了服务器之后，服务器立刻创建一个线程，实现和客户端的连接；这样设计实现多线程同时访问服务器
			*同时和服务器建立连接
			*/
			while(true) {
				// 等待客户端建立连接
				// 阻塞的方法，建立连接后向下执行
				final Socket socket = serverSocket.accept();
				
				//创建新线程，记得在后面启动线程，否则会报错。
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						DataInputStream inputStream = null;
						DataOutputStream outputStream = null;
						try {
						// 使用输入流读取客户端发送的数据
						inputStream = new DataInputStream(socket.getInputStream());
						String cityName = inputStream.readUTF();
						System.out.println("接收到客户端发送的数据：" + cityName);
						// 根据城市查询天气
						System.out.println("查询天气。。。。");
						Thread.sleep(1000);
						String resultString = "阴天转雷阵雨";
						outputStream = new DataOutputStream(socket.getOutputStream());
						// 返回查询结果
						System.out.println("返回查询结果:" + resultString);
						outputStream.writeUTF(resultString);
						} catch (Exception e) {
							// TODO: handle exception
						}finally {
							// 关闭流
							try {
								inputStream.close();
								outputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				};
				//启动线程
				new Thread(runnable).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 

	}
}

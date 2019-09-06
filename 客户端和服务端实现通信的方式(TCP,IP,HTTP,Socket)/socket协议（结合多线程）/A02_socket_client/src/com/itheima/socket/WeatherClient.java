package com.itheima.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class WeatherClient {

	public static void main(String[] args) throws Exception {
		while (true) {
			// 创建一个socket连接
			Socket socket = new Socket("127.0.0.1", 12345);
			// 使用输出流发送城市名称
			DataOutputStream outputStream = new DataOutputStream(
					socket.getOutputStream());
			// 发送城市名称
			outputStream.writeUTF("北京");
			// 接收返回的结果
			DataInputStream inputStream = new DataInputStream(
					socket.getInputStream());
			String resultString = inputStream.readUTF();
			System.out.println("天气信息：" + resultString);
			// 关闭流
			inputStream.close();
			outputStream.close();
		}
	}

}

package cn.itcast.shiro.authentication;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 
 * <p>Title: MD5Test</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-3-23下午5:14:47
 * @version 1.0
 */
public class MD5Test {
	
	public static void main(String[] args) {
		
		//原始 密码 
		String source = "111111";
		//盐
		String salt = "qwerty";
		//散列次数
		int hashIterations = 2;
		//上边散列1次：f3694f162729b7d0254c6e40260bf15c
		//上边散列2次：36f2dfa24d0a9fa97276abbe13e596fc
		
		//方法一：Md5Hash
		//构造方法中：
		//第一个参数：明文，原始密码 
		//第二个参数：盐，通过使用随机数
		//第三个参数：散列的次数，比如散列两次，相当 于md5(md5('盐+原始明文密码'))
		Md5Hash md5Hash = new Md5Hash(source, salt, hashIterations);
		
		String password_md5 =  md5Hash.toString();
		System.out.println(password_md5);
		
		//方法二：SimpleHash，除了第一个参数和第一个方法不同之外，其他的都一样
		//第一个参数：散列算法 
		SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
		System.out.println(simpleHash.toString());
		
		
		
	}

}

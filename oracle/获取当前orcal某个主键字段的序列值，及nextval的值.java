package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test2 
{
	//公共代码：得到数据库连接  
	public static Connection getConnection() throws Exception{  
	    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();  
	    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.6:1521:orcl", "kong", "kong");  
	    return conn;  
	}  
	
	//方法一  
	//先用select seq_t1.nextval as id from dual 取到新的sequence值。  
	//然后将最新的值通过变量传递给插入的语句：insert into t1(id) values(?)   
	//最后返回开始取到的sequence值。  
	//这种方法的优点代码简单直观，使用的人也最多，缺点是需要两次sql交互，性能不佳。  
	public static int insertDataReturnKeyByGetNextVal() throws Exception {  
	    Connection conn = getConnection();  
	    //seq_no.nextval表示序列名，id表示要查询的表中的主键字段。
	    String vsql = "select seq_no.nextval as id from dual";  
	    PreparedStatement pstmt =(PreparedStatement)conn.prepareStatement(vsql);  
	    ResultSet rs=pstmt.executeQuery();  
	    rs.next();  
	    int id=rs.getInt(1);  
	    System.out.println(id);
	    rs.close();  
	    pstmt.close();  
	    vsql="insert into t1(id) values(?)";  
	    pstmt =(PreparedStatement)conn.prepareStatement(vsql);  
	    pstmt.setInt(1, id);  
	    pstmt.executeUpdate();  
	    System.out.println("id:"+id);  
	    return id;  
	}  
	
	public static void main(String[] args) throws Exception 
	{

			int i = insertDataReturnKeyByGetNextVal();
			System.out.println(i);

	}
}

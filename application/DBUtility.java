package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection con=null;
		try {
			//1. MYSQL DataBase Class 로드한다.
			Class.forName("com.mysql.jdbc.Driver");
		//2. 주소와 아이디, 비밀번호를 통해서 접속요청한다.
		con=DriverManager.getConnection("jdbc:mysql://localhost/memberdb", "root", "123456");
		
		} catch (Exception e) {
			RootController.callAlent("연결실패 : DB연결 실패");
			return null;
		}
		return con;
	}
}

package kr.sshsoft.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
	//필드
	Connection conn = null;
	Statement stmt;
	PreparedStatement psmt; // stmt보다 파라메터 들어가야하는 부분을 손쉽게 넣을 수 있다.
	ResultSet rs;
	
	// 메서드
	// 커넥션
	public Connection getConn() {
		// 1) Oracle JDBC Driver 자바라이브러리.
		// 2) Connection 객체. 세션
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "jsp";
		String pass = "jsp";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("오라클 드라이버 없음.");
			e.printStackTrace();
		}
		return conn;
	} // end getConn
}

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	
	// 1. DB 접속을 수행하는 getConnection() 메소드 정의
	// => 파라미터 : 없음   리턴타입 : java.sql.Connection(con)
	public static Connection getConnection() {
		Connection con = null;
		
		// 0 단계
		String driver = "com.mysql.cj.jdbc.Driver"; // 드라이버 클래스
		String url = "jdbc:mysql://localhost:3306/funweb"; // DB 접속 정보
		String user = "root"; // 계정명
		String password = "1234"; // 패스워드

		try {
			// 1단계. 드라이버 클래스 로드
			Class.forName(driver);

			// 2단계. DB 연결
			con = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패! - " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 실패! - " + e.getMessage());
			e.printStackTrace();
		}
		
		return con;
	}
	
	// -----------------------------------------------------------------------------------------------
	
	// 2. DB 자원 반환을 수행하는 close() 메소드 정의
	// => 반환해야하는 대상 객체 : Connection, PreparedStatement, ResultSet
	// => 메소드 이름은 close() 로 통일하고, 파라미터 타입만 다르게 하는 오버로딩 활용하여 정의
	public static void close(Connection con) {
		try {
			if(con != null) { con.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) { pstmt.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null) { rs.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
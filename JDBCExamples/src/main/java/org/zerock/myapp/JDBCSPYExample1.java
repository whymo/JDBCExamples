package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCSPYExample1 { // JDBC Driver SPY
	
	// Driver Spy를 사용하기 위해서는 jdbcUrl를 수정해야 한다.
	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	// Driver Spy에 해당하는 log4jdbc를 추가해줘야 한다.
	
	static final String user = "hr";
	static final String pass = "Oracle87761226";
	
	public static void main(String[] args) {
		
		String sql = "INSERT INTO departments(department_id, DEPARTMENT_NAME) VALUES(?, ?)";
		// 추가하는 sql문 
		// sql문은 ;을 포함해서는 안된다.
		
		try {
			
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			conn.setAutoCommit(false);
			// Connection 객체는 기본적으로 Auto Commit을 해준다.
			// conn.setAutoCommit(false);로 자동 커밋을 해제시켜줄 수도 있다.
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 900);
			pstmt.setString(2, "영화 900");
			
			int affectedLines = 0;
			
			try ( conn; pstmt; ){
				affectedLines = pstmt.executeUpdate(); // 영향을 받은 라인의 수를 알려준다.
				log.info("+ affectedLines : {}", affectedLines);
				// 하나만 생성되었기 때문에 affectedLines 값이 1로 나온다.
				
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			} // try - with - resources
			
			// 비지니스 로직 수행
			
		} catch (Exception e) {
			e.printStackTrace();
		} // try - catch
		
	} // end main

} // end class
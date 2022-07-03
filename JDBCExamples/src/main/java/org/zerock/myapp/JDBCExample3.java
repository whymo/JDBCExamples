package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample3 {
	
	// JDBC - Java에서 DB를 만지기 위한 것이다.
	
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP"; // (****)
//	static final String driverClass = "oracle.jdbc.OracleDriver";
	static final String user = "hr";
	static final String pass = "Oracle87761226";
	
	public static void main (String [] args) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 1 단계 생략 버전! ( Driver Class 객체 생략 ) - Driver Class는 이제 등록하지 않아도 된다.
		
		// 2. 연결 얻어내기
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
		
		// 3. To Create a PreparedStatement sql
		String sql = "SELECT employee_id, last_name, hire_date, salary, department_id "
				+ "FROM employees "
				+ "WHERE salary > ? "
				+ "ORDER BY salary DESC";
		pstmt = conn.prepareStatement(sql);
		pstmt.setDouble(1, 5000); // ?값 설정
		
		// 4. Execution ( 실행 )
		rs = pstmt.executeQuery(); // 준비된 SQL 실행
		
		// + 동적인 SQL의 경우에는 stmt.executeQuery(sql);와 같이 매개변수로 sql문이 들어가게 된다.
		// + 동적인 보다 준비된 SQL를 사용하는 것이 좋다.
		
		// 5. 데이터 추출
		while( rs.next() ) {
			
			int employeeId = rs.getInt("EMPLOYEE_ID");
			String lastName = rs.getString("last_name");
			Date hireDate = rs.getDate("HIRE_DATE");
			double salary = rs.getDouble("SALARY");
			int departmentId = rs.getInt("DEPARTMENT_ID");
			// 칼럼명은 대소문자 구분하지 않는다.
			
			log.info("\t + employee : {} {} {} {} {}", employeeId, lastName, hireDate, salary, departmentId); // (****)
			
		} // while
		
		// 6. 자원해제
		Objects.requireNonNull(rs);
		rs.close();
		
		if(pstmt != null ) pstmt.close();
		
		assert conn != null;
		conn.close();
		
	} // end main

} // end class

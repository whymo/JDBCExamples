package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample4 { // try - with - resource 1 - until java7s

	// 1. TNSNAME 방식으로 클라우드 연결
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP"; // (****)
//	static final String jdbcUrl = "jdbc:oracle:thin:TNSAlias?TNS_ADMIN=파일이 있는 주소(tnsnames.ora파일을 보면된다.)";
//	환경변수로 TNS_ADMIN이라는 환경변수가 있는 경우에는 환경변수가 우선적으로 적용되기에, 잘적용될 수 있다.
	
	// 2. 사용자 DB 계정
	static final String user = "hr";
	
	// 3. 사용자 DB 암호
	static final String pass = "Oracle87761226";
	
	// =======================================================================================================================
	
	public static void main(String[] args) throws SQLException {
		
//		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try ( Connection conn = DriverManager.getConnection(jdbcUrl, user, pass); ) { // until java7
			
			String sql = new StringBuffer().
					append("SELECT employee_id, last_name, hire_date, salary, department_id ").
					append("FROM employees ").
					append("WHERE salary > ? ").
					append("ORDER BY salary DESC").toString(); // (****)
			// StringBuffer은 문자열을 다닥다닥 붙이기 때문에 공백을 직접 입력해줘야 한다.
			// 자원 효율성을 위해서 StringBuffer이나 StringBuilder를 필수로 사용해야 한다. (****)
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "7000");
			
			rs = pstmt.executeQuery(); // 실행
			
			while( rs.next() ) {
				String employeeId = rs.getString("EMPLOYEE_ID");
				String lastName = rs.getString("last_name");
				String hireDate = rs.getString("HIRE_DATE");
				String salary = rs.getString("SALARY");
				String departmentId = rs.getString("DEPARTMENT_ID");
				// 칼럼명은 대소문자 구분하지 않는다.
				
				log.info("\t + employee : {} {} {} {} {}", employeeId, lastName, hireDate, salary, departmentId); // (****)
			} // while
			
			// 자원해제
			Objects.requireNonNull(rs);
			rs.close();
			
			if(pstmt != null ) pstmt.close();
			// + conn 자원 해제는 try - with - resource로 한다.
			// conn은 Connection 인터페이스가 AutoCloseable하고 있기에 가능한 것이다. (**)
			// AutoCloseable이 없으면 try - with - resource으로는 자원해제가 불가능하다.
			
		} // try - with - resource
		
	} // end main
	
} // end class

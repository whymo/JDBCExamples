package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample2 { // 동적 SQL
	
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP"; // (****)
	static final String driverClass = "oracle.jdbc.OracleDriver";
	static final String user = "hr";
	static final String pass = "Oracle87761226";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection conn = null; // 연결
		Statement stmt = null; // sql문장
		PreparedStatement pstmt = null; // 인터페이스 형태
		ResultSet rs = null; // 결과셋
		
//		1. JDBC Driver Class를 Method Area에 Clazz 객체로 등록
		Class.forName(driverClass); // 예외처리를 해야한다.
		
//		2. driverClass를 이용해서 연결시도 ( To connect to the specified Oracle Instance using driverClass )
		conn = DriverManager.getConnection(jdbcUrl, user, pass); // 예외처리를 해야한다.
		log.info("2. conn : "+ conn);
		
//		3. To create a Statement object from Connection object ( Connection 객체로부터 정적 객체 생성하기 )
		String sql = "SELECT * FROM employees";
		stmt = conn.createStatement(); // 동적인 SQL문을 작성할 때 ( 동일한 SQL문장을 매번 생성 및 실행 )
		log.info("3. stmt : " + stmt);
		
//		4. SQL문 실행하기 ( To execute the specified Statement, SQL )
		rs = stmt.executeQuery(sql);  // DQL
		log.info("4. rs : " + rs);
		
//		5. TO get All records from ResultSet object (***)
		while( rs.next() ) {
			
			// 가져올 속성을 기제해야 한다.
			int employeeId = rs.getInt("EMPLOYEE_ID");
			String firstName = rs.getString("FIRST_NAME");
			String lastName = rs.getString("LAST_NAME");
			String email = rs.getString("EMAIL");
			String phone = rs.getString("PHONE_NUMBER");
			Date hireDate = rs.getDate("HIRE_DATE");
			String jodId = rs.getString("JOB_ID");
			double salary = rs.getDouble("SALARY");
			double commPct = rs.getDouble("COMMISSION_PCT");
			int managerId = rs.getInt("MANAGER_ID");
			int departmentId = rs.getInt("DEPARTMENT_ID");
			
			String employee = 
					String.format(
							"%s %s %s %s %s %s %s %s %s %s %s", 
							employeeId, firstName, lastName, email, phone, hireDate, jodId, salary, commPct, managerId, departmentId );
			
			log.info( "+ employee : " + employee);
			
		} // while - 다음 레코드가 남아있으면 무한 반복 		
		
//		5. To close current Connection ( 연결 끊기 - 순서중요!!! )

		assert rs != null; // = if(rs != null ) // = Objects.requireNonNull(rs); (**)
		rs.close(); // 자원해제
		
		assert stmt != null;
		stmt.close(); // 자원해제를 해야한다.
		
		assert conn != null;
		conn.close();
		log.info("Connection Closed");
		
	} // end main

} // end class

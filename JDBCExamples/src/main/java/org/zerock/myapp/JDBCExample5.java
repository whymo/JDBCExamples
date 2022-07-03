package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample5 {
	
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP"; // (****)
	static final String user = "hr";
	static final String pass = "Oracle87761226";
	
	public static void main(String[] args) throws SQLException {
		
		Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
		
		String sql = new StringBuffer().
				append("SELECT employee_id, last_name, hire_date, salary, department_id ").
				append("FROM employees ").
				append("WHERE salary > ? ").
				append("ORDER BY salary DESC").toString(); // (****)
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "7000");
		
		try ( conn; pstmt; ) { // over java8	
			
			// rs의 경우 DB의 데이터를 활용할 지점이 오면 rs를 사용한다. 그렇기에 이렇게 작성할 수도 있다.
			
			ResultSet rs = pstmt.executeQuery(); // 실행
			
			try ( rs; ) {
				
				while( rs.next() ) {
					
					String employeeId = rs.getString("EMPLOYEE_ID");
					String lastName = rs.getString("last_name");
					String hireDate = rs.getString("HIRE_DATE");
					String salary = rs.getString("SALARY");
					String departmentId = rs.getString("DEPARTMENT_ID");
					// 칼럼명은 대소문자 구분하지 않는다.
					
					log.info("\t + employee : {} {} {} {} {}", employeeId, lastName, hireDate, salary, departmentId); // (****)
					
				} // while
				
			} catch(Exception e) {
				e.printStackTrace();
			} // inner - try- catch
			
			// 자원 해제를 try - with - resource에서 해주었다.
			// 단, try - with - resource에서 자원해제하기 위해서는 AutoCloseable해야 한다.
			// try( )에서 자원해제는 오른쪽에서 왼쪽으로 순서대로 진행된다.
			
			// TCL
			// 커밋할 때에는 conn.commit();
			// 롤백할 때에는 conn.rollback();
			// + 오류가 발생할 경우에는 rollback을 해줘야 하기 때문에
			// + executeQuery(); 하는 곳에 try-catch로 SQL Exception이 발생할 경우에는 롤백을 하게 처리해야 한다.
			// + 그리고 throw e를 줌으로써 밖에서도 오류로 멈추게 해야 한다.
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		} // try - with - resource
		
	} // end main

} // end class

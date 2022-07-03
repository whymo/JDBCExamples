package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zerock.myapp.domain.Employee;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCSPYExample2 {
	
	// Driver Spy를 사용하기 위해서는 jdbcUrl를 수정해야 한다.
	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	// Driver Spy에 해당하는 log4jdbc를 추가해줘야 한다.
	
	static final String user = "hr";
	static final String pass = "Oracle87761226";
	
	public static void main(String[] args) {
		
		try {
			
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			String sql = new StringBuffer().
					append("SELECT employee_id, last_name, hire_date, salary, department_id ").
					append("FROM employees ").
					append("WHERE salary > ? ").
					append("ORDER BY salary DESC").toString(); // (****)
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "7000");
			
			ResultSet rs = pstmt.executeQuery(); // 실행
			
			@Cleanup("clear")
			List<Employee> employees = new ArrayList<>();
			
			try ( conn; pstmt; rs; ){
				
				while ( rs.next() ) {
					
					String employeeId = rs.getString("EMPLOYEE_ID");
					String lastName = rs.getString("last_name");
					String hireDate = rs.getString("HIRE_DATE");
					String salary = rs.getString("SALARY");
					String departmentId = rs.getString("DEPARTMENT_ID");

					// 적절한 자료구조를 만들어 저장 - VO(Value Object)
					Employee emp = new Employee( employeeId, lastName, hireDate, salary, departmentId );
					
					employees.add(emp); // ArrayList에 원소 추가하기
					
				} // while
				
			} //try -with - resources ( try()안에 지정되어 있는 자원을 블록이 끝나면 자동으로 해제시켜준다. )
			
			employees.forEach( e -> log.info(e) ); // forEach(*****)
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // try - catch ( try블록 내에서 발생한 예외를 catch에 지정해 주어 예외를 추적하게 한다. )
		
	} // main

} // end class

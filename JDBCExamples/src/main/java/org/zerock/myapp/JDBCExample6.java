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
public class JDBCExample6 {
	
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
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
			// rs는 rs.getString할때, 비로소 활동하기 때문에 조심해야 한다.
			
			@Cleanup("clear")
			List<Employee> employees = new ArrayList<>();
			// @Cleanup("clear")은 블록이 끝날때 자원을 자동으로 해제시켜 준다.
			
			try ( conn; pstmt; rs; ){
				// 오른쪽에서 왼쪽순으로 닫아준다.
				
				while ( rs.next() ) {
					
					String employeeId = rs.getString("EMPLOYEE_ID");
					String lastName = rs.getString("last_name");
					String hireDate = rs.getString("HIRE_DATE");
					String salary = rs.getString("SALARY");
					String departmentId = rs.getString("DEPARTMENT_ID");
					// 칼럼명은 대소문자 구분하지 않는다.

					// 적절한 자료구조를 만들어 저장 - VO(Value Object)
					Employee emp = new Employee( employeeId, lastName, hireDate, salary, departmentId );
					
					employees.add(emp); // ArrayList에 원소 추가하기
					
				} // while
				
			} //try -with - resources ( try()안에 지정되어 있는 자원을 블록이 끝나면 자동으로 해제시켜준다. )
			
			//=================================
			// 일련의 비지니스 로직 수행 자리 ( 자료구조에 저장된 DB데이터 사용 )
			// 위에서 생성한 자료구조 이용
			
//			for ( Employee em : employees ) {
//				log.info( "\t + employee : " + em);
//			} // enhanced for
			
			employees.forEach( e -> log.info(e) ); // forEach(*****)
			
			//=================================
			
			// 자원은 빨리 끊어줄 수록 좋다.
			// 그렇기 때문에 ResultSet은 빨리 얻어내는 것이 좋다. (**)
			// + 즉, ReultSet 얻기 -> 비지니스 로직 수행
			
			// employees.clear();
			// @Cleanup("clear")로 자원해제하였다.
			// ArrayList는 AutoCloseable하지 않기에, 비지니스 로직 후에 해제시켜줘야 한다.
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // try - catch ( try블록 내에서 발생한 예외를 catch에 지정해 주어 예외를 추적하게 한다. )
		
	} // main

} //end class

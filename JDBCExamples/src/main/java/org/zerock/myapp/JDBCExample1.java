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
public class JDBCExample1 { // 준비된 SQL
	
//	============================================================
	// JDBC를 이용한 Target DB에 연결을 위한 정보를 아래와 같이 준비해야 한다!!
	
	// (1) JDBC URL
	// (2) Driver Class
	// (3) ID
	// (4) PassWord
//	============================================================
	// (1) JDBC URL : DB Vendor마다 조금씩 다르지만, 기본틀은 표준에 의해서 정해져있다.
	
//	final String jdbcUrl = "jdbc:<vendor명>:thin:@IP주소:포트번호/<DB이름>"; // EZCONNECT 방식
//	vendor명은 회사명을 의미하는데, Oracle인지 아니면 그외인지 회사명을 작성하는 것이다.
//	thin은 JDBC를 연결하는 방법이며, 그 아래에는 연결할 타겟의 정보를 작성하는 것이다.
	
//	final String jdbcUrl = "jdbc:oracle:thin:@<네트워크별칭(TNS Alias)?TNS_ADMIN=tnsnames.ora파일의경로>"; // TNSAlias방식
//	클라우드는 TNSAlias방식만 된다. (*****)
	
	static final String jdbcUrl = "jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP"; // (****)
	// 환경변수 TNS_ADMIN은 삭제하는 편이 좋다. ( 전자지갑의 위치가 바뀔수 있기에 )
	// TNS_ADMIN=C:/opt/OracleCloudWallet/ATP는 쿼리 스트링으로, db202204131245_high의 파일(tnsnames.ora) 위치를 알려준다.(***)
	// TNS_ADMIN은 네트워크 별칭(TNS)를 관리하는 환경변수를 의미한다.
//	============================================================
	// (2) Driver Class
	
	static final String driverClass = "oracle.jdbc.OracleDriver";
//	final String driverClass = "oracle.jdbc.driver.OracleDriver"; // OK
//	============================================================
	// (3) 사용자 DB계정
	
	static final String user = "hr";
//	============================================================
	// (4) 사용자 DB 암호
	
	static final String pass = "Oracle87761226";
//	============================================================
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		log.info("0. user명 : " + user);
		// static은 static(정적 멤버) 내에서만 사용이 가능하다.
//		============================================================
		Connection conn = null; // 연결
		Statement stmt = null; // sql문장
		PreparedStatement pstmt = null; // 인터페이스 형태
		ResultSet rs = null; // 결과셋
		// import 잊지말고 주의해서 사용하자!!
//		============================================================
		
		// 1. JDBC Driver Class를 Method Area에 Clazz 객체로 등록
//		Class.forName("FQCN of the JDBC driver class");
		Class.forName(driverClass); // 예외처리를 해야한다.
		
//		Class.forName("a.b.c.OracleDriver"); // xx
//		============================================================
		
//		2. driverClass를 이용해서 연결시도 ( To connect to the specified Oracle Instance using driverClass )
		conn = DriverManager.getConnection(jdbcUrl, user, pass); // 예외처리를 해야한다.
//		Connection java.sql.DriverManager.getConnection(String url, String user, String password) throws SQLException
		
		log.info("2. conn : "+ conn);
//		============================================================
		
//		3. To create a Statement object from Connection object ( Connection 객체로부터 정적 객체 생성하기 )
//		conn.createStatement(); // 동적인 SQL문을 작성할 때 ( 동일한 SQL문장을 매번 생성 및 실행 )
//		conn.prepareStatement(); // 준비된 SQL문을 작성할 때 ( 동일한 SQL문일 경우, 최적화 실행계획 재사용 ) (****)
		
		String sql = "SELECT * FROM employees WHERE salary >= ?"; // ?는 Bind Variable이라고 한다.
		
		pstmt = conn.prepareStatement(sql);
		// PreparedStatement java.sql.Connection.prepareStatement(String sql) throws SQLException
		// String 타입으로 sql문을 작성해 줘야 한다.
		
		pstmt.setDouble(1, 10000); // (****)
		// 이를 통해서 ?(Bind Variable)에 값을 지정해 줄 수 있다.
		// 1은 몇번째 Bind Variable인지 지정하는 것으로, Bind Variable은 1번부터 시작한다.
		// 10000는 Bind Variable에 들어갈 값을 지정한 것이다.
		// Bind Variable은 준비된 SQL에서만 가능하기에, 동적에서는 불가능 하다.
		
		log.info( "3. pstmt : " + pstmt );
//		============================================================
		
//		4. SQL문 실행하기 ( To execute the specified Statement, SQL )
		rs = pstmt.executeQuery();  // DQL
//		int affectedLines = pstmt.executeUpdate(); // DML
//		executeUpdate()는 sql문으로 영향을 받은 라인의 개수를 반환해 준다.
		
		log.info("4. rs : " + rs);
//		============================================================
		
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
//		============================================================
		
//		5. To close current Connection ( 연결 끊기 - 순서중요!!! )

		assert rs != null; // = if(rs != null ) // = Objects.requireNonNull(rs); (**)
		rs.close(); // 자원해제
		
		assert pstmt != null;
		pstmt.close(); // 자원해제를 해야한다.
		
		assert conn != null;
		conn.close();
		log.info("Connection Closed");
		
		// + 반드시 자원을 해제해 줘야지 운영체제가 망가지지 않는다. (***)
//		============================================================
		
	} // end main

} // end class

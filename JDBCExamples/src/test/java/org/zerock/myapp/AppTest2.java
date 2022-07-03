package org.zerock.myapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * JUnit 5.x 테스트 소스 골격 코드를 배우자! ( Stub Class )
 */

@Log4j2
@NoArgsConstructor
//==========================================================

@TestInstance(Lifecycle.PER_CLASS)
// @TestInstance는 라이프사이클을 지정해줘야 한다. (***)

//==========================================================

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @TestMethodOrder는 테스트 메소드가 수행되는 순서를 지정해 준다. (***)
// 속성으로 value 속성을 지정해 줘야 한다.

//==========================================================

public class AppTest2 {
	
	private String jdbcUrl;
	private String user;
	private String pass;
	private Connection conn;
	
	//==========================================================
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		this.jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
		this.user = "hr";
		this.pass = "Oracle87761226";
	} // beforeAll
	
	// + beforeAll과 eachAll은 전역적인 의미의 사전 / 사후 처리이기 때문에
	// + @Test 메소드가 여러개여도 1번씩만 진행이 된다.
	
	//==========================================================
	
	@BeforeEach
	void beforeEach() {
		log.trace("beforeEach() invoked.");
	} // beforeEach
	
	// + beforeEach와 afterEach는 매 테스트 메소드마다 사전 / 사후에 진행이 된다.
	
	//==========================================================
	
	@AfterAll
	void afterAll() {
		log.trace("afterAll() invoked.");
	} // afterAll
	
	@AfterEach
	void afterEach() throws SQLException {
		log.trace("afterEach() invoked.");	
		
		conn.close();
	} // afterEach
	
	//==========================================================
	
//	@Disabled
	@Test 
	@Order(2) // 실행되는 순서를 변경할 수 있다.
	@DisplayName("<<<< contextLoads1 >>>>") // 실행시킬때 나타나는 이름을 지정해 준다.
	@Timeout(value=2000, unit=TimeUnit.MILLISECONDS)
	void contextLoads1() throws SQLException {
		log.trace("contextLoads1() invoked.");
		
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
		assertNotNull(conn);
	} // contextLoads1
	
	@Test 
	@Order(3) // 실행되는 순서를 변경할 수 있다.
	@DisplayName("<<<< contextLoads2 >>>>") // 실행시킬때 나타나는 이름을 지정해 준다.
	@Timeout(value=2000, unit=TimeUnit.MILLISECONDS)
	void contextLoads2() throws SQLException {
		log.trace("contextLoads2() invoked.");
		
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
		assertNotNull(conn);
	} // contextLoads2
	
	//==========================================================
	
//	@Disabled // @Disabled는 비활성화시킨다.
	@Test 
	@Order(1) // 실행되는 순서를 변경할 수 있다.
	@DisplayName("<<<< contextLoads3 >>>>") // 실행시킬때 나타나는 이름을 지정해 준다.
	@Timeout(value=3000, unit=TimeUnit.MILLISECONDS)
	void contextLoads3() throws SQLException {
		log.trace("contextLoads3() invoked.");
		
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
		assertNotNull(conn);
	} // contextLoads3
	
	
} // end class

package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * JUnit 4.x 테스트 소스 골격 코드를 배우자!
 */

@Log4j2
@NoArgsConstructor
public class AppTest {
	
	//===============================================
	// @NoArgsConstructor로 자동으로 생성자 생성
	//===============================================
	
	private String jdbcUrl;
	private String user;
	private String pass;
	private Connection conn;
	
	//===============================================
	
//	@Before
	public void before() {
		log.trace("before() invoked.");
		
		// + 사전처리할 코드 ex. jdbcURL, DB 계정, DB 암호
		
		this.jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db202204131245_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
		this.user = "hr";
		this.pass = "Oracle87761226";
		
	} // before
	
	//===============================================
	
//	@Test(timeout = 100)
	public void contextLoads1() throws SQLException {
		log.trace("contextLoads1() invoked.");
		
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
	} // contextLoads
	
	// + @Test는 timeout 속성을 주어, 블록 내의 코드실행시간을 총 몇 밀리세컨드까지 기다릴지 정할 수 있다.
	// + 이때 준비시간은 제외된다.
	
	//===============================================
	
//	@Test
	public void contextLoads2() {
		log.trace("contextLoads2() invoked.");
	} // contextLoads2
	
	//===============================================
	
//	@After
	public void tearDown() throws SQLException {
		log.trace("tearDown() invoked.");
		
		// + 사후처리할 코드 ex. 자원 해제
		
		// 1번째 방법
		assert this.conn != null;
		
		// 2번째 방법
		Objects.requireNonNull(this.conn);
		
		// 3번째 방법
//		assertNotNull(this.conn);
		
		// 4번째 방법 ( 알려주는 것이 없기에 추천하지 않는다. )
		if(this.conn != null)
		
		conn.close();
		
	} // tearDown
	
	//===============================================
	
	// + @Befor / @Test / @After는 public이어야 한다.
	// + @Befor / @Test / @After는 매개변수가 없다.
	// + @Befor / @After는 사전처리와 사후처리가 없을 경우, 없앨 수 있다.
	// + @Test 메소드는 test가 이름 앞에 붙여야 했는데, 현재 버전에서는 붙이지 않아도 가능하다.
	// + @Test는 여러개 생성이 가능하다.
	// + 4.x 버전에서는 비포와 에프터가 매 테스트 메소드마다 초기화된다.
	
	//===============================================
	
} // end class
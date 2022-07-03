package org.zerock.myapp;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class App {
    
	public static void main( String[] args ) {
		
		log.info("Hello World! - log" );
		// log를 남기기 위해서는 log4j2.xml파일이 필요하다. (**)
		// 출력되는 로그의 형태를 바꾸기 위해서는 log4j2.xml파일을 vsCode에서 열어서 변경하면 된다.
		
        System.out.println( "Hello World! - sysout" );
        
    } // end main
    
} // end class

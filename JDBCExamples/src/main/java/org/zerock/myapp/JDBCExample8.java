package org.zerock.myapp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample8 {
	
	public static void main(String[] args) {
		
		MyResource res1 = new MyResource(); res1.setName("MyResource1");
		MyResource res2 = new MyResource(); res2.setName("MyResource2");
		MyResource res3 = new MyResource(); res3.setName("MyResource3");
		
		log.info("res1 : {}, res2 : {}, res3 : {}", res1.getName(), res2.getName(), res3.getName());
		
		try( res1; res2; res3; ){
			;;
		} catch(Exception e) {
			e.printStackTrace();
		} // try - with - resources
		
//		14:50:04.828 TRACE --- [      main] o.z.m.MyResource.close:40 - Resource : MyResource3 closed
//		14:50:04.829 TRACE --- [      main] o.z.m.MyResource.close:40 - Resource : MyResource2 closed
//		14:50:04.829 TRACE --- [      main] o.z.m.MyResource.close:40 - Resource : MyResource1 closed
		// try()에서는 오른쪽에서 왼쪽순으로 자원이 닫히고 있음을 알 수 있다.
		
	} // end class

} //end class


@Log4j2
@NoArgsConstructor
class MyResource implements AutoCloseable {
	
	@Getter
	@Setter
	private String name;
	
	@Override
	public void close() throws Exception {
		log.trace("Resource : {} closed", this.name);
	} // end close()
	
} // end MyResource

package org.zerock.myapp.domain;

import lombok.Value;

// @Value는 Read-only이기에 getter은 제공해도 setter는 없다.
// @Value = VO(Value Object) : Read only object (**)
// DB 테이블의 1개의 레코드를 저장할 수 있는 객체를 생성하는 클래스에 적합하다.(***)

// setter까지 하려면 @Data를 사용하는 것이 좋다. (**)
// @Data = DTO(Data Transfer object) : 프런트에서 얻은 데이터를 백엔드로 넘겨줄때 사용(***)

@Value
public class Employee {
	
	private String employeeId; 
	private String lastName;
	private String hireDate;
	private String salary; 
	private String departmentId;

} // end class

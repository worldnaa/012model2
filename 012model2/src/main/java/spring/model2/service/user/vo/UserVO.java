package spring.model2.service.user.vo;

import java.io.Serializable;

/*
 *  :: 사용자 정보를 갖는 Bean : Value Object
 *  :: Controller 에서 Model 로 : client 요구사항 ==> Model 
 *  :: Model 에서 Control 로 : Model data 처리결과 ==> Control 
 *  즉 Presentation Layer/Business Layer 를 오가면 Data 이동 (Data Transfer Object::DTO)
 *  :: POJO(Plain Old Java Object)
*/
public class  UserVO implements Serializable{
	
	// Field
	private String userId;
	private String userPwd;
	private boolean active;

	// Constructor
	public UserVO(){
		System.out.println("UserVO Default Constructor");
	}

	// Method(getter/setter Method)
	public void setUserId(String userId){
		this.userId =  userId;	
	}
	public void setUserPwd(String userPwd){
		this.userPwd = userPwd;
	}
	public void setActive(boolean active)	{
		this.active = active;	
	}
	public String getUserId(){
		return userId;
	}
	public String getUserPwd(){
		return userPwd;	
	}	
	public boolean isActive(){
		return active;	
	}

	public String toString() {
		return "UserVO [active=" + active + ", userId=" + userId
				+ ", userPwd=" + userPwd + "]";
	}
	
}//end of class

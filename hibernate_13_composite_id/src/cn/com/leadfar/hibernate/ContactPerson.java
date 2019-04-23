package cn.com.leadfar.hibernate;

import java.io.Serializable;

public class ContactPerson implements Serializable{

	private String name;
	private String address;
	private String qq;
	
	public ContactPerson(){
		
	}
	
	public ContactPerson(String name){
		this.name = name;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}


}

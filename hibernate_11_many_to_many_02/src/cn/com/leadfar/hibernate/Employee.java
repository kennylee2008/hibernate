package cn.com.leadfar.hibernate;

import java.util.Set;

public class Employee {
	private int id;
	private String name;
	private String address;
	private String qq;
	
	private Set<EmpRole> empRoles;
	
	public Employee(){
	}

	public Employee(String name){
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public Set<EmpRole> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(Set<EmpRole> empRoles) {
		this.empRoles = empRoles;
	}

}

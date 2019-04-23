package cn.com.leadfar.hibernate;

import java.util.HashSet;
import java.util.Set;

public class Employee {
	private int id;
	private String name;
	private String address;
	private String qq;
	
	private Set<Role> roles;
	
	public Employee(){
	}

	public Employee(String name){
		this.name = name;
	}
	
	public void addRole(Role role){
		if(roles == null){
			roles = new HashSet<Role>();
		}
		roles.add(role);
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}

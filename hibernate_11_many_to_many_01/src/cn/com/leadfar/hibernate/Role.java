package cn.com.leadfar.hibernate;

import java.util.Set;

public class Role {
	private int id;
	private String name;
	private Set<Employee> employees;
	
	public Role(){
		
	}
	
	public Role(String name){
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
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}

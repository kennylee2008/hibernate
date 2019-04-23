package cn.com.leadfar.hibernate;

import java.util.HashSet;
import java.util.Set;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private Set<String> qqs;
	
	public ContactPerson(){
	}
	
	public ContactPerson(String name){
		this.name = name;
	}
	
	public void addQq(String qqNumber){
		if(qqs == null){
			qqs = new HashSet<String>();
		}
		qqs.add(qqNumber);
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

	public Set<String> getQqs() {
		return qqs;
	}

	public void setQqs(Set<String> qqs) {
		this.qqs = qqs;
	}
}

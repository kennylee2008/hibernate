package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.List;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private List<String> qqs;
	
	public ContactPerson(){
	}
	
	public ContactPerson(String name){
		this.name = name;
	}
	
	public void addQq(String qqNumber){
		if(qqs == null){
			qqs = new ArrayList<String>();
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

	public List<String> getQqs() {
		return qqs;
	}

	public void setQqs(List<String> qqs) {
		this.qqs = qqs;
	}


}

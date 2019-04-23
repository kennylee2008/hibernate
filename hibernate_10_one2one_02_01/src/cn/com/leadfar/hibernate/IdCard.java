package cn.com.leadfar.hibernate;

public class IdCard {
	private int id;
	private String sn;
	private ContactPerson person;
	
	public IdCard(){
		
	}
	
	public IdCard(String sn){
		this.sn = sn;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public ContactPerson getPerson() {
		return person;
	}
	public void setPerson(ContactPerson person) {
		this.person = person;
	}
}

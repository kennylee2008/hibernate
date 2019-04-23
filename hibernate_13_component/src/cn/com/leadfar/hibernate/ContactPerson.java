package cn.com.leadfar.hibernate;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private String qq;
	
	private Address home;
	
	private Address work;
	
	public ContactPerson(){
		
	}
	
	public ContactPerson(String name){
		this.name = name;
	}
	
	public void setHomeAddress(String province,String city,String block,String street,String postcode){
		home = new Address();
		home.setBlock(block);
		home.setCity(city);
		home.setPostcode(postcode);
		home.setStreet(street);
		home.setProvince(province);
	}
	
	public void setWorkAddress(String province,String city,String block,String street,String postcode){
		work = new Address();
		work.setBlock(block);
		work.setCity(city);
		work.setPostcode(postcode);
		work.setStreet(street);
		work.setProvince(province);
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

	public Address getHome() {
		return home;
	}

	public void setHome(Address home) {
		this.home = home;
	}

	public Address getWork() {
		return work;
	}

	public void setWork(Address work) {
		this.work = work;
	}

}

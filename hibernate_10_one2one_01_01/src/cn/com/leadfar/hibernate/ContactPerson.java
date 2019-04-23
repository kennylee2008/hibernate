package cn.com.leadfar.hibernate;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private String qq;
	
	private IdCard idCard;
	
	public ContactPerson(){
	}

	public ContactPerson(String name){
		this.name = name;
	}
	
	public ContactPerson(int id, String name, String address, String qq) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.qq = qq;
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

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}

}

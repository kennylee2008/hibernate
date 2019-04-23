package cn.com.leadfar.hibernate;

import java.util.HashMap;
import java.util.Map;

public class ContactPerson {
	private int id;
	private String name;
	private Map<String,Address> addresses;
	
	public ContactPerson(){
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContactPerson other = (ContactPerson) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public ContactPerson(String name){
		this.name = name;
	}
	
	public void addAddress(String type,String province,String city,String postcode){
		if(addresses == null){
			addresses = new HashMap<String,Address>();
		}
		
		Address addr = new Address();
		addr.setProvince(province);
		addr.setCity(city);
		addr.setPostcode(postcode);
		
		addresses.put(type, addr);
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

	public Map<String, Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Map<String, Address> addresses) {
		this.addresses = addresses;
	}
}

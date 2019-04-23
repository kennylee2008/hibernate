package cn.com.leadfar.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name="t_person")
public class ContactPerson {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String address;
	private String qq;
	
	@ManyToOne
	@JoinColumn(name="gid")
	@LazyToOne(LazyToOneOption.PROXY)
	private Group group;
	
	public ContactPerson(){
		
	}
	
	public ContactPerson(String name){
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}

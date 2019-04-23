package cn.com.leadfar.hibernate;

import java.io.Serializable;
import java.util.Date;

public class Student {
	private Serializable _id;
	private String name;
	private String password;
	private int age;
	private String address;
	private Date createTime;
	private Date expireTime;
	
	public Serializable getId() {
		return _id;
	}
	public void setId(Serializable id) {
		this._id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}

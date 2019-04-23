package cn.com.leadfar.hibernate;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private String qq;
	private int age;
	private Group group;
	
	/**
	 * 如果deleted=0，表示本对象是正常状态
	 * 如果deleted=-1，表示本对象已经被删除，在正常的查询中，不应该把这种状态的对象查询出来！
	 */
	private Integer deleted;
	
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

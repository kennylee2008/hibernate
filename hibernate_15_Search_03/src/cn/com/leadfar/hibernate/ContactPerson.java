package cn.com.leadfar.hibernate;

public class ContactPerson {
	private int id;
	private String name;
	private String address;
	private String qq;
	private int age;
	private Group group;
	
	/**
	 * ���deleted=0����ʾ������������״̬
	 * ���deleted=-1����ʾ�������Ѿ���ɾ�����������Ĳ�ѯ�У���Ӧ�ð�����״̬�Ķ����ѯ������
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

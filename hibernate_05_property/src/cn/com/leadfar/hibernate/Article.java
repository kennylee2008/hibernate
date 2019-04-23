package cn.com.leadfar.hibernate;

import java.util.Date;

public class Article {
	private int id;
	private String title;
	private String content;
	private Date createTime;
	private byte[] attachement;
	private String desc;
	private String _author;
	
	public Article(){
	}
	
	public Article(String _author){
		this._author = _author;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public byte[] getAttachement() {
		return attachement;
	}
	public void setAttachement(byte[] attachement) {
		this.attachement = attachement;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}

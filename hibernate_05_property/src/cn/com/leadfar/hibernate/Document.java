package cn.com.leadfar.hibernate;

import java.util.Date;

public class Document {
	private int id;
	private float fvalue;
	private double dvalue;
	private String textvalue;
	private byte[] bolbvalue;
	private String description;
	private Date datevalue;
	private Date timevalue;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getFvalue() {
		return fvalue;
	}
	public void setFvalue(float fvalue) {
		this.fvalue = fvalue;
	}
	public double getDvalue() {
		return dvalue;
	}
	public void setDvalue(double dvalue) {
		this.dvalue = dvalue;
	}
	public String getTextvalue() {
		return textvalue;
	}
	public void setTextvalue(String textvalue) {
		this.textvalue = textvalue;
	}
	public byte[] getBolbvalue() {
		return bolbvalue;
	}
	public void setBolbvalue(byte[] bolbvalue) {
		this.bolbvalue = bolbvalue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDatevalue() {
		return datevalue;
	}
	public void setDatevalue(Date datevalue) {
		this.datevalue = datevalue;
	}
	public Date getTimevalue() {
		return timevalue;
	}
	public void setTimevalue(Date timevalue) {
		this.timevalue = timevalue;
	}
	
}

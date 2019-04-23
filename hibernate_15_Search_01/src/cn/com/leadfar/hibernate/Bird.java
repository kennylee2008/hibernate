package cn.com.leadfar.hibernate;

public class Bird extends Animal implements Thing{
	private int height;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

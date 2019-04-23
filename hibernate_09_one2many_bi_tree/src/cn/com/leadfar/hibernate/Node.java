package cn.com.leadfar.hibernate;

import java.util.Set;

public class Node {
	private int id;
	private int level;
	private String name;
	private Node parent;
	private Set<Node> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Set<Node> getChildren() {
		return children;
	}
	public void setChildren(Set<Node> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

package cn.com.leadfar.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="t_group")
public class Group {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	@OneToMany(mappedBy="group")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<ContactPerson> persons;
	
	public Group(){
	}
	
	public Group(String name){
		this.name = name;
	}
	
	public void addPerson(ContactPerson cp){
		if(persons == null){
			persons = new HashSet<ContactPerson>();
		}
		persons.add(cp);
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

	public Set<ContactPerson> getPersons() {
		return persons;
	}

	public void setPersons(Set<ContactPerson> persons) {
		this.persons = persons;
	}
}

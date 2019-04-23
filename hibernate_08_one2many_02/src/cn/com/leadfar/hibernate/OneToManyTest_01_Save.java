package cn.com.leadfar.hibernate;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_01_Save extends TestCase {
	
	public void testOneToMany01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp2 = new ContactPerson("巴菲特");
			session.save(cp2);//cp2变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp3 = new ContactPerson("路人甲");
			session.save(cp3);//cp3变成了持久化对象状态，现在拥有了数据库标识
			
			Group g1 = new Group("朋友");
			Set<ContactPerson> set1 = new HashSet<ContactPerson>();
			set1.add(cp1);
			set1.add(cp2);
			g1.setPersons(set1);
			session.save(g1);//g1变成了持久化对象状态，现在拥有了数据库标识
			
			Group g2 = new Group("陌生人");
			session.save(g2);//g2变成了持久化对象状态，现在拥有了数据库标识
			
			Group g3 = new Group("商务");
			Set<ContactPerson> set2 = new HashSet<ContactPerson>();
			set2.add(cp3);
			g3.setPersons(set2);
			session.save(g3);//g3变成了持久化对象状态，现在拥有了数据库标识
			
			//建立关联
//			Set<ContactPerson> set1 = new HashSet<ContactPerson>();
//			set1.add(cp1);
//			set1.add(cp2);
//			g1.setPersons(set1);
//			Set<ContactPerson> set2 = new HashSet<ContactPerson>();
//			set2.add(cp3);
//			g3.setPersons(set2);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
	public void testOneToMany02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp2 = new ContactPerson("巴菲特");
			session.save(cp2);//cp2变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp3 = new ContactPerson("路人甲");
			session.save(cp3);//cp3变成了持久化对象状态，现在拥有了数据库标识
			
			Group g1 = new Group("朋友");
			
			/**
			 * 基于GRASP中的信息专家模式，重构Group的代码，使得建立关联更加简便
			 * 信息专家设计原则：尽可能把职责分配在完成这个职责所需信息的那个类中
			 */
			g1.addPerson(cp1);
			g1.addPerson(cp2);
			session.save(g1);//g1变成了持久化对象状态，现在拥有了数据库标识
			
			Group g2 = new Group("陌生人");
			session.save(g2);//g2变成了持久化对象状态，现在拥有了数据库标识
			
			Group g3 = new Group("商务");
			g3.addPerson(cp3);
			session.save(g3);//g3变成了持久化对象状态，现在拥有了数据库标识

			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
	public void testOneToMany03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//重建关联
			Group g1 = (Group)session.get(Group.class, 1);
			System.out.println(g1.getName());
			
			Set<ContactPerson> persons = g1.getPersons();
			
			for(ContactPerson p:persons){
				System.out.println(p.getName());
			}
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
	public void testOneToMany04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//修改关联
			Group g1 = (Group)session.get(Group.class, 1);
			
			ContactPerson cp3 = (ContactPerson)session.load(ContactPerson.class, 3);
			
			g1.addPerson(cp3);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
	public void testOneToMany05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//修改关联
			Group g1 = (Group)session.get(Group.class, 1);
			
			g1.getPersons().clear();
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
}

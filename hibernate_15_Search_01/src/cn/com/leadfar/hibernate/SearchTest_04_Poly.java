package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class SearchTest_04_Poly extends TestCase {
	
	
	public void testSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Animal a = new Animal();
			a.setName("未知动物");
			a.setSex("1");
			session.save(a);
			
			Bird b = new Bird();
			b.setName("老鹰");
			b.setSex("2");
			b.setHeight(5000);
			session.save(b);
			
			Pig p = new Pig();
			p.setName("野猪");
			p.setSex("1");
			p.setWeight(800);
			session.save(p);
			
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
	
	public void testSearch01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//显式多态查询
			List animals = session.createQuery("from Animal").list();
			for (Iterator iterator = animals.iterator(); iterator.hasNext();) {
				Animal a = (Animal) iterator.next();
				System.out.println(a.getName()+","+a);
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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//隐式多态查询
			List things = session.createQuery("from java.lang.Object").list();
			for (Iterator iterator = things.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//隐式多态查询
			List things = session.createQuery("select t from cn.com.leadfar.hibernate.Thing t where t.name = '朋友'").list();
			for (Iterator iterator = things.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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
	
}

package cn.com.leadfar.hibernate;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ExtendsTest_Save extends TestCase {
	
	public void testExtendsSave01(){
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
	
	public void testExtendsSave02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Employee e1 = new Employee();
			e1.setName("员工1");
			session.save(e1);
			
			Employee e2 = new Employee();
			e2.setName("员工2");
			session.save(e2);
			
			Boss boss = new Boss();
			boss.setName("老板");
			session.save(boss);
			
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
	
	public void testExtendsLoad01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//不可以这样加载一个接口
			Animal a = (Animal)session.get(Animal.class, 1);
			
			System.out.println(a.getName());
			
			if(a instanceof Bird){
				System.out.println("这是一只鸟！");
			}
			if(a instanceof Pig){
				System.out.println("这是一头猪！");
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
	
	public void testExtendsFind02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//Hibernate支持基于接口的多态查询
			List somethings = session.createQuery("select s from Animal s").list();
			
			System.out.println(somethings.size());
			
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
	
	public void testExtendsFind03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//Hibernate支持基于接口的多态查询
			List somethings = session.createQuery("select s from cn.com.leadfar.hibernate.Mankind s").list();
			
			System.out.println(somethings.size());
			
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
	
	public void testExtendsFind04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//Hibernate支持基于接口的多态查询
			List somethings = session.createQuery("select o from java.lang.Object o").list();
			
			System.out.println(somethings.size());
			
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

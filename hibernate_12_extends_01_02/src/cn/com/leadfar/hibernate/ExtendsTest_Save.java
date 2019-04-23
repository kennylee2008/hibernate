package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ExtendsTest_Save extends TestCase {
	
	public void testExtendsSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
//			Animal a = new Animal();
//			a.setName("未知动物");
//			a.setSex("1");
//			session.save(a);
			
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
	
	public void testExtendsLoad01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Animal a = (Animal)session.get(Animal.class, 2);
			
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
	
	public void testExtendsLoad0101(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Animal a = (Animal)session.get(Object.class, 2);
			
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
	
	public void testExtendsLoad02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Animal a = (Animal)session.load(Bird.class, 2);
			
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
	
}

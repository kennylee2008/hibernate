package cn.com.leadfar.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ManyToOneTest_02_Load extends TestCase {
	public void testManyToOne01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp1 = (ContactPerson)session.load(ContactPerson.class, 1);
			
			System.out.println(cp1.getName());
			
			Group g = cp1.getGroup();
			
			System.out.println(g.getName());
			
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
	
	public void testManyToOne02(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		ContactPerson cp1 = null;
		try{
			//开启事务
			session.beginTransaction();
			
			cp1 = (ContactPerson)session.get(ContactPerson.class, 1);
			
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
	
		//cp1对象是一个离线对象
		System.out.println(cp1.getName());
		
		Group g = cp1.getGroup();
		
		System.out.println(g.getName());
	}
}

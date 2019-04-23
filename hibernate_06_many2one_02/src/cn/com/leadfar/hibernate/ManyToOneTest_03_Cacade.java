package cn.com.leadfar.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ManyToOneTest_03_Cacade extends TestCase {
	public void testCacade01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g1 = new Group("朋友");
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			
			cp1.setGroup(g1);
			
			//在hibernate中，一个持久化对象不可以引用到一个瞬时对象！
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			
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
	
	public void testCacade02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			ContactPerson cp1 = (ContactPerson)session.get(ContactPerson.class, 3);
			
			session.delete(cp1);
			
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
	
	public void testCacade03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			ContactPerson cp1 = new ContactPerson(); 
			cp1.setId(1); //【离线对象】
			
			//针对离线对象执行删除操作，cacade级联特性会失效！
			session.delete(cp1);
			
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

package cn.com.leadfar.hibernate;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class CollectionTest extends TestCase {
	
	public void testCollectionSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("张三");
			cp.addQq("2342342");
			cp.addQq("95865956");
			cp.addQq("958659589");
			cp.addQq("3984885");
			session.save(cp);
			
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
	
	public void testCollectionLoad01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class,1);
			
			List<String> qqs = cp.getQqs();
			for(String qq:qqs){
				System.out.println(qq);
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
	
	public void testCollectionUpdate01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class,1);
			
			Collections.reverse(cp.getQqs());
			
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

package cn.com.leadfar.hibernate;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_Update_Association extends TestCase {
	
	public void testOneToManyUpdate01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 3);
			
			ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			g3.getPersons().add(cp2);
			
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
	
	public void testOneToManyUpdate02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 3);
			
			//ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			ContactPerson cp4 = new ContactPerson("路人乙");
			
			g3.getPersons().add(cp4);
			
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

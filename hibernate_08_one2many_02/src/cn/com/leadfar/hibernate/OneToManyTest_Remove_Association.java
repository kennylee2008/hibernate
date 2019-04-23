package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_Remove_Association extends TestCase {
	
	public void testOneToManyRemoveAssociation01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 1);
			
			ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			g3.getPersons().remove(cp2);
			
			session.delete(g3);
			
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

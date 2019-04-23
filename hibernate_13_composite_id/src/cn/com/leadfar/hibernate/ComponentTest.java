package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ComponentTest extends TestCase {
	
	public void testComponentSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("张三");
			cp.setAddress("北京");
			
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
	
}

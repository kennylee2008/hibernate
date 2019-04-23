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
			cp.setQq("232423423");
			cp.setHomeAddress("beijing", "beijing", "fengtai", "guangwai da jie", "834833");
			cp.setWorkAddress("shanghai", "shanghai", "pudong", "xxx", "453454");
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

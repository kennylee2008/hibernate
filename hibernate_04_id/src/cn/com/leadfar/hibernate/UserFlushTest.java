package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class UserFlushTest extends TestCase{

	public void testUser01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			User u = new User();
			u.setUsername("张三");
			u.setValidCode("xxx");
			//u.setValidCode(""+(u.getId()*100));
			session.save(u);
			
			//u.setValidCode(""+(u.getId()*100));
			
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
	
	public void testUser02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			for(int i=0; i<10; i++){
				User u = new User();
				u.setUsername("用户"+i);
				u.setValidCode("xxx");
				session.save(u);
				u.setValidCode(""+(u.getId()*100));
				
				//必须先flush
				session.flush();
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

package cn.com.leadfar.hibernate;

import java.sql.Connection;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Session;

public class LongApplicationPersistenceContextTest extends TestCase{
	
	/**
	 * 在一个两层架构的应用程序中，一个用户事务，可以使用同一个session
	 */
	public void testLongApplicationPersistenceContext01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g = (Group)session.load(Group.class, 1);
			System.out.println(g.getName());
			
			//g.setName("组"+new Random().nextInt(99999));
			
			//提交事务
			session.getTransaction().commit();
			
			//断开链接
			Connection connection = session.disconnect();
			
			//第二次请求的时候，重新链接
			session.reconnect(connection);
			session.beginTransaction();
			
			g.setName("组"+new Random().nextInt(99999));
			
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

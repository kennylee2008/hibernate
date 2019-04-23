package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest extends TestCase{

	public void testId01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Student s = new Student(); 
			s.setId("kdsjfdksjfd");
			s.setName("李四");
			s.setPassword("zhangsan");
			s.setAge(19);
			s.setCreateTime(new Date());
			
			//保存对象
			session.save(s); 

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
	
	public void testId02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Person p = new Person();
			p.setName("张三");
			
			//保存对象
			session.save(p); 

			//强制hibernate把内存中实体对象的状态同步到数据库中！
			session.flush();
			
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
	
	public void testId03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			User u = new User();
			u.setUsername("zhangsan");
			
			//保存对象
			session.save(u); 
			
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

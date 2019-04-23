package cn.com.leadfar.hibernate;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

public class StudentTest03 extends TestCase{

	public void testQuery01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//通过HQL语言来查询
			Query query = session.createQuery("select s FrOm Student s");
			
			List<Student> students = query.list();
			for(Student s:students){
				System.out.println(s.getId()+","+s.getName());
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
	
	public void testDelete01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 1);
			session.delete(s); //针对持久化实体对象执行删除操作
			
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
	
	public void testDelete02(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = (Student)session.load(Student.class, 2);
			session.delete(s); //针对持久化实体对象执行删除操作
			
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
	
	public void testDelete03(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = new Student();
			s.setId(3); //【离线对象】
			session.delete(s); //针对离线对象执行删除操作
			
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

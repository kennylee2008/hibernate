package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest02 extends TestCase{

	public void testGet01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 1、Get方法，会马上发出select语句
			 * 2、Get方法的返回值的类型就是Student类型
			 * 3、Get方法查询的实体对象如果不存在，会返回null
			 * 4、懒加载机制对get方法不起作用！
			 */
			Student s = (Student)session.get(Student.class, 10);
			
			System.out.println(s.getId());
			System.out.println(s.getName());
			System.out.println(s.getAddress());
			
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
	
	public void testLoad01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 1、Load方法，hibernate不会马上发出select语句（懒加载,LazyLoad机制）
			 * 2、Load方法，加载的是一个Student的代理对象
			 * 3、Load方法，如果加载的对象不存在，仍然会返回代理对象，但是访问其中的属性（非id）的时候，会抛出ObjectNotFoundException异常！
			 * 4、如果取消了懒加载机制（在class上配置lazy="false"），load方法和get方法就没有区别了
			 */
			Student s = (Student)session.load(Student.class, 1);
			
			System.out.println(s.getId());
			System.out.println(s.getName());
			System.out.println(s.getAddress());
			
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

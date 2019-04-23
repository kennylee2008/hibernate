package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class SearchTest_06_DML extends TestCase {
	
	
	public void testDelete01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "delete ContactPerson cp where cp.id in (:ids)";
			
			List ids = new ArrayList();
			ids.add(1);
			ids.add(2);
			ids.add(3);
			
			session.createQuery(hql)
				.setParameterList("ids", ids)
				.executeUpdate();
			
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
	
	public void testUpdate01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "update ContactPerson cp set cp.createTime = ? where id > ?";

			session.createQuery(hql)
				.setParameter(0, new Date())
				.setParameter(1, 100)
				.executeUpdate();
			
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
	
	public void testInsert01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "insert into ContactPerson (name) select g.name from Group g";

			session.createQuery(hql)
				.executeUpdate();
			
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

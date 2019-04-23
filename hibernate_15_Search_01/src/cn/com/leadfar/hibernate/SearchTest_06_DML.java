package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class SearchTest_06_DML extends TestCase {
	
	
	public void testDelete01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "delete ContactPerson cp where cp.id in (:ids)";
			
			List ids = new ArrayList();
			ids.add(1);
			ids.add(2);
			ids.add(3);
			
			session.createQuery(hql)
				.setParameterList("ids", ids)
				.executeUpdate();
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); 
		}
	}
	
	public void testUpdate01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "update ContactPerson cp set cp.createTime = ? where id > ?";

			session.createQuery(hql)
				.setParameter(0, new Date())
				.setParameter(1, 100)
				.executeUpdate();
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); 
		}
	}
	
	public void testInsert01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "insert into ContactPerson (name) select g.name from Group g";

			session.createQuery(hql)
				.executeUpdate();
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); 
		}
	}
	
}

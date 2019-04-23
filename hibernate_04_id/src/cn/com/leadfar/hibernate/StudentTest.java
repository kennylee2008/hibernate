package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest extends TestCase{

	public void testId01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Student s = new Student(); 
			s.setId("kdsjfdksjfd");
			s.setName("����");
			s.setPassword("zhangsan");
			s.setAge(19);
			s.setCreateTime(new Date());
			
			//�������
			session.save(s); 

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
	
	public void testId02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Person p = new Person();
			p.setName("����");
			
			//�������
			session.save(p); 

			//ǿ��hibernate���ڴ���ʵ������״̬ͬ�������ݿ��У�
			session.flush();
			
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
	
	public void testId03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			User u = new User();
			u.setUsername("zhangsan");
			
			//�������
			session.save(u); 
			
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

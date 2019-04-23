package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class UserFlushTest extends TestCase{

	public void testUser01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			User u = new User();
			u.setUsername("����");
			u.setValidCode("xxx");
			//u.setValidCode(""+(u.getId()*100));
			session.save(u);
			
			//u.setValidCode(""+(u.getId()*100));
			
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
	
	public void testUser02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			for(int i=0; i<10; i++){
				User u = new User();
				u.setUsername("�û�"+i);
				u.setValidCode("xxx");
				session.save(u);
				u.setValidCode(""+(u.getId()*100));
				
				//������flush
				session.flush();
			}
			
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

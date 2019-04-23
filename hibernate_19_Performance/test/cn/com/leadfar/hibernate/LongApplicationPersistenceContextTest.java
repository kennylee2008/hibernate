package cn.com.leadfar.hibernate;

import java.sql.Connection;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Session;

public class LongApplicationPersistenceContextTest extends TestCase{
	
	/**
	 * ��һ������ܹ���Ӧ�ó����У�һ���û����񣬿���ʹ��ͬһ��session
	 */
	public void testLongApplicationPersistenceContext01(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g = (Group)session.load(Group.class, 1);
			System.out.println(g.getName());
			
			//g.setName("��"+new Random().nextInt(99999));
			
			//�ύ����
			session.getTransaction().commit();
			
			//�Ͽ�����
			Connection connection = session.disconnect();
			
			//�ڶ��������ʱ����������
			session.reconnect(connection);
			session.beginTransaction();
			
			g.setName("��"+new Random().nextInt(99999));
			
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

package cn.com.leadfar.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ManyToOneTest_02_Load extends TestCase {
	public void testManyToOne01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp1 = (ContactPerson)session.load(ContactPerson.class, 1);
			
			System.out.println(cp1.getName());
			
			Group g = cp1.getGroup();
			
			System.out.println(g.getName());
			
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
	
	public void testManyToOne02(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		ContactPerson cp1 = null;
		try{
			//��������
			session.beginTransaction();
			
			cp1 = (ContactPerson)session.get(ContactPerson.class, 1);
			
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
	
		//cp1������һ�����߶���
		System.out.println(cp1.getName());
		
		Group g = cp1.getGroup();
		
		System.out.println(g.getName());
	}
}

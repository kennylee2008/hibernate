package cn.com.leadfar.hibernate;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_Update_Association extends TestCase {
	
	public void testOneToManyUpdate01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 3);
			
			ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			g3.getPersons().add(cp2);
			
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
	
	public void testOneToManyUpdate02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 3);
			
			//ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			ContactPerson cp4 = new ContactPerson("·����");
			
			g3.getPersons().add(cp4);
			
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

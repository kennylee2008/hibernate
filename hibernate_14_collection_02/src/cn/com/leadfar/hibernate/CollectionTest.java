package cn.com.leadfar.hibernate;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class CollectionTest extends TestCase {
	
	public void testCollectionSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("����");
			cp.addQq("2342342");
			cp.addQq("95865956");
			cp.addQq("958659589");
			cp.addQq("3984885");
			session.save(cp);
			
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
	
	public void testCollectionLoad01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class,1);
			
			List<String> qqs = cp.getQqs();
			for(String qq:qqs){
				System.out.println(qq);
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
	
	public void testCollectionUpdate01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class,1);
			
			Collections.reverse(cp.getQqs());
			
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

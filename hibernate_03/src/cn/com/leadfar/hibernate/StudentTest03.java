package cn.com.leadfar.hibernate;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

public class StudentTest03 extends TestCase{

	public void testQuery01(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͨ��HQL��������ѯ
			Query query = session.createQuery("select s FrOm Student s");
			
			List<Student> students = query.list();
			for(Student s:students){
				System.out.println(s.getId()+","+s.getName());
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
	
	public void testDelete01(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 1);
			session.delete(s); //��Գ־û�ʵ�����ִ��ɾ������
			
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
	
	public void testDelete02(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = (Student)session.load(Student.class, 2);
			session.delete(s); //��Գ־û�ʵ�����ִ��ɾ������
			
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
	
	public void testDelete03(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = new Student();
			s.setId(3); //�����߶���
			session.delete(s); //������߶���ִ��ɾ������
			
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

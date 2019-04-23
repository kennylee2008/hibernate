package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class SearchTest_04_Poly extends TestCase {
	
	
	public void testSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Animal a = new Animal();
			a.setName("δ֪����");
			a.setSex("1");
			session.save(a);
			
			Bird b = new Bird();
			b.setName("��ӥ");
			b.setSex("2");
			b.setHeight(5000);
			session.save(b);
			
			Pig p = new Pig();
			p.setName("Ұ��");
			p.setSex("1");
			p.setWeight(800);
			session.save(p);
			
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
	
	public void testSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ʽ��̬��ѯ
			List animals = session.createQuery("from Animal").list();
			for (Iterator iterator = animals.iterator(); iterator.hasNext();) {
				Animal a = (Animal) iterator.next();
				System.out.println(a.getName()+","+a);
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
	
	public void testSearch02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ʽ��̬��ѯ
			List things = session.createQuery("from java.lang.Object").list();
			for (Iterator iterator = things.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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
	
	public void testSearch03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ʽ��̬��ѯ
			List things = session.createQuery("select t from cn.com.leadfar.hibernate.Thing t where t.name = '����'").list();
			for (Iterator iterator = things.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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

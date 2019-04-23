package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ExtendsTest_Save extends TestCase {
	
	public void testExtendsSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
//			Animal a = new Animal();
//			a.setName("δ֪����");
//			a.setSex("1");
//			session.save(a);
			
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
	
	public void testExtendsLoad01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Animal a = (Animal)session.get(Animal.class, 2);
			
			System.out.println(a.getName());
			
			if(a instanceof Bird){
				System.out.println("����һֻ��");
			}
			if(a instanceof Pig){
				System.out.println("����һͷ��");
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
	
	public void testExtendsLoad0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Animal a = (Animal)session.get(Object.class, 2);
			
			System.out.println(a.getName());
			
			if(a instanceof Bird){
				System.out.println("����һֻ��");
			}
			if(a instanceof Pig){
				System.out.println("����һͷ��");
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
	
	public void testExtendsLoad02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Animal a = (Animal)session.load(Bird.class, 2);
			
			System.out.println(a.getName());
			
			if(a instanceof Bird){
				System.out.println("����һֻ��");
			}
			if(a instanceof Pig){
				System.out.println("����һͷ��");
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

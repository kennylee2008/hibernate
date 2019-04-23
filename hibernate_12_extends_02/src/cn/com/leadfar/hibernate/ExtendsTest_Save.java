package cn.com.leadfar.hibernate;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ExtendsTest_Save extends TestCase {
	
	public void testExtendsSave01(){
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
	
	public void testExtendsSave02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Employee e1 = new Employee();
			e1.setName("Ա��1");
			session.save(e1);
			
			Employee e2 = new Employee();
			e2.setName("Ա��2");
			session.save(e2);
			
			Boss boss = new Boss();
			boss.setName("�ϰ�");
			session.save(boss);
			
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
			
			//��������������һ���ӿ�
			Animal a = (Animal)session.get(Animal.class, 1);
			
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
	
	public void testExtendsFind02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//Hibernate֧�ֻ��ڽӿڵĶ�̬��ѯ
			List somethings = session.createQuery("select s from Animal s").list();
			
			System.out.println(somethings.size());
			
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
	
	public void testExtendsFind03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//Hibernate֧�ֻ��ڽӿڵĶ�̬��ѯ
			List somethings = session.createQuery("select s from cn.com.leadfar.hibernate.Mankind s").list();
			
			System.out.println(somethings.size());
			
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
	
	public void testExtendsFind04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//Hibernate֧�ֻ��ڽӿڵĶ�̬��ѯ
			List somethings = session.createQuery("select o from java.lang.Object o").list();
			
			System.out.println(somethings.size());
			
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

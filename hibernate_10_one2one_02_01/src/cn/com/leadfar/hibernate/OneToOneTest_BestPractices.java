package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_BestPractices extends TestCase {
	
	public void testOneToOneSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp = new ContactPerson("����");
			session.save(cp);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("123456789012345678");
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneSave0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			//session.save(cp);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("111111111111111111");
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneSave02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp = new ContactPerson("����");
			session.save(cp);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("873483274974837483");
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneLoad01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��һ�������ӣ���ѯ��
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			IdCard ic = cp.getIdCard();
			
			System.out.println(ic.getSn());
			
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
	
	public void testOneToOneLoad02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//IdCard����һ��many-to-one
			//���ԣ��ڼ���IdCard��ʱ�򣬿���֪��IdCard��û�ж�Ӧ��ContactPerson
			//�������ֻ��һ����ѯ���
			IdCard ic = (IdCard)session.load(IdCard.class,1);
			System.out.println(ic.getSn());
			
			ContactPerson cp = ic.getPerson();
			
			//���潫������ѯ����ѯContactPerson����
			System.out.println(cp.getName());
			
			System.out.println((cp.getIdCard() == ic)+","+(cp == ic.getPerson()));
			
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

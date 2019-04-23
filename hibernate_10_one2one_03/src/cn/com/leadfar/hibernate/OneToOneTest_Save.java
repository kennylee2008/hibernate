package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_Save extends TestCase {
	
	public void testOneToOneSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp1 = new ContactPerson("����");
			cp1.setId(10);
			session.save(cp1);
			
			ContactPerson cp2 = new ContactPerson("����");
			cp2.setId(20);
			session.save(cp2);
			
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
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 10);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("123456789012345999");
			idcard.setId(20);
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
	
	public void testOneToOneSave03(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 20);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("888888888888888888");
			idcard.setId(10);
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
			
			//�ȱ���ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 20);
			System.out.println(cp.getName()+",�����֤�����ǣ�"+cp.getIdCard().getSn());
			
			
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

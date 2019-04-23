package cn.com.leadfar.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ManyToOneTest_03_Cacade extends TestCase {
	public void testCacade01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g1 = new Group("����");
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			
			cp1.setGroup(g1);
			
			//��hibernate�У�һ���־û����󲻿������õ�һ��˲ʱ����
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			
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
	
	public void testCacade02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			
			ContactPerson cp1 = (ContactPerson)session.get(ContactPerson.class, 3);
			
			session.delete(cp1);
			
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
	
	public void testCacade03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			
			ContactPerson cp1 = new ContactPerson(); 
			cp1.setId(1); //�����߶���
			
			//������߶���ִ��ɾ��������cacade�������Ի�ʧЧ��
			session.delete(cp1);
			
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

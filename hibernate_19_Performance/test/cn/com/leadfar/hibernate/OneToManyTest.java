package cn.com.leadfar.hibernate;

import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest extends TestCase{
	
	
	public void testOne2Many01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g1 = new Group("����");
			session.save(g1);
			Group g2 = new Group("����");
			session.save(g2);
			Group g3 = new Group("İ����");
			session.save(g3);			
			
			Group g4 = new Group("����");
			session.save(g4);
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			cp1.setGroup(g1); //�ӡ��ࡱһ�˹�������֮��Ĺ���
			session.save(cp1);
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			cp2.setGroup(g1); //�ӡ��ࡱһ�˹�������֮��Ĺ���
			session.save(cp2);
			ContactPerson cp3 = new ContactPerson("·�˼�");
			cp3.setGroup(g3); //�ӡ��ࡱһ�˹�������֮��Ĺ���
			session.save(cp3);
			
			ContactPerson cp4 = new ContactPerson("·����");
			session.save(cp4);
			
			
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
	
	public void testOne2Many02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			for(int i=0; i<50; i++){
				
				Group g = new Group("��"+i);
				session.save(g);
				
				ContactPerson cp = new ContactPerson("������Ա"+i);
				cp.setGroup(g);
				session.save(cp);
				
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

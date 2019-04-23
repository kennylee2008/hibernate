package cn.com.leadfar.hibernate;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_01_Save extends TestCase {
	
	public void testOneToMany01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			session.save(cp2);//cp2����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp3 = new ContactPerson("·�˼�");
			session.save(cp3);//cp3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g1 = new Group("����");
			Set<ContactPerson> set1 = new HashSet<ContactPerson>();
			set1.add(cp1);
			set1.add(cp2);
			g1.setPersons(set1);
			session.save(g1);//g1����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g2 = new Group("İ����");
			session.save(g2);//g2����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g3 = new Group("����");
			Set<ContactPerson> set2 = new HashSet<ContactPerson>();
			set2.add(cp3);
			g3.setPersons(set2);
			session.save(g3);//g3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			//��������
//			Set<ContactPerson> set1 = new HashSet<ContactPerson>();
//			set1.add(cp1);
//			set1.add(cp2);
//			g1.setPersons(set1);
//			Set<ContactPerson> set2 = new HashSet<ContactPerson>();
//			set2.add(cp3);
//			g3.setPersons(set2);
			
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
	
	public void testOneToMany02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			session.save(cp2);//cp2����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp3 = new ContactPerson("·�˼�");
			session.save(cp3);//cp3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g1 = new Group("����");
			
			/**
			 * ����GRASP�е���Ϣר��ģʽ���ع�Group�Ĵ��룬ʹ�ý����������Ӽ��
			 * ��Ϣר�����ԭ�򣺾����ܰ�ְ�������������ְ��������Ϣ���Ǹ�����
			 */
			g1.addPerson(cp1);
			g1.addPerson(cp2);
			session.save(g1);//g1����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g2 = new Group("İ����");
			session.save(g2);//g2����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g3 = new Group("����");
			g3.addPerson(cp3);
			session.save(g3);//g3����˳־û�����״̬������ӵ�������ݿ��ʶ

			
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
	
	public void testOneToMany03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ؽ�����
			Group g1 = (Group)session.get(Group.class, 1);
			System.out.println(g1.getName());
			
			Set<ContactPerson> persons = g1.getPersons();
			
			for(ContactPerson p:persons){
				System.out.println(p.getName());
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
	
	public void testOneToMany04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�޸Ĺ���
			Group g1 = (Group)session.get(Group.class, 1);
			
			ContactPerson cp3 = (ContactPerson)session.load(ContactPerson.class, 3);
			
			g1.addPerson(cp3);
			
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
	
	public void testOneToMany05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�޸Ĺ���
			Group g1 = (Group)session.get(Group.class, 1);
			
			g1.getPersons().clear();
			
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

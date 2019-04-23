package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public class CacheTest_01_Level2 extends TestCase {
	
	public void testSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//******************* �Ӷ��һ�˹�������֮��Ĺ�����ϵ ***********************//
			Group g1 = new Group("����");
			session.save(g1);//g1����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g2 = new Group("İ����");
			session.save(g2);//g2����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g3 = new Group("����");
			session.save(g3);//g3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			//����cp1��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp1.setGroup(g1);
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			//����cp2��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp2.setGroup(g1);
			session.save(cp2);//cp2����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp3 = new ContactPerson("·�˼�");
			//����cp3��g2����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g2������g2�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp3.setGroup(g2);			
			session.save(cp3);//cp3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
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
	
	public void testSave02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			for(int i=0; i<200; i++){
				ContactPerson cp = new ContactPerson("��Ա"+new Random().nextInt(99999));
				cp.setAddress("����"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //����״̬
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
	
	public void testSave03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g = new Group();
			g.setName("ĳĳ��");
			session.save(g);
			
			for(int i=0; i<100; i++){
				ContactPerson cp = new ContactPerson("��Ա"+new Random().nextInt(99999));
				cp.setAddress("����"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //����״̬
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
	
	public void testSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȿɴӶ����������Ҳ������������д
			//session.setCacheMode(CacheMode.NORMAL);
			
			//ֻ�Ӷ��������������������������д
			//session.setCacheMode(CacheMode.GET);
			
			//ֻ����������д�������Ӷ��������
			//session.setCacheMode(CacheMode.PUT);
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			
			
			//ͨ��SessionFactory�Զ���������й���
			//session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
		
		//������session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͨ��SessionFactory�Զ���������й���
			//session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
	
	public void testSearch0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȿɴӶ����������Ҳ������������д
			//session.setCacheMode(CacheMode.NORMAL);
			
			//ֻ�Ӷ��������������������������д
			//session.setCacheMode(CacheMode.GET);
			
			//ֻ����������д�������Ӷ��������
			session.setCacheMode(CacheMode.PUT);
			
			//����ѯ���԰Ѷ������������棬��ˣ��ڵڶ���session�н���get/load��
			//�����ٷ�����ѯ���
			session.createQuery("from ContactPerson").list();
			
//			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
//			System.out.println(cp2.getName());
//			
//			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
//			System.out.println(cp3.getName());
			
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͨ��SessionFactory�Զ���������й���
			session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//��Ϊ�����˶������棬���Բ��ٷ���SQL��ѯ���
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
			
			//read-only���棬�������
			ContactPerson cp = new ContactPerson("xxxxx");
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
	
	public void testSearch03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//read-only���棬�����Ը���
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			cp.setName("�û�8888888");
			
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
	
	public void testSearch04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//IGNORE��Ҳ������������
			session.setCacheMode(CacheMode.IGNORE);
			
			//read-only���棬�����Ը���
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			cp.setName("�û�8888888");
			
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
	
	public void testSearch05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//IGNORE��Ҳ������������
			session.setCacheMode(CacheMode.IGNORE);
			
			//read-only���棬������ɾ����
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 101);
			session.delete(cp);
			
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

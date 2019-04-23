package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

public class SearchTest_01_Query extends TestCase {
	
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
			
			//��ʽ������
			String hql = "select p.id,p.name,p.group.name from ContactPerson p";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
			
			//��ʽ������
			String hql = "select p.id,p.name,p.group.name from ContactPerson p where p.group.name = ?";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, "����");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]);
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
			
			//��ʽ������
			String hql = "select p.id,p.name,g.name from ContactPerson p join p.group g where g.name = ?";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, "����");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//���������ӣ���ѯ�����е���ϵ�˼�����������
			String hql = "select p.id,p.name,g.name from ContactPerson p left join p.group g";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//���������ӣ���ѯ�����е���ϵ�˼�����������
			String hql = "select p.id,p.name,g.name from Group g right join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch06(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ѯ�����飬������Ա������
			String hql = "select p.id,p.name,g.name from Group g right join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch07(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ѯ�����飬������Ա������
			String hql = "select g.id,g.name,p.name from Group g left join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch08(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ѯ�����飬������Ա������
			String hql = "select new map(g.id as groupId,g.name as groupName,count(p) as personSize) from Group g left join g.persons p group by g.id,g.name";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object cp = (Object) iterator.next();
				System.out.println(cp);
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

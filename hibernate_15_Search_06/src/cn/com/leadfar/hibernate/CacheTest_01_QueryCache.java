package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public class CacheTest_01_QueryCache extends TestCase {
	
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
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);

			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%13%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			//List persons = query.list(); //N����ѯ���
			Iterator iterator = query.iterate(); //N + 1����ѯ���
			for (; iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�����ѯ���棡
			session.getSessionFactory().getCache()
				.evictDefaultQueryRegion();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//����ѯʹ�ò�ѯ����
			query.setCacheable(true);
			
			//�����˲�ѯ����֮��list()����Ҳ�����û���!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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

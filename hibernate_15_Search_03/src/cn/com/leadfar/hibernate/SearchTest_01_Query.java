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
				cp.setAge(new Random().nextInt(99));
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
			
			String hql = "select p from ContactPerson p where p.name like ? and p.deleted = 0";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			
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
	
	public void testSearch0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();

			session.enableFilter("ageFilter1").setParameter("maxAge", 28);
			
			String hql = "select p from ContactPerson p where p.name like ?";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");

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
	
	public void testSearch02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String countHql = "select count(*) from ContactPerson p where p.name like ? and p.deleted = 0";
			Query countQuery = session.createQuery(countHql);
			countQuery.setParameter(0, "%1%");
			long total = (Long)countQuery.uniqueResult();
			System.out.println("�ܼ�¼����"+total);
			
			String hql = "select p from ContactPerson p where p.name like ? and p.deleted = 0";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			query.setFirstResult(20);
			query.setMaxResults(15);
			
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
	
	public void testSearch0201(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
				.setParameter("deletedCondition", 0); //��ѯ������ContactPerson����
			
			String countHql = "select count(*) from ContactPerson p where p.name like ?";
			Query countQuery = session.createQuery(countHql);
			countQuery.setParameter(0, "%1%");
			long total = (Long)countQuery.uniqueResult();
			System.out.println("�ܼ�¼����"+total);
			
			String hql = "select p from ContactPerson p where p.name like ?";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			query.setFirstResult(20);
			query.setMaxResults(15);
			
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
	
	public void testSearch03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ѯ����
			ContactPerson c = new ContactPerson();
			c.setName("1");
			c.setAddress("2");
			c.setDeleted(0);
			
			//��ѯ�ܼ�¼��
			Criteria criteria = session.createCriteria(ContactPerson.class);
			criteria.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			criteria.setProjection(
				Projections.rowCount()
			);
			
			long total = (Long)criteria.uniqueResult();
			System.out.println("�ܼ�¼����"+total);
			
			//��ѯ��ǰҳ������
			Criteria current = session.createCriteria(ContactPerson.class);
			current.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			List persons = current.list();
			
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
	
	public void testSearch0301(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
			.setParameter("deletedCondition", 0); //��ѯ������ContactPerson����

			//��ѯ����
			ContactPerson c = new ContactPerson();
			c.setName("1");
			c.setAddress("2");
			
			//��ѯ�ܼ�¼��
			Criteria criteria = session.createCriteria(ContactPerson.class);
			criteria.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			criteria.setProjection(
				Projections.rowCount()
			);
			
			long total = (Long)criteria.uniqueResult();
			System.out.println("�ܼ�¼����"+total);
			
			//��ѯ��ǰҳ������
			Criteria current = session.createCriteria(ContactPerson.class);
			current.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			List persons = current.list();
			
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
			
			//������һ��Group����ϣ���õ�Group���������ContactPerson�����б�
			Group g = (Group)session.load(Group.class, 1);
			
			System.out.println("�顾"+g.getName()+"�������У�"+g.getPersons().size()+"��");
			
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
	
	public void testSearch0401(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
				.setParameter("deletedCondition", 0); //��ѯ������ContactPerson����
			
			//������һ��Group����ϣ���õ�Group���������ContactPerson�����б�
			Group g = (Group)session.load(Group.class, 1);
			
			System.out.println("�顾"+g.getName()+"�������У�"+g.getPersons().size()+"��");
			Set<ContactPerson> persons = g.getPersons();
			for(ContactPerson cp:persons){
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
	
	public void testSearch0402(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//������һ��Group����ϣ���õ�Group���������ContactPerson�����б�
			Group g = (Group)session.load(Group.class, 1);
			
			//�Լ��Ͻ��й���
			List<ContactPerson> persons = session.createFilter(g.getPersons(), "where name like ?")
				.setParameter(0, "%��%")
				.list();
			
			//System.out.println("�顾"+g.getName()+"�������У�"+g.getPersons().size()+"��");
			//Set<ContactPerson> persons = g.getPersons();
			for(ContactPerson cp:persons){
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
	
	public void testSearch0403(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�˹�������Ҳ���Զ�����
			session.enableFilter("deletedFilter")
			.setParameter("deletedCondition", 0); 
			
			//������һ��Group����ϣ���õ�Group���������ContactPerson�����б�
			Group g = (Group)session.load(Group.class, 1);
			
			//�Լ��Ͻ��й���
			List<String> persons = session.createFilter(g.getPersons(), "select name where id > 201")
				.list();
			
			for(String cp:persons){
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
	
	public void testSearch0405(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			
			
			//������һ��Group����ϣ���õ�Group���������ContactPerson�����б�
			Group g = (Group)session.load(Group.class, 1);
			
			//�Լ��Ͻ��й���
			//select name
			//select name order by id
			//select name where id > 200 order by id
			List<String> persons = session.createFilter(g.getPersons(), "select name  ")
				.list();
			
			for(String cp:persons){
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
	
	public void testDeletePerson(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��ɾ��
			for(int i=30; i<80; i++){
				ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, i);
				cp.setDeleted(-1); //��ɾ��
				session.update(cp);
			}
			
			for(int i= 230; i<260; i++){
				ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, i);
				cp.setDeleted(-1); //��ɾ��
				session.update(cp);
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

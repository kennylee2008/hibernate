package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public class SearchTest_01_Query_01 extends TestCase {
	
	public void testSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.createTime = ?";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			//query.setParameter(0, "2011-11-24 14:40:18",Hibernate.TIMESTAMP);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			//����������ѯ
			String hql = "select new Animal(p.id,p.name) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			//query.setParameter(0, "2011-11-24 14:40:18",Hibernate.TIMESTAMP);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Animal cp = (Animal) iterator.next();
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
			
			//Ҳ����������ѯ
			String hql = "select new cn.com.leadfar.hibernate.PersonVO(p.id,p.name) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			//query.setParameter(0, "2011-11-24 14:40:18",Hibernate.TIMESTAMP);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				PersonVO cp = (PersonVO) iterator.next();
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
			
			//Ҳ����������ѯ
			String hql = "select new map(p.id,p.name) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//map��key��0��1��value�Ǿ����id��name��ֵ
				Map cp = (Map) iterator.next();
				System.out.println(cp.get("0")+","+cp.get("1"));
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
	
	public void testSearch0302(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//Ҳ����������ѯ
			String hql = "select new map(p.id as pid,p.name as pname) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//map��key��pid��pname��value�Ǿ����id��name��ֵ
				Map cp = (Map) iterator.next();
				System.out.println(cp.get("pid")+","+cp.get("pname"));
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
	
	public void testSearch030201(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//Ҳ����������ѯ
			String hql = "select new map(p.id as id,p.name as name) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//map��key��pid��pname��value�Ǿ����id��name��ֵ
				Map cp = (Map) iterator.next();
				System.out.println(cp.get("id")+","+cp.get("name")+" --> "+cp);
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
	
	public void testSearch0303(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//������������ѯ
			//[cause=org.hibernate.PropertyNotFoundException: no appropriate constructor in class: java.util.HashMap]
			String hql = "select new java.util.HashMap(p.id as pid,p.name as pname) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//map��key��pid��pname��value�Ǿ����id��name��ֵ
				Map cp = (Map) iterator.next();
				System.out.println(cp.get("pid")+","+cp.get("pname"));
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
	
	public void testSearch0304(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����������ѯ
			String hql = "select new list(p.id as pid,p.name as pname) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//list[0]=id,list[1]=name
				List cp = (List) iterator.next();
				System.out.println(cp.get(0)+","+cp.get(1));
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
	
	public void testSearch0305(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��������������ѯ��
			String hql = "select new set(p.id as pid,p.name as pname) from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				
				//
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
	
	public void testSearch04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p";
			
			Query query = session.createQuery(hql);

			ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
			while(results.next()){
				
				//��������
				ContactPerson cp = (ContactPerson)results.get(0);
				System.out.println(cp.getId()+","+cp.getName());
				
				//����������
//				Object[] os = results.get();
//				System.out.println(os[0]+","+os[1]);
				
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
			
			String hql = "select p.id,p.name from ContactPerson p";
			
			Query query = session.createQuery(hql);
			
			ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
			while(results.next()){
				
				//����һ������
				Object cp = (Object)results.get();
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

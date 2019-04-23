package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Hibernate;
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
				cp.setCreateTime(new Date());
				cp.setCreateDate(new Date());
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
			
			String hql = "select p from ContactPerson p where p.name like ? and p.address like ?";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			query.setParameter(0, "%1%");
			query.setParameter(1, "%2%");
			
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
	
	public void testSearch0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like ? and p.address like :addr";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			query.setParameter(0, "%1%");
			//query.setParameter(1, "%2%");
			query.setParameter("addr", "%2%");
			
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
	
	public void testSearch0102(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//���ʹ��ʱ������������Ҫ�������
			String hql = "select p from ContactPerson p where  p.name like ?  and p.age = ?  and p.address like :addr";
			
			Query query = session.createQuery(hql);
			
			//���� - �������� �� 0-base
			query.setParameter(0, "%1%");
			query.setParameter(1, 4); //���ͱ���ƥ��
			//query.setParameter(1, "%2%");
			query.setParameter("addr", "%2%");
			
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
	
	public void testSearch_count(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʹ��<>��!= ��ʾ���������ֹ�ϵ
			String hql = "select count(*) from ContactPerson p " +
					"where p.age <= ? and p.age >= ? and p.age <> 30";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, 45)
				.setParameter(1, 20);
			
			List<Long> persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long count = (Long)iterator.next();
				System.out.println(count);
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
	
	public void testSearch_Max(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʹ��<>��!= ��ʾ���������ֹ�ϵ
			String hql = "select p.name,p.age,max(p.age) from ContactPerson p " +
					"group by p.name,p.age having max(p.age) = p.age";
			
			Query query = session.createQuery(hql);
			
			List<Object[]> persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] count = (Object[])iterator.next();
				System.out.println(count[0]+","+count[1]);
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
			
			String hql = "select p from ContactPerson p where p.name like :pname and p.address like :paddr";
			
			Query query = session.createQuery(hql);
			
			//���� - ������������
			query.setParameter("paddr", "%2%");
			query.setParameter("pname", "%1%");
			
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
	
	public void testSearch03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String pname = "2";
			String paddr = "1";
			
			//HQL����ƴװ��������ַ������ͣ���Ҫ��������
			String hql = "select p from ContactPerson p where p.name like '%"+pname+"%' and p.address like '%"+paddr+"%'";
			
			Query query = session.createQuery(hql);
			
			
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
	
	public void testSearch04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			int id = 50;
			
			//HQL���ƴװ����������Σ������������
			String hql = "select p from ContactPerson p where p.id = "+id;
			
			Query query = session.createQuery(hql);
			
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
	
	public void testSearch05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//in�﷨
			String hql = "select p from ContactPerson p where p.id in (1,2,3,4,5,6,7,8)";
			
			Query query = session.createQuery(hql);
			
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
	
	public void testSearch06(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʹ����������������һ������
			String hql = "select p from ContactPerson p where p.id in (:ids)";
			
			Query query = session.createQuery(hql);
			
			//����һ�����ϣ���Ϊids���������ֵ
			List ids = new ArrayList();
			ids.add(1);
			ids.add(2);
			ids.add(3);
			
			//���ϲ���������null��empty
			query.setParameterList("ids", ids);
			
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
	
	public void testSearch07(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʹ��=null��is null���жϿ�ֵ������ʹ��is null
			String hql = "select p from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
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
	
	public void testSearch08(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			String hql = "select p.id from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Integer cp = (Integer) iterator.next();
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
	
	public void testSearch09(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			String hql = "select p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				String cp = (String) iterator.next();
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
	
	public void testSearch10(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			String hql = "select p.id,p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
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
	
	public void testSearch11(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			//��ѯ�ܼ�¼����selct count(*) from ....
			//�䷵�ؽ����������Long����
			String hql = "select count(*) from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long cp = (Long) iterator.next();
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
	
	public void testSearch12(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			//��HQL����У�����ֱ�Ӱ���*
			String hql = "select * from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long cp = (Long) iterator.next();
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
	
	public void testSearch13(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			//��ѯ�ܼ�¼����selct count(*) from ....
			//�䷵�ؽ����������Long����
			String hql = "select count(*) from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			Long count = (Long)query.uniqueResult();
			System.out.println(count);
			
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
	
	public void testSearch14(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//ͶӰ��ѯ
			//��ѯ�ܼ�¼����selct count(*) from ....
			//�䷵�ؽ����������Long����
			String hql = "select p.name from ContactPerson p where p.id = 1";
			
			Query query = session.createQuery(hql);
						
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch15(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
			
			//�����ѯ�Ľ������Ψһ�ģ����Ƕ���һ�������׳��쳣��
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch16(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p.name from ContactPerson p where p.id = 500";
			
			Query query = session.createQuery(hql);
			
			//�����ѯ�Ľ���ļ����ǿյģ���ô����null
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch17(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			//��ҳ��ѯ
			query.setFirstResult(30);
			query.setMaxResults(15);
			
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
	
	public void testSearch18(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			/**
			 * Query.list()���ǻᷢ��һ����ѯ����ѯ���еĽ��
			 */
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
	
	public void testSearch19(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			/**
			 * Query.iterate()���ȷ���һ����ѯ��䣬��ѯʵ������ID�б�
			 * Ȼ�󣬵�����ѭ���������е�����ʱ��Hibernate�ٸ���ID���η���
			 * ��ѯ��䣬������Ӧ�Ķ���
			 */
			Iterator iterator = query.iterate();
			for (; iterator.hasNext();) {
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
	
	public void testSearch20(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * �����ѯ�Ľ��������ʵ�����Ľ�������������������һ������ô
			 * list()��iterate()����û���κ�����
			 */
			String hql = "select p.id,p.name from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
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
	
	public void testSearch21(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * �����ѯ�Ľ��������ʵ�����Ľ�������������������һ������ô
			 * list()��iterate()����û���κ�����
			 */
			String hql = "select p.id,p.name from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			Iterator iterator = query.iterate();
			for (; iterator.hasNext();) {
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
	
	public void testSearch22(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//������ѯ
			List persons = session.getNamedQuery("searchPersonByName")
				.setParameter(0, "%11%")
				.list();
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
}

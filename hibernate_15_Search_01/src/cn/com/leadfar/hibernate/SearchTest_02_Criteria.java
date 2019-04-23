package cn.com.leadfar.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

public class SearchTest_02_Criteria extends TestCase {
	
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
			
			for(int i=0; i<20; i++){
				Group g = new Group();
				g.setName("��"+i);
				session.save(g);
			}
			
			for(int i=0; i<200; i++){
				ContactPerson cp = new ContactPerson("��Ա"+new Random().nextInt(99999));
				cp.setAddress("����"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setAge(new Random().nextInt(99));
				
				cp.setGroup((Group)session.load(Group.class, random(20)));
				
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
	
	private int random(int max){
		int r = new Random().nextInt(max+1);
		if(r >= 1 && r <= max){
			return r;
		}else{
			return random(max);
		}
	}
	
	public void testSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����һ��������ѯ���󣨱�׼�����ѯ��
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//�����ѯ����
			criteria.add(
				Restrictions.like("name", "%1%")
			);
			
			List persons = criteria.list();
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
			
			ContactPerson cp = new ContactPerson();
			cp.setName("1");
			cp.setQq("2");
			
			String hql = "select p from ContactPerson p where 1 = 1 ";
			if(cp.getAddress() != null){
				hql += " and p.address like '%"+cp.getAddress()+"%'";
			}
			if(cp.getName() != null){
				hql += " and p.name like '%"+cp.getName()+"%'";
			}
			if(cp.getQq() != null){
				hql += " and p.qq like '%"+cp.getQq()+"%'";
			}
			
			List persons = session.createQuery(hql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson p = (ContactPerson) iterator.next();
				System.out.println(p.getId()+","+p.getName()+","+p.getAddress());
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
			
			/**
			 * Ĭ������£�����null���Խ������ԣ�����Ϊ��ѯ����
			 */
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID��������
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
					//.excludeZeroes()
					//.excludeNone()
					.excludeProperty("age")
			);
			criteria.add(
				Restrictions.between("age", 18, 28)
			);
			
			List persons = criteria.list();
			
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
	
	public void testSearch0301(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * ������ʹ�������VO������Ϊ��ѯ����
			 */
			PersonVO c = new PersonVO();
			c.setId(100); //ID��������
			c.setName("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					//.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
					//.excludeZeroes()
					//.excludeNone()
					//.excludeProperty("age")
			);
			criteria.add(
				Restrictions.between("age", 18, 28)
			);
			
			List persons = criteria.list();
			
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
	
	public void testSearch0302(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * Ĭ������£�����null���Խ������ԣ�����Ϊ��ѯ����
			 */
			
			//��������PersonVO����example����
//			PersonVO c = new PersonVO();
//			c.setId(10);
//			c.setName("1");
			
			//������Map����Example����
			Map c = new HashMap();
			c.put("idd", 10);
			c.put("name", "1");
			c.put("qq", null);
			c.put("age", 0);
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					//.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
					.excludeZeroes() //����ȡֵΪ0��������Ϊ��ѯ����
					//.excludeNone()
					//.excludeProperty("age")
			);
			//criteria.add(
			//	Restrictions.between("age", 18, 28)
			//);
			
			List persons = criteria.list();
			
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
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID��������
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
			);
			
			//ͶӰ��ѯ��ֻ��һ�����ԣ�
			criteria.setProjection(
				Projections.property("name")
			);
//			Projections.projectionList()
//				.add(Projections.property("id"))
//				.add(Projections.property("name"))
			
			List persons = criteria.list();
			
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
	
	public void testSearch05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID��������
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
			);
			
			//ͶӰ��ѯ���������ԣ�
			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("name"))
					.add(Projections.property("address"))
					.add(Projections.property("id"))
			);
			
			List persons = criteria.list();
			
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
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID��������
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//����Example�������Զ�������ѯ����
			criteria.add(
				Example.create(c) //����Example����
					.enableLike(MatchMode.ANYWHERE) //����ģ��ƥ���ģʽ
					.excludeProperty("qq") //����qq���������ʲô������������ֵ��Ϊ��ѯ����
			);
			
			//ͶӰ��ѯ�����ܼ�¼����
			criteria.setProjection(
				Projections.rowCount()
			);
			
			Long total = (Long)criteria.uniqueResult();
			
			System.out.println(total);
			
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
			
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			criteria.add(
				Restrictions.like("name", "1",MatchMode.ANYWHERE)
			);
			Property id = Property.forName("id");
			Property name = Property.forName("name");
			criteria.setProjection(
				Projections.projectionList()
					.add(
						Projections.property("id"),"id_" //,"p_id"
						//	id.as("pid")
					)
					.add(
						Projections.property("name"),"name_"
						//	name.as("p_name")
					)
			);
			
			//Map
			//��Ҫ�����������ұ������ܺ�������һ�������������������һ�������乹��������SQL���ᱨ��
			//criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			//Bean
			//��Ҫ�����������ұ������ܺ�������һ�������������������һ�������乹��������SQL���ᱨ��
			//��������������򴴽�������VO����������ֵ
			//criteria.setResultTransformer(new AliasToBeanResultTransformer(PersonVO.class));
			
			//��Ҫ�����������ұ������ܺ�������һ�������������������һ�������乹��������SQL���ᱨ��
			criteria.setResultTransformer(new MyTransformer(PersonVO.class));
			
			List list = criteria.list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			criteria.add(
				Restrictions.like("name", "1",MatchMode.ANYWHERE)
			);

			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id")
					.add(Projections.property("name"),"name")
			);
			
			//Map
			//��Ҫ�����������ұ������ܺ�������һ����
			//����Ҫ��ѯ����һ��POJOʵ����
			criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			List list = criteria.list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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

package cn.com.leadfar.hibernate;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_01_Lazy extends TestCase {
	
	public void testOneToManyLazy01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g1 = (Group)session.get(Group.class, 1);
			
			System.out.println(g1.getName());
			
			Set<ContactPerson> persons = g1.getPersons();
			
			/**
			 * ��Ҫ��ȡ�����а������ٸ�ContactPerson
			 */
			System.out.println(persons.size());
			
			/**
			 * ��Ҫ֪����ĳ��Group�����Ƿ����ĳ��ContactPerson
			 */
			ContactPerson cp1 = new ContactPerson();
			cp1.setId(1);
			boolean b = persons.contains(cp1);
			System.out.println(b);
			
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

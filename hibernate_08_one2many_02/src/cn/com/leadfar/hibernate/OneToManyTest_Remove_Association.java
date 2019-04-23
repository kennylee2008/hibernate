package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_Remove_Association extends TestCase {
	
	public void testOneToManyRemoveAssociation01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g3 = (Group)session.load(Group.class, 1);
			
			ContactPerson cp2 = (ContactPerson)session.load(ContactPerson.class, 2);
			
			g3.getPersons().remove(cp2);
			
			session.delete(g3);
			
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

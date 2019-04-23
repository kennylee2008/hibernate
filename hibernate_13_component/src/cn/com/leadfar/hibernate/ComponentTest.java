package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ComponentTest extends TestCase {
	
	public void testComponentSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("����");
			cp.setQq("232423423");
			cp.setHomeAddress("beijing", "beijing", "fengtai", "guangwai da jie", "834833");
			cp.setWorkAddress("shanghai", "shanghai", "pudong", "xxx", "453454");
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
	
}

package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class CollectionTest extends TestCase {
	
	public void testCollectionSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("����");
			cp.addAddress("��ͥ��ַ", "����", "����", "24234343");
			cp.addAddress("������ַ1", "�Ϻ�", "�Ϻ�", "2423434343");
			cp.addAddress("������ַ2", "�㶫", "����", "2423433443");
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

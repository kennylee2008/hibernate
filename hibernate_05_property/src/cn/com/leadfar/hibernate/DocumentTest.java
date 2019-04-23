package cn.com.leadfar.hibernate;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

public class DocumentTest extends TestCase{

	public void testProperty01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Document d = new Document();
			d.setDvalue(2345678.83764874938374548);
			d.setFvalue(23456.457f);
			session.save(d);

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

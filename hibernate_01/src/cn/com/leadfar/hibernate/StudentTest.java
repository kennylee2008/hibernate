package cn.com.leadfar.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;

public class StudentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Ĭ�϶�ȡ��·����Ŀ¼�µ�hibernate.cfg.xml�����ļ�
		Configuration cfg = new Configuration().configure();
		
		//����SessionFactory
		SessionFactory factory = cfg.buildSessionFactory();
				
		//����Hibenate Session
		Session session = factory.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Student user = new Student();
			user.setName("����");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//�������
			session.save(user);

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

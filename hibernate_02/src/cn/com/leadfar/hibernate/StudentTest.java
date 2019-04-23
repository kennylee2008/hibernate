package cn.com.leadfar.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
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

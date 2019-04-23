package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest02 extends TestCase{

	public void testGet01(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * 1��Get�����������Ϸ���select���
			 * 2��Get�����ķ���ֵ�����;���Student����
			 * 3��Get������ѯ��ʵ�������������ڣ��᷵��null
			 * 4�������ػ��ƶ�get�����������ã�
			 */
			Student s = (Student)session.get(Student.class, 10);
			
			System.out.println(s.getId());
			System.out.println(s.getName());
			System.out.println(s.getAddress());
			
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
	
	public void testLoad01(){
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			/**
			 * 1��Load������hibernate�������Ϸ���select��䣨������,LazyLoad���ƣ�
			 * 2��Load���������ص���һ��Student�Ĵ������
			 * 3��Load������������صĶ��󲻴��ڣ���Ȼ�᷵�ش�����󣬵��Ƿ������е����ԣ���id����ʱ�򣬻��׳�ObjectNotFoundException�쳣��
			 * 4�����ȡ���������ػ��ƣ���class������lazy="false"����load������get������û��������
			 */
			Student s = (Student)session.load(Student.class, 1);
			
			System.out.println(s.getId());
			System.out.println(s.getName());
			System.out.println(s.getAddress());
			
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

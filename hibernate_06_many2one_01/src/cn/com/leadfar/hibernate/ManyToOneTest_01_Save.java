package cn.com.leadfar.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ManyToOneTest_01_Save extends TestCase {
	public void testManyToOne01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			session.save(cp2);//cp2����˳־û�����״̬������ӵ�������ݿ��ʶ
			ContactPerson cp3 = new ContactPerson("·�˼�");
			session.save(cp3);//cp3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			Group g1 = new Group("����");
			session.save(g1);//g1����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g2 = new Group("İ����");
			session.save(g2);//g2����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g3 = new Group("����");
			session.save(g3);//g3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			//����cp1��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp1.setGroup(g1);
			
			//����cp2��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp2.setGroup(g1);
			
			//����cp3��g2����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g2������g2�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp3.setGroup(g2);


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
	
	//ȥ�������update���
	public void testManyToOne02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
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
}

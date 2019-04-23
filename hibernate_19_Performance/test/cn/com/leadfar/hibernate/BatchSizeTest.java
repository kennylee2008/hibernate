package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.CacheMode;
import org.hibernate.Session;

public class BatchSizeTest extends TestCase{
	
	public void testBatchsize01(){
		
		long begin = System.currentTimeMillis();
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//���籾ʵ���������˶������棬����������֮ǰ����Ѷ�������ر�
			//��������CacheMode.IGNORE����Ҫ�����������ڼ���������潻��
			session.setCacheMode(CacheMode.IGNORE);
			
			/**
			 * ����ʵ�����ID���ɲ�����native��save��ʱ��batch_size���ò���������
			 */
			for(int i=0; i<100000; i++){
				ContactPerson cp = new ContactPerson("������Ա");
				session.save(cp);
				
				/**
				 * ������������������batch_size����һ�£�
				 */
				if(i % 25 == 0){
					session.flush();
					session.clear();
				}
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
		
		long end = System.currentTimeMillis();
		
		/**
		 * mysql��JDBC����URL�������������ò�����������ʾ��
		 * jdbc:mysql://localhost/hibernate?rewriteBatchedStatements=true
		 * batch_size=1,�ܺ�ʱ����Լ27��
		 * batch_size=25,�ܺ�ʱ����Լ12��
		 */
		System.out.println("�ܺ�ʱ��"+(end - begin)+"ms");
	}	
}

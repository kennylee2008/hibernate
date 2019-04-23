package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ManyToManyTest_Save extends TestCase {
	
	public void testManyToManySave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Employee emp1 = new Employee("����");
			session.save(emp1);
			
			Employee emp2 = new Employee("����");
			session.save(emp2);
			
			Employee emp3 = new Employee("����");
			session.save(emp3);
			
			Role role1 = new Role("ϵͳ����Ա");
			session.save(role1);
			
			Role role2 = new Role("��������Ա");
			session.save(role2);
			
			Role role3 = new Role("��Ŀ����");
			session.save(role3);
			
			emp1.addRole(role1);
			emp1.addRole(role2);
			
			emp2.addRole(role2);
			emp2.addRole(role3);
			
			emp3.addRole(role2);
			
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

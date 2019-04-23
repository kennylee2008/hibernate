package cn.com.leadfar.hibernate;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_01_Lazy extends TestCase {
	
	public void testOneToManyLazy01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g1 = (Group)session.get(Group.class, 1);
			
			System.out.println(g1.getName());
			
			Set<ContactPerson> persons = g1.getPersons();
			
			/**
			 * 想要获取集合中包含多少个ContactPerson
			 */
			System.out.println(persons.size());
			
			/**
			 * 想要知道在某个Group下面是否包含某个ContactPerson
			 */
			ContactPerson cp1 = new ContactPerson();
			cp1.setId(1);
			boolean b = persons.contains(cp1);
			System.out.println(b);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); 
		}
	}
	
}

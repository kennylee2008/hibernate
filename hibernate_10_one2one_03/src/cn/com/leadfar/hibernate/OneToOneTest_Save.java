package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_Save extends TestCase {
	
	public void testOneToOneSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp1 = new ContactPerson("张三");
			cp1.setId(10);
			session.save(cp1);
			
			ContactPerson cp2 = new ContactPerson("李四");
			cp2.setId(20);
			session.save(cp2);
			
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
	
	public void testOneToOneSave02(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 10);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("123456789012345999");
			idcard.setId(20);
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneSave03(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 20);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("888888888888888888");
			idcard.setId(10);
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneLoad01(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 20);
			System.out.println(cp.getName()+",的身份证号码是："+cp.getIdCard().getSn());
			
			
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

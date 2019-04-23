package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_BestPractices extends TestCase {
	
	public void testOneToOneSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = new ContactPerson("张三");
			session.save(cp);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("123456789012345678");
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
	
	public void testOneToOneSave0101(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			//session.save(cp);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("111111111111111111");
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
	
	public void testOneToOneSave02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = new ContactPerson("李四");
			session.save(cp);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("873483274974837483");
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
			
			//用一个左连接，查询到
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			IdCard ic = cp.getIdCard();
			
			System.out.println(ic.getSn());
			
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
	
	public void testOneToOneLoad02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//IdCard中有一个many-to-one
			//所以，在加载IdCard的时候，可以知道IdCard有没有对应的ContactPerson
			//因此下面只发一条查询语句
			IdCard ic = (IdCard)session.load(IdCard.class,1);
			System.out.println(ic.getSn());
			
			ContactPerson cp = ic.getPerson();
			
			//下面将发出查询语句查询ContactPerson对象
			System.out.println(cp.getName());
			
			System.out.println((cp.getIdCard() == ic)+","+(cp == ic.getPerson()));
			
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

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
	
	public void testOneToOneSave02(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//然后保存IdCard
			IdCard idcard = new IdCard("123456789012345999");
			//idcard.setPerson(cp);
			session.save(idcard);
			
			//先保存ContactPerson
			ContactPerson cp = new ContactPerson("李四");
			cp.setIdCard(idcard); //不可以保存！！！
			session.save(cp);
			
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
			
			//只发一条语句
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
			
			//发出两条语句
			IdCard ic = (IdCard)session.load(IdCard.class,1);
			System.out.println(ic.getSn());
			
			ContactPerson cp = ic.getPerson();
			
			System.out.println(cp.getName());
			
			//System.out.println(cp.getIdCard() == ic);
			
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

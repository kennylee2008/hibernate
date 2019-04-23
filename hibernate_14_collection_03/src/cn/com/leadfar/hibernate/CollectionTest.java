package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class CollectionTest extends TestCase {
	
	public void testCollectionSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson("张三");
			cp.addAddress("家庭地址", "北京", "北京", "24234343");
			cp.addAddress("工作地址1", "上海", "上海", "2423434343");
			cp.addAddress("工作地址2", "广东", "广州", "2423433443");
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
	
}

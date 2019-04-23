package cn.com.leadfar.hibernate;

import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest extends TestCase{
	
	
	public void testOne2Many01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g1 = new Group("朋友");
			session.save(g1);
			Group g2 = new Group("商务");
			session.save(g2);
			Group g3 = new Group("陌生人");
			session.save(g3);			
			
			Group g4 = new Group("家人");
			session.save(g4);
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			cp1.setGroup(g1); //从“多”一端管理两者之间的关联
			session.save(cp1);
			ContactPerson cp2 = new ContactPerson("巴菲特");
			cp2.setGroup(g1); //从“多”一端管理两者之间的关联
			session.save(cp2);
			ContactPerson cp3 = new ContactPerson("路人甲");
			cp3.setGroup(g3); //从“多”一端管理两者之间的关联
			session.save(cp3);
			
			ContactPerson cp4 = new ContactPerson("路人乙");
			session.save(cp4);
			
			
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
	
	public void testOne2Many02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			for(int i=0; i<50; i++){
				
				Group g = new Group("组"+i);
				session.save(g);
				
				ContactPerson cp = new ContactPerson("测试人员"+i);
				cp.setGroup(g);
				session.save(cp);
				
			}
			
			
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

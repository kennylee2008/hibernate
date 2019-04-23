package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public class CacheTest_01_Level2 extends TestCase {
	
	public void testSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//******************* 从多的一端管理它们之间的关联关系 ***********************//
			Group g1 = new Group("朋友");
			session.save(g1);//g1变成了持久化对象状态，现在拥有了数据库标识
			Group g2 = new Group("陌生人");
			session.save(g2);//g2变成了持久化对象状态，现在拥有了数据库标识
			Group g3 = new Group("商务");
			session.save(g3);//g3变成了持久化对象状态，现在拥有了数据库标识
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			//建立cp1与g1对象之间的关联
			//所以，hibernate能够访问到g1，并把g1的数据库标识
			//根据映射文件的指示，存储到groupId字段
			cp1.setGroup(g1);
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			
			ContactPerson cp2 = new ContactPerson("巴菲特");
			//建立cp2与g1对象之间的关联
			//所以，hibernate能够访问到g1，并把g1的数据库标识
			//根据映射文件的指示，存储到groupId字段
			cp2.setGroup(g1);
			session.save(cp2);//cp2变成了持久化对象状态，现在拥有了数据库标识
			
			ContactPerson cp3 = new ContactPerson("路人甲");
			//建立cp3与g2对象之间的关联
			//所以，hibernate能够访问到g2，并把g2的数据库标识
			//根据映射文件的指示，存储到groupId字段
			cp3.setGroup(g2);			
			session.save(cp3);//cp3变成了持久化对象状态，现在拥有了数据库标识
			
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
	
	public void testSave02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			for(int i=0; i<200; i++){
				ContactPerson cp = new ContactPerson("人员"+new Random().nextInt(99999));
				cp.setAddress("北京"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //正常状态
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
	
	public void testSave03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g = new Group();
			g.setName("某某组");
			session.save(g);
			
			for(int i=0; i<100; i++){
				ContactPerson cp = new ContactPerson("人员"+new Random().nextInt(99999));
				cp.setAddress("北京"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //正常状态
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
	
	public void testSearch01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//既可从二级缓存读，也可往二级缓存写
			//session.setCacheMode(CacheMode.NORMAL);
			
			//只从二级缓存读，但不会往二级缓存写
			//session.setCacheMode(CacheMode.GET);
			
			//只往二级缓存写，但不从二级缓存读
			//session.setCacheMode(CacheMode.PUT);
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			
			//通过SessionFactory对二级缓存进行管理
			//session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
		
		//第三个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//通过SessionFactory对二级缓存进行管理
			//session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
	
	public void testSearch0101(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//既可从二级缓存读，也可往二级缓存写
			//session.setCacheMode(CacheMode.NORMAL);
			
			//只从二级缓存读，但不会往二级缓存写
			//session.setCacheMode(CacheMode.GET);
			
			//只往二级缓存写，但不从二级缓存读
			session.setCacheMode(CacheMode.PUT);
			
			//本查询可以把对象放入二级缓存，因此，在第二个session中进行get/load等
			//将不再发出查询语句
			session.createQuery("from ContactPerson").list();
			
//			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
//			System.out.println(cp2.getName());
//			
//			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
//			System.out.println(cp3.getName());
			
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//通过SessionFactory对二级缓存进行管理
			session.getSessionFactory().getCache().evictEntityRegion(ContactPerson.class);
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//因为启用了二级缓存，所以不再发出SQL查询语句
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//read-only缓存，可以添加
			ContactPerson cp = new ContactPerson("xxxxx");
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//read-only缓存，不可以更新
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			cp.setName("用户8888888");
			
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
	
	public void testSearch04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//IGNORE，也不可以做更新
			session.setCacheMode(CacheMode.IGNORE);
			
			//read-only缓存，不可以更新
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			cp.setName("用户8888888");
			
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
	
	public void testSearch05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//IGNORE，也不可以做更新
			session.setCacheMode(CacheMode.IGNORE);
			
			//read-only缓存，不可以删除！
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 101);
			session.delete(cp);
			
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

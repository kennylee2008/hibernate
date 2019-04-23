package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class SearchTest_03_SQLQuery extends TestCase {
	
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
			
			String sql = "select * from t_person";
			
			List persons = session.createSQLQuery(sql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]);
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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String sql = "select id,name from t_person";
			
			List persons = session.createSQLQuery(sql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]);
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
	
	public void testSearch0203(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//不可以这样用！！！
			String sql = "select new map(id as id,name as name) from t_person";
			
			List persons = session.createSQLQuery(sql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]);
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
	
	public void testSearch0202(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//查询单个属性
			String sql = "select name from t_person";
			
			List<String> persons = session.createSQLQuery(sql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				String cp = (String) iterator.next();
				System.out.println(cp);
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
	
	public void testSearch0201(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String sql = "select id,name,address,qq,age,createTime,createDate,gid from t_person";
			
			//不可以转换为VO对象
//			List persons = session.createSQLQuery(sql)
//				.addEntity(PersonVO.class)
//				.list();
			
			//如果要转换为实体对象，需要用select * from ...
			//或select [所有字段] from ...
			List persons = session.createSQLQuery(sql)
			.addEntity(ContactPerson.class)
			.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object cp = (Object) iterator.next();
				System.out.println(cp);
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String sql = "select * from t_person";
			
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			
			sqlQuery.addEntity(ContactPerson.class); //把结果转换为ContactPerson对象
			
			List persons = sqlQuery.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
	
	public void testSearch04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String sql = "delete from t_animal";
			
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			
			sqlQuery.executeUpdate();
			
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
			
			String sql = "insert into t_person (name) values (?)";
			
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter(0, "xxx");
			
			sqlQuery.executeUpdate();
			
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

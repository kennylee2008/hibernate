package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

public class SearchTest_01_Query extends TestCase {
	
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
			
			//隐式内连接
			String hql = "select p.id,p.name,p.group.name from ContactPerson p";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
			
			//隐式内连接
			String hql = "select p.id,p.name,p.group.name from ContactPerson p where p.group.name = ?";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, "朋友");
			
			List persons = query.list();
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//显式内连接
			String hql = "select p.id,p.name,g.name from ContactPerson p join p.group g where g.name = ?";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, "朋友");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
			
			//利用左连接，查询出所有的联系人及其所属的组
			String hql = "select p.id,p.name,g.name from ContactPerson p left join p.group g";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//利用右连接，查询出所有的联系人及其所属的组
			String hql = "select p.id,p.name,g.name from Group g right join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch06(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//查询所有组，及其组员的数量
			String hql = "select p.id,p.name,g.name from Group g right join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch07(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//查询所有组，及其组员的数量
			String hql = "select g.id,g.name,p.name from Group g left join g.persons p";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testSearch08(){
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//查询所有组，及其组员的数量
			String hql = "select new map(g.id as groupId,g.name as groupName,count(p) as personSize) from Group g left join g.persons p group by g.id,g.name";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
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
}

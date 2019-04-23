package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public class CacheTest_01_QueryCache extends TestCase {
	
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
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p");
			
			//本查询使用查询缓存
			query.setCacheable(true);

			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%13%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
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
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Query query = session.createQuery("select p from ContactPerson p");
			
			//query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			//List persons = query.list(); //N个查询语句
			Iterator iterator = query.iterate(); //N + 1个查询语句
			for (; iterator.hasNext();) {
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
	
	public void testSearch05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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
		
		//第二个session
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//清除查询缓存！
			session.getSessionFactory().getCache()
				.evictDefaultQueryRegion();
			
			Query query = session.createQuery("select p.name,p.address from ContactPerson p where p.name like ?");
			
			query.setParameter(0, "%12%");
			
			//本查询使用查询缓存
			query.setCacheable(true);
			
			//启用了查询缓存之后，list()操作也会利用缓存!
			List persons = query.list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] datas = (Object[]) iterator.next();
				System.out.println(datas[0]+","+datas[1]);
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

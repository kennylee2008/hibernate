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
				cp.setAge(new Random().nextInt(99));
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
			
			String hql = "select p from ContactPerson p where p.name like ? and p.deleted = 0";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			
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
	
	public void testSearch0101(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();

			session.enableFilter("ageFilter1").setParameter("maxAge", 28);
			
			String hql = "select p from ContactPerson p where p.name like ?";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");

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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String countHql = "select count(*) from ContactPerson p where p.name like ? and p.deleted = 0";
			Query countQuery = session.createQuery(countHql);
			countQuery.setParameter(0, "%1%");
			long total = (Long)countQuery.uniqueResult();
			System.out.println("总计录数："+total);
			
			String hql = "select p from ContactPerson p where p.name like ? and p.deleted = 0";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			query.setFirstResult(20);
			query.setMaxResults(15);
			
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
	
	public void testSearch0201(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
				.setParameter("deletedCondition", 0); //查询正常的ContactPerson对象
			
			String countHql = "select count(*) from ContactPerson p where p.name like ?";
			Query countQuery = session.createQuery(countHql);
			countQuery.setParameter(0, "%1%");
			long total = (Long)countQuery.uniqueResult();
			System.out.println("总计录数："+total);
			
			String hql = "select p from ContactPerson p where p.name like ?";
			
			Query query = session.createQuery(hql);
			query.setParameter(0, "%1%");
			query.setFirstResult(20);
			query.setMaxResults(15);
			
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//查询条件
			ContactPerson c = new ContactPerson();
			c.setName("1");
			c.setAddress("2");
			c.setDeleted(0);
			
			//查询总计录数
			Criteria criteria = session.createCriteria(ContactPerson.class);
			criteria.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			criteria.setProjection(
				Projections.rowCount()
			);
			
			long total = (Long)criteria.uniqueResult();
			System.out.println("总计录数："+total);
			
			//查询当前页的数据
			Criteria current = session.createCriteria(ContactPerson.class);
			current.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			List persons = current.list();
			
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
	
	public void testSearch0301(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
			.setParameter("deletedCondition", 0); //查询正常的ContactPerson对象

			//查询条件
			ContactPerson c = new ContactPerson();
			c.setName("1");
			c.setAddress("2");
			
			//查询总计录数
			Criteria criteria = session.createCriteria(ContactPerson.class);
			criteria.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			criteria.setProjection(
				Projections.rowCount()
			);
			
			long total = (Long)criteria.uniqueResult();
			System.out.println("总计录数："+total);
			
			//查询当前页的数据
			Criteria current = session.createCriteria(ContactPerson.class);
			current.add(
				Example.create(c)
					.enableLike(MatchMode.ANYWHERE)
			);
			List persons = current.list();
			
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
			
			//现在有一个Group对象，希望得到Group对象下面的ContactPerson对象列表
			Group g = (Group)session.load(Group.class, 1);
			
			System.out.println("组【"+g.getName()+"】下面有："+g.getPersons().size()+"人");
			
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
	
	public void testSearch0401(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			session.enableFilter("deletedFilter")
				.setParameter("deletedCondition", 0); //查询正常的ContactPerson对象
			
			//现在有一个Group对象，希望得到Group对象下面的ContactPerson对象列表
			Group g = (Group)session.load(Group.class, 1);
			
			System.out.println("组【"+g.getName()+"】下面有："+g.getPersons().size()+"人");
			Set<ContactPerson> persons = g.getPersons();
			for(ContactPerson cp:persons){
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
	
	public void testSearch0402(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//现在有一个Group对象，希望得到Group对象下面的ContactPerson对象列表
			Group g = (Group)session.load(Group.class, 1);
			
			//对集合进行过滤
			List<ContactPerson> persons = session.createFilter(g.getPersons(), "where name like ?")
				.setParameter(0, "%巴%")
				.list();
			
			//System.out.println("组【"+g.getName()+"】下面有："+g.getPersons().size()+"人");
			//Set<ContactPerson> persons = g.getPersons();
			for(ContactPerson cp:persons){
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
	
	public void testSearch0403(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//此过滤条件也被自动附加
			session.enableFilter("deletedFilter")
			.setParameter("deletedCondition", 0); 
			
			//现在有一个Group对象，希望得到Group对象下面的ContactPerson对象列表
			Group g = (Group)session.load(Group.class, 1);
			
			//对集合进行过滤
			List<String> persons = session.createFilter(g.getPersons(), "select name where id > 201")
				.list();
			
			for(String cp:persons){
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
	
	public void testSearch0405(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			
			//现在有一个Group对象，希望得到Group对象下面的ContactPerson对象列表
			Group g = (Group)session.load(Group.class, 1);
			
			//对集合进行过滤
			//select name
			//select name order by id
			//select name where id > 200 order by id
			List<String> persons = session.createFilter(g.getPersons(), "select name  ")
				.list();
			
			for(String cp:persons){
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
	
	public void testDeletePerson(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//假删除
			for(int i=30; i<80; i++){
				ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, i);
				cp.setDeleted(-1); //假删除
				session.update(cp);
			}
			
			for(int i= 230; i<260; i++){
				ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, i);
				cp.setDeleted(-1); //假删除
				session.update(cp);
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

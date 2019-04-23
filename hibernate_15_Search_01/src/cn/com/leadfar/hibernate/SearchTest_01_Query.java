package cn.com.leadfar.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Hibernate;
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
				cp.setCreateTime(new Date());
				cp.setCreateDate(new Date());
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
			
			String hql = "select p from ContactPerson p where p.name like ? and p.address like ?";
			
			Query query = session.createQuery(hql);
			
			//传参 - 基于索引 ： 0-base
			query.setParameter(0, "%1%");
			query.setParameter(1, "%2%");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			String hql = "select p from ContactPerson p where p.name like ? and p.address like :addr";
			
			Query query = session.createQuery(hql);
			
			//传参 - 基于索引 ： 0-base
			query.setParameter(0, "%1%");
			//query.setParameter(1, "%2%");
			query.setParameter("addr", "%2%");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
	
	public void testSearch0102(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//混合使用时，命名参数需要放在最后
			String hql = "select p from ContactPerson p where  p.name like ?  and p.age = ?  and p.address like :addr";
			
			Query query = session.createQuery(hql);
			
			//传参 - 基于索引 ： 0-base
			query.setParameter(0, "%1%");
			query.setParameter(1, 4); //类型必需匹配
			//query.setParameter(1, "%2%");
			query.setParameter("addr", "%2%");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
	
	public void testSearch_count(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//可以使用<>或!= 表示不等于这种关系
			String hql = "select count(*) from ContactPerson p " +
					"where p.age <= ? and p.age >= ? and p.age <> 30";
			
			Query query = session.createQuery(hql);
			
			query.setParameter(0, 45)
				.setParameter(1, 20);
			
			List<Long> persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long count = (Long)iterator.next();
				System.out.println(count);
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
	
	public void testSearch_Max(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//可以使用<>或!= 表示不等于这种关系
			String hql = "select p.name,p.age,max(p.age) from ContactPerson p " +
					"group by p.name,p.age having max(p.age) = p.age";
			
			Query query = session.createQuery(hql);
			
			List<Object[]> persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Object[] count = (Object[])iterator.next();
				System.out.println(count[0]+","+count[1]);
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
			
			String hql = "select p from ContactPerson p where p.name like :pname and p.address like :paddr";
			
			Query query = session.createQuery(hql);
			
			//传参 - 基于命名参数
			query.setParameter("paddr", "%2%");
			query.setParameter("pname", "%1%");
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			String pname = "2";
			String paddr = "1";
			
			//HQL语句的拼装，如果是字符串类型，需要加上引号
			String hql = "select p from ContactPerson p where p.name like '%"+pname+"%' and p.address like '%"+paddr+"%'";
			
			Query query = session.createQuery(hql);
			
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			int id = 50;
			
			//HQL语句拼装，如果是整形，无需加上引号
			String hql = "select p from ContactPerson p where p.id = "+id;
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			//in语法
			String hql = "select p from ContactPerson p where p.id in (1,2,3,4,5,6,7,8)";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			//可以使用命名参数来传递一个集合
			String hql = "select p from ContactPerson p where p.id in (:ids)";
			
			Query query = session.createQuery(hql);
			
			//传递一个集合，作为ids这个参数的值
			List ids = new ArrayList();
			ids.add(1);
			ids.add(2);
			ids.add(3);
			
			//集合参数不能是null或empty
			query.setParameterList("ids", ids);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			//可以使用=null或is null来判断空值，建议使用is null
			String hql = "select p from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
			
			//投影查询
			String hql = "select p.id from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Integer cp = (Integer) iterator.next();
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
	
	public void testSearch09(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			String hql = "select p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
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
	
	public void testSearch10(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			String hql = "select p.id,p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
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
	
	public void testSearch11(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			//查询总记录数，selct count(*) from ....
			//其返回结果的类型是Long类型
			String hql = "select count(*) from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long cp = (Long) iterator.next();
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
	
	public void testSearch12(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			//在HQL语句中，不能直接包含*
			String hql = "select * from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				Long cp = (Long) iterator.next();
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
	
	public void testSearch13(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			//查询总记录数，selct count(*) from ....
			//其返回结果的类型是Long类型
			String hql = "select count(*) from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
						
			Long count = (Long)query.uniqueResult();
			System.out.println(count);
			
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
	
	public void testSearch14(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//投影查询
			//查询总记录数，selct count(*) from ....
			//其返回结果的类型是Long类型
			String hql = "select p.name from ContactPerson p where p.id = 1";
			
			Query query = session.createQuery(hql);
						
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch15(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "select p.name from ContactPerson p where p.qq is null";
			
			Query query = session.createQuery(hql);
			
			//如果查询的结果不是唯一的，而是多于一条，则抛出异常！
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch16(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "select p.name from ContactPerson p where p.id = 500";
			
			Query query = session.createQuery(hql);
			
			//如果查询的结果的集合是空的，那么返回null
			String name = (String)query.uniqueResult();
			System.out.println(name);
			
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
	
	public void testSearch17(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			//分页查询
			query.setFirstResult(30);
			query.setMaxResults(15);
			
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
	
	public void testSearch18(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			/**
			 * Query.list()总是会发出一条查询语句查询所有的结果
			 */
			List persons = query.list();
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
	
	public void testSearch19(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			String hql = "select p from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			/**
			 * Query.iterate()会先发出一条查询语句，查询实体对象的ID列表
			 * 然后，当我们循环访问其中的数据时，Hibernate再根据ID依次发出
			 * 查询语句，加载相应的对象！
			 */
			Iterator iterator = query.iterate();
			for (; iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress());
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
	
	public void testSearch20(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 如果查询的结果集不是实体对象的结果集，像下面这个例子一样，那么
			 * list()和iterate()方法没有任何区别
			 */
			String hql = "select p.id,p.name from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
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
	
	public void testSearch21(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 如果查询的结果集不是实体对象的结果集，像下面这个例子一样，那么
			 * list()和iterate()方法没有任何区别
			 */
			String hql = "select p.id,p.name from ContactPerson p where p.name like '%1%'";
			
			Query query = session.createQuery(hql);
			
			Iterator iterator = query.iterate();
			for (; iterator.hasNext();) {
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
	
	public void testSearch22(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//命名查询
			List persons = session.getNamedQuery("searchPersonByName")
				.setParameter(0, "%11%")
				.list();
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
}

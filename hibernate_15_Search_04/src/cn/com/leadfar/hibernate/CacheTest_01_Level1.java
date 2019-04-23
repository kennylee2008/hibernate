package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public class CacheTest_01_Level1 extends TestCase {
	
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
			
			//session级别的缓存，缓存的是实体对象
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
			
			//session级别的缓存，缓存的是实体对象
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			session.refresh(cp);
			session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			
			//
			ContactPerson cp = new ContactPerson();
			cp.setId(4);
			
			//以下操作可以执行，将导致内存中的cp对象被填充上各种属性值！
			session.refresh(cp);
			session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 4);
			System.out.println(cp2.getName());
			session.refresh(cp2);
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh_02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			session.get(ContactPerson.class, 1);
			
			//
			ContactPerson cp = new ContactPerson();
			cp.setId(1);
			
			//以下操作，因为id=1的对象已经在session中，下面的操作将
			//不再把属性填充到cp对象中（但会发出查询语句！）
			session.refresh(cp);
			//session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh_03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp0 = (ContactPerson)session.get(ContactPerson.class, 4);
			cp0.setName("XXX"+new Random().nextInt(9999));
			
			session.evict(cp0);
			
			session.refresh(cp0);
			
			cp0.setName("XXX"+new Random().nextInt(9999));
			
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
			
			Group g = new Group();
			g.setId(1);
			g.setName("pengyou21");
			session.update(g);
			session.refresh(g);
			Set persons = g.getPersons();
			if(persons != null)
			System.out.println(persons.size());
			else
				System.out.println("no persons");
			
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
			
			//session级别的缓存，缓存的是实体对象
			List persons = session.createQuery("from ContactPerson").list();
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			
			ContactPerson cp3 = (ContactPerson)session.load(ContactPerson.class, 10);
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//session级别的缓存，缓存的是实体对象
			List persons = session.createQuery("from ContactPerson").list();
			
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
			
			//再次发出SQL查询语句
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//再次发出SQL查询语句
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
	
	public void testSearch04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//第一次用list()，会发出SQL语句
			List persons = session.createQuery("from ContactPerson").list();
			
			//第二次用list()，还会发出SQL语句
			List persons2 = session.createQuery("from ContactPerson").list();
			
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
			
			//第一次用iterate()，会发出SQL语句
			Iterator persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
			}
			
			//第二次用iterate()，不再发出SQL语句
			persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
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
			
			//第一次用list()，会发出SQL语句
			List personsList = session.createQuery("from ContactPerson").list();

			
			//第二次用iterate()，不再发出SQL语句
			Iterator persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
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
			
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress()+","+cp.getQq());
			
			ContactPerson cp2 = new ContactPerson();
			cp2.setId(1);
			cp2.setName("xxxx");
			cp2.setAddress("kkdjf");
			cp2.setQq("93849384");
			cp2.setGroup(cp.getGroup());
			
			//不可以直接调用update
			//session.update(cp2);
			
			//可以使用merge方法来更新
			//session.merge(cp2);
			
			//也可以先把持久化对象从一级缓存中清除，然后在调用update方法！
			session.evict(cp);
			session.update(cp2);
			
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
			
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress()+","+cp.getQq());
			
			cp.setName("ffffffffffffffffff");
			
			//clear()方法，清空一级缓存，所以cp对象不再被session所管理
			//因此，在事务提交的时候，这种状态的变化，将无法自动同步到数据库!
			session.clear();
			
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
			
			//大批量插入数据
			for(int i=0; i<1000000000;i ++){
				ContactPerson cp = new ContactPerson();
				cp.setName("xxxx"+i);
				session.save(cp);
				if(i % 200 == 0){
					session.flush();
					session.clear();
				}
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

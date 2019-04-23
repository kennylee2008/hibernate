package cn.com.leadfar.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

public class SearchTest_02_Criteria extends TestCase {
	
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
			
			for(int i=0; i<20; i++){
				Group g = new Group();
				g.setName("组"+i);
				session.save(g);
			}
			
			for(int i=0; i<200; i++){
				ContactPerson cp = new ContactPerson("人员"+new Random().nextInt(99999));
				cp.setAddress("北京"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setAge(new Random().nextInt(99));
				
				cp.setGroup((Group)session.load(Group.class, random(20)));
				
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
	
	private int random(int max){
		int r = new Random().nextInt(max+1);
		if(r >= 1 && r <= max){
			return r;
		}else{
			return random(max);
		}
	}
	
	public void testSearch01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建一个条件查询对象（标准对象查询）
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//构造查询条件
			criteria.add(
				Restrictions.like("name", "%1%")
			);
			
			List persons = criteria.list();
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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp = new ContactPerson();
			cp.setName("1");
			cp.setQq("2");
			
			String hql = "select p from ContactPerson p where 1 = 1 ";
			if(cp.getAddress() != null){
				hql += " and p.address like '%"+cp.getAddress()+"%'";
			}
			if(cp.getName() != null){
				hql += " and p.name like '%"+cp.getName()+"%'";
			}
			if(cp.getQq() != null){
				hql += " and p.qq like '%"+cp.getQq()+"%'";
			}
			
			List persons = session.createQuery(hql).list();
			
			for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
				ContactPerson p = (ContactPerson) iterator.next();
				System.out.println(p.getId()+","+p.getName()+","+p.getAddress());
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
			
			/**
			 * 默认情况下，所有null属性将被忽略，不作为查询条件
			 */
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID将被忽略
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
					//.excludeZeroes()
					//.excludeNone()
					.excludeProperty("age")
			);
			criteria.add(
				Restrictions.between("age", 18, 28)
			);
			
			List persons = criteria.list();
			
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
	
	public void testSearch0301(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 不可以使用随意的VO对象作为查询条件
			 */
			PersonVO c = new PersonVO();
			c.setId(100); //ID将被忽略
			c.setName("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					//.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
					//.excludeZeroes()
					//.excludeNone()
					//.excludeProperty("age")
			);
			criteria.add(
				Restrictions.between("age", 18, 28)
			);
			
			List persons = criteria.list();
			
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
	
	public void testSearch0302(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			/**
			 * 默认情况下，所有null属性将被忽略，不作为查询条件
			 */
			
			//不可以用PersonVO创建example对象
//			PersonVO c = new PersonVO();
//			c.setId(10);
//			c.setName("1");
			
			//可以用Map创建Example对象
			Map c = new HashMap();
			c.put("idd", 10);
			c.put("name", "1");
			c.put("qq", null);
			c.put("age", 0);
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					//.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
					.excludeZeroes() //不把取值为0的属性作为查询条件
					//.excludeNone()
					//.excludeProperty("age")
			);
			//criteria.add(
			//	Restrictions.between("age", 18, 28)
			//);
			
			List persons = criteria.list();
			
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
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID将被忽略
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
			);
			
			//投影查询（只查一个属性）
			criteria.setProjection(
				Projections.property("name")
			);
//			Projections.projectionList()
//				.add(Projections.property("id"))
//				.add(Projections.property("name"))
			
			List persons = criteria.list();
			
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
	
	public void testSearch05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID将被忽略
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
			);
			
			//投影查询（查多个属性）
			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("name"))
					.add(Projections.property("address"))
					.add(Projections.property("id"))
			);
			
			List persons = criteria.list();
			
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
			
			ContactPerson c = new ContactPerson();
			c.setId(100); //ID将被忽略
			c.setName("1");
			c.setQq("2");
			c.setAddress("1");
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			//基于Example对象来自动构建查询条件
			criteria.add(
				Example.create(c) //创建Example对象
					.enableLike(MatchMode.ANYWHERE) //设置模糊匹配的模式
					.excludeProperty("qq") //不管qq这个属性是什么，都不把它的值作为查询条件
			);
			
			//投影查询（查总记录数）
			criteria.setProjection(
				Projections.rowCount()
			);
			
			Long total = (Long)criteria.uniqueResult();
			
			System.out.println(total);
			
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
			
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			criteria.add(
				Restrictions.like("name", "1",MatchMode.ANYWHERE)
			);
			Property id = Property.forName("id");
			Property name = Property.forName("name");
			criteria.setProjection(
				Projections.projectionList()
					.add(
						Projections.property("id"),"id_" //,"p_id"
						//	id.as("pid")
					)
					.add(
						Projections.property("name"),"name_"
						//	name.as("p_name")
					)
			);
			
			//Map
			//需要命别名，而且别名不能和属性名一样！如果别名和属性名一样，则其构建出来的SQL语句会报错
			//criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			//Bean
			//需要命别名，而且别名不能和属性名一样！如果别名和属性名一样，则其构建出来的SQL语句会报错
			//如果不命别名，则创建出来的VO对象无属性值
			//criteria.setResultTransformer(new AliasToBeanResultTransformer(PersonVO.class));
			
			//需要命别名，而且别名不能和属性名一样！如果别名和属性名一样，则其构建出来的SQL语句会报错
			criteria.setResultTransformer(new MyTransformer(PersonVO.class));
			
			List list = criteria.list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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
			
			Criteria criteria = session.createCriteria(ContactPerson.class);
			
			criteria.add(
				Restrictions.like("name", "1",MatchMode.ANYWHERE)
			);

			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id")
					.add(Projections.property("name"),"name")
			);
			
			//Map
			//需要命别名，而且别名不能和属性名一样！
			//我们要查询的是一个POJO实体类
			criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			List list = criteria.list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
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

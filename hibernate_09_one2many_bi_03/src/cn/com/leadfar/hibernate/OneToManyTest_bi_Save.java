package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToManyTest_bi_Save extends TestCase {
	
	public void testOneToManyBiSave01(){
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
	
	public void testOneToManyBiSave02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//从一的一端去管理它们之间的关联关系
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp2 = new ContactPerson("巴菲特");
			session.save(cp2);//cp2变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp3 = new ContactPerson("路人甲");
			session.save(cp3);//cp3变成了持久化对象状态，现在拥有了数据库标识
			
			Group g1 = new Group("朋友");
			g1.addPerson(cp1);
			g1.addPerson(cp2);
			session.save(g1);//g1变成了持久化对象状态，现在拥有了数据库标识
			
			Group g2 = new Group("陌生人");
			session.save(g2);//g2变成了持久化对象状态，现在拥有了数据库标识
			
			Group g3 = new Group("商务");
			g3.addPerson(cp3);
			session.save(g3);//g3变成了持久化对象状态，现在拥有了数据库标识			
			
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
	
	public void testOneToManyBiSave03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			
			ContactPerson cp1 = new ContactPerson("比尔盖茨");
			session.save(cp1);//cp1变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp2 = new ContactPerson("巴菲特");
			session.save(cp2);//cp2变成了持久化对象状态，现在拥有了数据库标识
			ContactPerson cp3 = new ContactPerson("路人甲");
			session.save(cp3);//cp3变成了持久化对象状态，现在拥有了数据库标识
			
			Group g1 = new Group("朋友");
			g1.addPerson(cp1); //如果设置了inverse="true"，本关联无效！
			g1.addPerson(cp2);//如果设置了inverse="true"，本关联无效！
			session.save(g1);//g1变成了持久化对象状态，现在拥有了数据库标识
			
			cp1.setGroup(g1);
			cp2.setGroup(g1);
			
			Group g2 = new Group("陌生人");
			session.save(g2);//g2变成了持久化对象状态，现在拥有了数据库标识
			
			Group g3 = new Group("商务");
			g3.addPerson(cp3); //如果设置了inverse="true"，本关联无效！
			session.save(g3);//g3变成了持久化对象状态，现在拥有了数据库标识			
			
			cp3.setGroup(g3);
			
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
	
	public void testOneToManyBiUpdate01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//如果没有设置inverse="true"，那么在更新一的一端的对象时？
			Group g1 = (Group)session.load(Group.class, 1);
			g1.setName("好朋友");
			
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
	
	public void testOneToManyBiUpdate02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			ContactPerson cp1 = new ContactPerson();
			cp1.setId(1);
			cp1.setName("比尔");
			cp1.setAddress("北京");
			cp1.setQq("qqqq");
			
			//记住关联！！！！！！
			Group g1 = new Group();
			g1.setId(1);
			cp1.setGroup(g1);
			
			session.update(cp1);
			
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
	
	public void testOneToManyBiUpdate03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g1 = new Group();
			g1.setId(1);
			g1.setName("朋友");
			
			//设置了inverse="true"之后，下述代码将不起任何作用！
			ContactPerson cp1 = new ContactPerson();
			cp1.setId(6);
			ContactPerson cp2 = new ContactPerson();
			cp1.setId(7);
			g1.addPerson(cp1);
			g1.addPerson(cp2);
			
			session.update(g1);
			
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
	
	public void testOneToManyBiLoad01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//加载ContactPerson对象，可以得到其关联的Group对象
			ContactPerson cp1 = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp1.getName()+",属于："+cp1.getGroup().getName());
			
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
	
	public void testOneToManyBiLoad02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//加载Group对象，可以得到其关联的ContactPerson对象
			Group g1 = (Group)session.load(Group.class,1);
			System.out.println(g1.getName()+",下面包含有："+g1.getPersons().size()+"个用户");
			
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

package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest extends TestCase{

	public void testPersistent01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Student user = new Student(); //【瞬时对象】
			user.setName("李四");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//保存对象
			session.save(user); // 【持久化对象】

			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//理解：对于持久化状态的对象，其状态的变化能够被自动同步到数据库
	public void testPersistent02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Student user = new Student(); //【瞬时对象】
			user.setName("王五");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//保存对象
			session.save(user); // 【持久化对象】

			//1、持久化状态的对象一定会有数据库标识
			System.out.println(user.getId()); //id就是对象的数据库标识
			
			//2、持久化状态的对象，它的状态变化能够被自动同步到数据库
			user.setName("赵毅"); //name状态发生了变化
			user.setAge(30); //age状态发生了变化
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	
	
	
	//理解：对于持久化状态的对象，其状态的变化能够被自动同步到数据库
	public void testPersistent03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 3); //【持久化状态对象】
			
			//1、持久化状态的对象一定会有数据库标识
			System.out.println(s.getId()); //id就是对象的数据库标识
			System.out.println(s.getName()); //name状态
			System.out.println(s.getAge()); //age状态
			System.out.println(s.getCreateTime()); //createTime状态
			
			//2、持久化状态的对象，它的状态变化能够被自动同步到数据库
			s.setName("随便");
			s.setAge(20);
			s.setCreateTime(new Date());
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//对于持久化状态的对象，调用save或update方法毫无意义！
	public void testPersistent04(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = new Student(); //【瞬时对象】
			s.setName("aaaaa");
			
			for(int i=0; i<5; i++){
				session.save(s); //【持久化对象】
			}
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//对于持久化状态的对象，调用save或update方法毫无意义！
	public void testPersistent05(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 1);
			
			session.save(s); //毫无意义
			session.save(s); //毫无意义
			session.save(s); //毫无意义
			session.update(s); //毫无意义
			session.update(s); //毫无意义
			session.update(s); //毫无意义
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//离线对象：对于离线对象，如果不调用update方法，它将无法自动同步到数据库
	public void testDetached01(){
		
		Student s = null;
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			s = (Student)session.get(Student.class, 3); //【持久化状态对象】
			
			//1、持久化状态的对象一定会有数据库标识
			System.out.println(s.getId()); //id就是对象的数据库标识
			System.out.println(s.getName()); //name状态
			System.out.println(s.getAge()); //age状态
			System.out.println(s.getCreateTime()); //createTime状态
			
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
		
		//【离线对象】
		System.out.println(s.getId());
		System.out.println(s.getName()); //name状态
		System.out.println(s.getAge()); //age状态
		System.out.println(s.getCreateTime()); //createTime状态
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			s.setName("xxxxxxxxxxxxxxxxxxxxxxxxx");
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//对于离线对象，必须调用update方法，它才能成为持久化对象
	public void testDetached02(){
		
		Student s = null;
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			s = (Student)session.get(Student.class, 3); //【持久化状态对象】
			
			//1、持久化状态的对象一定会有数据库标识
			System.out.println(s.getId()); //id就是对象的数据库标识
			System.out.println(s.getName()); //name状态
			System.out.println(s.getAge()); //age状态
			System.out.println(s.getCreateTime()); //createTime状态
			
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
		
		//【离线对象】
		System.out.println(s.getId());
		System.out.println(s.getName()); //name状态
		System.out.println(s.getAge()); //age状态
		System.out.println(s.getCreateTime()); //createTime状态
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//update方法，用于把一个离线对象转换成一个持久化对象！
			session.update(s);
			
			//因为s对象重新变成持久化对象，所以对这个对象的状态的变化，将被自动同步到数据库！
			s.setName("xxxxxxxxxxxxxxxxxxxxxxxxx");
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//拥有数据库标识，而未跟session绑定的对象，是离线对象
	public void testDetached03(){
		
		Student s = new Student();
		s.setId(3); //给一个对象赋予一个数据库标识，它就是一个离线对象
		s.setName("bbbbbbb");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//update方法，用于把一个离线对象转换成一个持久化对象！
			//而且，其数据库标识保持不变
			session.update(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	//拥有数据库标识，而未跟session绑定的对象，是离线对象
	public void testDetached04(){
		
		Student s = new Student();
		s.setId(3); //给一个对象赋予一个数据库标识，它就是一个离线对象
		s.setName("ccccccc");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//save方法，用于把一个离线对象转换成一个持久化对象！
			//而且，离线对象的数据库标识被重新分配！！！
			session.save(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testDetached05(){
		
		Student s = new Student();
		s.setId(30); //给一个对象赋予一个数据库标识，它就是一个离线对象
		s.setName("bbbbbbb");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//update方法，用于把一个离线对象转换成一个持久化对象！
			//而且，其数据库标识保持不变
			session.update(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testDetached06(){
		
		Student s = new Student();
		s.setName("bbbbbbb");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//update方法，用于把一个离线对象转换成一个持久化对象！
			//而且，其数据库标识保持不变
			session.update(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testSaveOrUpdate01(){
		
		Student s = new Student();
		s.setName("ddddddd");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//对于瞬时对象，发出insert语句
			session.saveOrUpdate(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testSaveOrUpdate02(){
		
		Student s = new Student();
		s.setId(1);
		s.setName("ddddddd");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//对于离线对象，发出update语句
			session.saveOrUpdate(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testMerge01(){
		
		Student s = new Student();
		s.setId(1); //【离线对象，数据库是1】
		s.setName("eeeeeee");
		s.setAddress("北京");
		s.setAge(20);
		s.setCreateTime(new Date());
		s.setPassword("xxxx");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s1 = (Student)session.get(Student.class, 1); //【持久化对象，数据库标识为1】
			
			//对于数据库标识为1的Student对象，它的name是：
			System.out.println(s1.getName());
			
			//在同一个Session中，不允许同时存在两个具有相同数据库标识的同种类型的持久化对象
			//对一个离线对象调用update方法，可以把这个离线对象变成持久化对象，但在当前session
			//中已经有一个具有相同数据库标识的Student对象，所以，下面的操作无法执行
			session.update(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
	
	public void testMerge02(){
		
		Student s = new Student();
		s.setId(1); //【离线对象，数据库是1】
		s.setName("eeeeeee");
		s.setAddress("北京");
		s.setAge(20);
		s.setCreateTime(new Date());
		s.setPassword("xxxx");
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//打开第二个session对象
		session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Student s1 = (Student)session.get(Student.class, 1); //【持久化对象，数据库标识为1】
			
			//对于数据库标识为1的Student对象，它的name是：
			System.out.println(s1.getName());
			
			//在同一个Session中，不允许同时存在两个具有相同数据库标识的同种类型的持久化对象
			//对一个离线对象调用update方法，可以把这个离线对象变成持久化对象，但在当前session
			//中已经有一个具有相同数据库标识的Student对象，所以，要更新这样的对象，必须调用merge方法
			session.merge(s);
			
			//提交事务
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//出现异常，需回滚事务
			session.getTransaction().rollback();
		}finally{
			//关闭session
			session.close(); //【离线对象】
		}
	}
}

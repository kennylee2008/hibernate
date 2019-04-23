package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

public class ManyToManyTest_Save extends TestCase {
	
	public void testManyToManySave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Employee emp1 = new Employee("张三");
			session.save(emp1);
			
			Employee emp2 = new Employee("李四");
			session.save(emp2);
			
			Employee emp3 = new Employee("王五");
			session.save(emp3);
			
			Role role1 = new Role("系统管理员");
			session.save(role1);
			
			Role role2 = new Role("档案管理员");
			session.save(role2);
			
			Role role3 = new Role("项目经理");
			session.save(role3);
			
			emp1.addRole(role1);
			emp1.addRole(role2);
			
			emp2.addRole(role2);
			emp2.addRole(role3);
			
			emp3.addRole(role2);
			
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

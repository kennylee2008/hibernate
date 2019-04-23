package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_BestPractices extends TestCase {
	
	public void testOneToOneSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//先保存ContactPerson
			ContactPerson cp = new ContactPerson("张三");
			session.save(cp);
			
			//然后保存IdCard
			IdCard idcard = new IdCard("123456789012345678");
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneSave02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			for(int i=0; i<10; i++){
			
				//先保存ContactPerson
				ContactPerson cp = new ContactPerson("张三"+new Random().nextInt(9999999));
				session.save(cp);
				
				//然后保存IdCard
				IdCard idcard = new IdCard("123456789012"+new Random().nextInt(9999999));
				idcard.setPerson(cp);
				session.save(idcard);
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
	
	public void testOneToOneLoad01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//IdCard 参考 ContactPerson 的ID
			//ContactPerson对象先创建，这时候，IdCard可能不存在
			//加载任何一个对象的时候，对于它的单端关联对象，必须保证：
			//   1、如果其关联对象不存在，则这个关联属性的值应该为null
			//   2、如果其关联对象存在，则应该给这个关联属性设置一个值（代理对象或非代理对象）
			//为了保证这一点，在加载ContactPerson对象的时候，必须知道其关联对象是否存在（因为它有可能不存在）
			//所以，hibernate用一个左连接来查询ContactPerson对象
			//虽然在映射文件上加上constrained="true"可以使得在加载ContactPerson对象的同时不
			//去加载IdCard，但这样一来，就形成了一个循环约束关系，导致无法再添加新的数据
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			IdCard ic = cp.getIdCard();
			
			System.out.println(ic.getSn());
			
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
	
	public void testOneToOneLoad02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//IdCard 参考 ContactPerson 的ID
			//即IdCard的ID约束（依赖）ContactPerson的ID
			//即假如IdCard对象存在，则其关联的ContactPerson对象必然存在
			//因此，无需外连接即可知道IdCard的person属性是非空的
			//因此，hibernate就可以创建这个关联对象（代理或非代理）
			IdCard ic = (IdCard)session.load(IdCard.class,1);
			System.out.println(ic.getSn());
			
			ContactPerson cp = ic.getPerson();
			
			System.out.println(cp.getName());
			
			//System.out.println(cp.getIdCard() == ic);
			
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
	
	public void testOneToOneSearch01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//很多SQL语句
			List list = session.createQuery("from ContactPerson").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getName());
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
	
	public void testOneToOneSearch0101(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			List list = session.createQuery("select p.id,p.name,p.address from ContactPerson p").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
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
	
	public void testOneToOneSearch0102(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			List list = session.createQuery("select new ContactPerson(p.id,p.name,p.address,p.qq) from ContactPerson p").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
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
	
	public void testOneToOneSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			List list = session.createQuery("from IdCard").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				IdCard cp = (IdCard) iterator.next();
				System.out.println(cp.getSn()+","+cp.getPerson().getName());
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

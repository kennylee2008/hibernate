package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public class SearchTest_05_Filter extends TestCase {
	
	public void testSearch01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Group g = (Group)session.load(Group.class, 3);
			
//			session.enableFilter("ageFilter")
//				.setParameter("begin", 20)
//				.setParameter("end", 40);
			
			Set<ContactPerson> persons = g.getPersons();
//			for(ContactPerson p:persons){
//				System.out.println(p.getId()+","+p.getName()+","+p.getAge());
//			}
			
			Query query = session.createFilter(persons, "select new map(id as id,name as name) where id < 25 order by id desc");
			List list = query.list();
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

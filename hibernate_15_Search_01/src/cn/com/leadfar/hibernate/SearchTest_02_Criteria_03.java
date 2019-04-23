package cn.com.leadfar.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

/**
 * 测试针对MAP类型的实体对象（动态对象）
 * @author Administrator
 *
 */
public class SearchTest_02_Criteria_03 extends TestCase {
	
	public void testSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession().getSession(EntityMode.MAP);
		
		try{
			//开启事务
			session.beginTransaction();
			
			for(int i=0; i<200; i++){
				Map f = new HashMap();
				f.put("age",new Random().nextInt(99999));
				f.put("qq",new Random().nextLong());
				f.put("name", "朋友"+random(99999));
				f.put("createTime", new Date());
				
				session.save("org.topxp.oa.system.model.Friend",f);
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
			
			Criteria criteria = session.createCriteria("org.topxp.oa.system.model.Friend");
			
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
			//否则报错（hibernate自动生成的sql语句有语法错误！）
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
	
	public void testSearch02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		String entityName = "org.topxp.oa.system.model.Friend";
		try{
			//开启事务
			session.beginTransaction();
			
			
//			Criteria criteria = session.createCriteria(entityName);
//			criteria.setProjection(Projections.rowCount());
//			
//			int total = ((Long)criteria.uniqueResult()).intValue();
			
			//查询当前页的数据
			Criteria criteria = session.createCriteria(entityName);
			
			//Projection
			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id")
					.add(Projections.property("name"),"name")
			);
			
			//如果没有查询条件，倒是可以转换！！！
			criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			criteria.setFirstResult(0);
			criteria.setMaxResults(Integer.MAX_VALUE);
			
			List datas = criteria.list();
			
			for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
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
	
	public void testSearch03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria("org.topxp.oa.system.model.Friend");
			
			criteria.add(
				Restrictions.like("name", "1",MatchMode.ANYWHERE)
			);
			

			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id_")
					.add(Projections.property("name"),"name_")
					.add(Projections.sqlProjection("'xxxx' as entityName", new String[]{"entityName"}, new Type[]{Hibernate.STRING}),"entityName")
					//.add(Projections.sqlProjection("date_format()", new String[]{"xxxx"}, new Type[]{Hibernate.STRING}),"$type$")
			);
			
			//Map
			//如果不命别名，sql语句没错，但是查询结果是空的！
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

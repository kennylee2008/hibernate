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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

/**
 * 测试针对MAP类型的实体对象（动态对象）
 * @author Administrator
 *
 */
public class SearchTest_02_Criteria_04_association extends TestCase {
	
	public void testSave01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession().getSession(EntityMode.MAP);
		
		try{
			//开启事务
			session.beginTransaction();
			
			//分类信息
			for(int i=0; i<20; i++){
				Map ft = new HashMap(); //friendType
				ft.put("name", "朋友分类"+new Random().nextInt(99999));
				session.save("org.topxp.oa.system.model.FriendType",ft);
			}
			
			//子分类信息
			for(int i=0; i<50; i++){
				Map ft = new HashMap(); //friendType
				ft.put("name", "子朋友分类"+new Random().nextInt(99999));
				int id = 1;
				if((id = new Random().nextInt(19)) == 0){
					id = 1;
				}
				ft.put("parent", session.get("org.topxp.oa.system.model.FriendType", new Long(id)));
				session.save("org.topxp.oa.system.model.FriendType",ft);
			}
			
			//分类下的人员
			for(int i=0; i<200; i++){
				Map f = new HashMap();
				f.put("age",new Random().nextInt(99999));
				f.put("qq",new Random().nextLong());
				f.put("name", "朋友"+random(99999));
				f.put("createTime", new Date());
				
				//人员对应的分类
				f.put("type", session.get("org.topxp.oa.system.model.FriendType", new Long(new Random().nextInt(85))));
				
				//人员地址，这是一个Component对象
				Map addr = new HashMap();
				addr.put("city", "北京"+new Random().nextInt(9999));
				addr.put("street", "西大望路"+new Random().nextInt(99999));
				f.put("address", addr);
				
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
			
			//这些是属性
//			Property name = Property.forName("name");
//			Property id = Property.forName("id");
//			Property typeName = Property.forName("type.name");
//			Property typepName = Property.forName("type_parent.name");
//			
			//From
			Criteria criteria = session.createCriteria("org.topxp.oa.system.model.Friend");
			
			//type.parent.parent -> type_parent_.parent
			criteria.createAlias("type_parent_.parent", "tpp_",Criteria.LEFT_JOIN);
			
			//type.parent -> type_.parent
			 criteria.createAlias("type_.parent", "type_parent_",Criteria.LEFT_JOIN);
			 
			 //type -> type
			 criteria.createAlias("type", "type_"); //关联查询
			
			//criteria = criteria.createAlias("type.parent", "type_parent_",Criteria.LEFT_JOIN);
			//criteria = criteria.createAlias("address", "address_"); //不能用这种方式进行组件查询
			
			
			
			//criteria.add(typeName.like("1",MatchMode.ANYWHERE));
			
			//Select
			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id_")
					.add(Projections.property("name"),"name_")
					.add(Projections.property("type_.name"),"type_name_")
					.add(Projections.property("type_parent_.name"),"type_parent_name_")
					.add(Projections.property("tpp_.name"),"tpp_name")
					.add(Projections.property("address.city"),"address_city_") //普通的组件
			);
			
			//Where
			criteria.add(Restrictions.like("name","1", MatchMode.ANYWHERE)); //普通属性
			criteria.add(Restrictions.like("address.city", "2", MatchMode.ANYWHERE)); //组件中的属性
			criteria.add(Restrictions.like("type_.name","31", MatchMode.ANYWHERE)); //关联属性约束
//			criteria.add(Restrictions.like("type_parent_.name","1", MatchMode.ANYWHERE)); //关联属性约束
//			criteria.add(Restrictions.isNull("type_parent_.parent"));
			
			Criterion c1 = Restrictions.or(
				Restrictions.like("name","1", MatchMode.ANYWHERE),
				Restrictions.like("address.city", "2", MatchMode.ANYWHERE)
			);
			criteria.add(c1);
			
//			//Restrictions.
//			Criterion c2 = Restrictions.and(c1, Restrictions.like("type_.name","1", MatchMode.ANYWHERE));
//			Criterion c3 = Restrictions.or(c2, Restrictions.like("type_parent_.name","1", MatchMode.ANYWHERE));
//			
//			criteria.add(c3);
			
			//Restrictions.
			
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
	
}

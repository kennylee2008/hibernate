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
 * �������MAP���͵�ʵ����󣨶�̬����
 * @author Administrator
 *
 */
public class SearchTest_02_Criteria_04_association extends TestCase {
	
	public void testSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession().getSession(EntityMode.MAP);
		
		try{
			//��������
			session.beginTransaction();
			
			//������Ϣ
			for(int i=0; i<20; i++){
				Map ft = new HashMap(); //friendType
				ft.put("name", "���ѷ���"+new Random().nextInt(99999));
				session.save("org.topxp.oa.system.model.FriendType",ft);
			}
			
			//�ӷ�����Ϣ
			for(int i=0; i<50; i++){
				Map ft = new HashMap(); //friendType
				ft.put("name", "�����ѷ���"+new Random().nextInt(99999));
				int id = 1;
				if((id = new Random().nextInt(19)) == 0){
					id = 1;
				}
				ft.put("parent", session.get("org.topxp.oa.system.model.FriendType", new Long(id)));
				session.save("org.topxp.oa.system.model.FriendType",ft);
			}
			
			//�����µ���Ա
			for(int i=0; i<200; i++){
				Map f = new HashMap();
				f.put("age",new Random().nextInt(99999));
				f.put("qq",new Random().nextLong());
				f.put("name", "����"+random(99999));
				f.put("createTime", new Date());
				
				//��Ա��Ӧ�ķ���
				f.put("type", session.get("org.topxp.oa.system.model.FriendType", new Long(new Random().nextInt(85))));
				
				//��Ա��ַ������һ��Component����
				Map addr = new HashMap();
				addr.put("city", "����"+new Random().nextInt(9999));
				addr.put("street", "������·"+new Random().nextInt(99999));
				f.put("address", addr);
				
				session.save("org.topxp.oa.system.model.Friend",f);
			}
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
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
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��Щ������
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
			 criteria.createAlias("type", "type_"); //������ѯ
			
			//criteria = criteria.createAlias("type.parent", "type_parent_",Criteria.LEFT_JOIN);
			//criteria = criteria.createAlias("address", "address_"); //���������ַ�ʽ���������ѯ
			
			
			
			//criteria.add(typeName.like("1",MatchMode.ANYWHERE));
			
			//Select
			criteria.setProjection(
				Projections.projectionList()
					.add(Projections.property("id"),"id_")
					.add(Projections.property("name"),"name_")
					.add(Projections.property("type_.name"),"type_name_")
					.add(Projections.property("type_parent_.name"),"type_parent_name_")
					.add(Projections.property("tpp_.name"),"tpp_name")
					.add(Projections.property("address.city"),"address_city_") //��ͨ�����
			);
			
			//Where
			criteria.add(Restrictions.like("name","1", MatchMode.ANYWHERE)); //��ͨ����
			criteria.add(Restrictions.like("address.city", "2", MatchMode.ANYWHERE)); //����е�����
			criteria.add(Restrictions.like("type_.name","31", MatchMode.ANYWHERE)); //��������Լ��
//			criteria.add(Restrictions.like("type_parent_.name","1", MatchMode.ANYWHERE)); //��������Լ��
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
			//��Ҫ�����������ұ������ܺ�������һ����
			//���򱨴�hibernate�Զ����ɵ�sql������﷨���󣡣�
			criteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);

			List list = criteria.list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object o = (Object) iterator.next();
				System.out.println(o);
			}
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); 
		}
	}
	
}

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
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//******************* �Ӷ��һ�˹�������֮��Ĺ�����ϵ ***********************//
			Group g1 = new Group("����");
			session.save(g1);//g1����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g2 = new Group("İ����");
			session.save(g2);//g2����˳־û�����״̬������ӵ�������ݿ��ʶ
			Group g3 = new Group("����");
			session.save(g3);//g3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp1 = new ContactPerson("�ȶ��Ǵ�");
			//����cp1��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp1.setGroup(g1);
			session.save(cp1);//cp1����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp2 = new ContactPerson("�ͷ���");
			//����cp2��g1����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g1������g1�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp2.setGroup(g1);
			session.save(cp2);//cp2����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp3 = new ContactPerson("·�˼�");
			//����cp3��g2����֮��Ĺ���
			//���ԣ�hibernate�ܹ����ʵ�g2������g2�����ݿ��ʶ
			//����ӳ���ļ���ָʾ���洢��groupId�ֶ�
			cp3.setGroup(g2);			
			session.save(cp3);//cp3����˳־û�����״̬������ӵ�������ݿ��ʶ
			
			ContactPerson cp4 = new ContactPerson("·����");
			session.save(cp4);
			
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
	
	public void testSave02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			for(int i=0; i<200; i++){
				ContactPerson cp = new ContactPerson("��Ա"+new Random().nextInt(99999));
				cp.setAddress("����"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //����״̬
				session.save(cp);
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
	
	public void testSave03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Group g = new Group();
			g.setName("ĳĳ��");
			session.save(g);
			
			for(int i=0; i<100; i++){
				ContactPerson cp = new ContactPerson("��Ա"+new Random().nextInt(99999));
				cp.setAddress("����"+new Random().nextInt(99999));
				cp.setQq(""+new Random().nextInt(99999));
				cp.setDeleted(0); //����״̬
				cp.setGroup(g);
				session.save(cp);
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
	
	public void testSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//session����Ļ��棬�������ʵ�����
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//session����Ļ��棬�������ʵ�����
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			session.refresh(cp);
			session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			
			
			//
			ContactPerson cp = new ContactPerson();
			cp.setId(4);
			
			//���²�������ִ�У��������ڴ��е�cp��������ϸ�������ֵ��
			session.refresh(cp);
			session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 4);
			System.out.println(cp2.getName());
			session.refresh(cp2);
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh_02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			session.get(ContactPerson.class, 1);
			
			//
			ContactPerson cp = new ContactPerson();
			cp.setId(1);
			
			//���²�������Ϊid=1�Ķ����Ѿ���session�У�����Ĳ�����
			//���ٰ�������䵽cp�����У����ᷢ����ѯ��䣡��
			session.refresh(cp);
			//session.refresh(cp);

			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			System.out.println(cp == cp2);
			
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
	
	public void testSearch0101_refresh_03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp0 = (ContactPerson)session.get(ContactPerson.class, 4);
			cp0.setName("XXX"+new Random().nextInt(9999));
			
			session.evict(cp0);
			
			session.refresh(cp0);
			
			cp0.setName("XXX"+new Random().nextInt(9999));
			
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
	
	public void testSearch0102(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
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
	
	public void testSearch02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//session����Ļ��棬�������ʵ�����
			List persons = session.createQuery("from ContactPerson").list();
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			
			ContactPerson cp3 = (ContactPerson)session.load(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
	
	public void testSearch03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//session����Ļ��棬�������ʵ�����
			List persons = session.createQuery("from ContactPerson").list();
			
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
		
		//�ڶ���session
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ٴη���SQL��ѯ���
			ContactPerson cp2 = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp2.getName());
			
			//�ٴη���SQL��ѯ���
			ContactPerson cp3 = (ContactPerson)session.get(ContactPerson.class, 10);
			System.out.println(cp3.getName());
			
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
	
	public void testSearch04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��һ����list()���ᷢ��SQL���
			List persons = session.createQuery("from ContactPerson").list();
			
			//�ڶ�����list()�����ᷢ��SQL���
			List persons2 = session.createQuery("from ContactPerson").list();
			
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
	
	public void testSearch05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��һ����iterate()���ᷢ��SQL���
			Iterator persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
			}
			
			//�ڶ�����iterate()�����ٷ���SQL���
			persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
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
	
	public void testSearch06(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��һ����list()���ᷢ��SQL���
			List personsList = session.createQuery("from ContactPerson").list();

			
			//�ڶ�����iterate()�����ٷ���SQL���
			Iterator persons = session.createQuery("from ContactPerson").iterate();
			for (; persons.hasNext();) {
				ContactPerson cp = (ContactPerson) persons.next();
				System.out.print(cp.getName()+",");
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
	
	public void testSearch07(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress()+","+cp.getQq());
			
			ContactPerson cp2 = new ContactPerson();
			cp2.setId(1);
			cp2.setName("xxxx");
			cp2.setAddress("kkdjf");
			cp2.setQq("93849384");
			cp2.setGroup(cp.getGroup());
			
			//������ֱ�ӵ���update
			//session.update(cp2);
			
			//����ʹ��merge����������
			//session.merge(cp2);
			
			//Ҳ�����Ȱѳ־û������һ�������������Ȼ���ڵ���update������
			session.evict(cp);
			session.update(cp2);
			
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
	
	public void testSearch08(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			ContactPerson cp = (ContactPerson)session.get(ContactPerson.class, 1);
			System.out.println(cp.getId()+","+cp.getName()+","+cp.getAddress()+","+cp.getQq());
			
			cp.setName("ffffffffffffffffff");
			
			//clear()���������һ�����棬����cp�����ٱ�session������
			//��ˣ��������ύ��ʱ������״̬�ı仯�����޷��Զ�ͬ�������ݿ�!
			session.clear();
			
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
	
	public void testSearch09(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��������������
			for(int i=0; i<1000000000;i ++){
				ContactPerson cp = new ContactPerson();
				cp.setName("xxxx"+i);
				session.save(cp);
				if(i % 200 == 0){
					session.flush();
					session.clear();
				}
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

package cn.com.leadfar.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.Session;

public class OneToOneTest_BestPractices extends TestCase {
	
	public void testOneToOneSave01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ȱ���ContactPerson
			ContactPerson cp = new ContactPerson("����");
			session.save(cp);
			
			//Ȼ�󱣴�IdCard
			IdCard idcard = new IdCard("123456789012345678");
			idcard.setPerson(cp);
			session.save(idcard);
			
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
	
	public void testOneToOneSave02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			for(int i=0; i<10; i++){
			
				//�ȱ���ContactPerson
				ContactPerson cp = new ContactPerson("����"+new Random().nextInt(9999999));
				session.save(cp);
				
				//Ȼ�󱣴�IdCard
				IdCard idcard = new IdCard("123456789012"+new Random().nextInt(9999999));
				idcard.setPerson(cp);
				session.save(idcard);
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
	
	public void testOneToOneLoad01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//IdCard �ο� ContactPerson ��ID
			//ContactPerson�����ȴ�������ʱ��IdCard���ܲ�����
			//�����κ�һ�������ʱ�򣬶������ĵ��˹������󣬱��뱣֤��
			//   1�������������󲻴��ڣ�������������Ե�ֵӦ��Ϊnull
			//   2����������������ڣ���Ӧ�ø����������������һ��ֵ����������Ǵ������
			//Ϊ�˱�֤��һ�㣬�ڼ���ContactPerson�����ʱ�򣬱���֪������������Ƿ���ڣ���Ϊ���п��ܲ����ڣ�
			//���ԣ�hibernate��һ������������ѯContactPerson����
			//��Ȼ��ӳ���ļ��ϼ���constrained="true"����ʹ���ڼ���ContactPerson�����ͬʱ��
			//ȥ����IdCard��������һ�������γ���һ��ѭ��Լ����ϵ�������޷�������µ�����
			ContactPerson cp = (ContactPerson)session.load(ContactPerson.class, 1);
			System.out.println(cp.getName());
			
			IdCard ic = cp.getIdCard();
			
			System.out.println(ic.getSn());
			
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
	
	public void testOneToOneLoad02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//IdCard �ο� ContactPerson ��ID
			//��IdCard��IDԼ����������ContactPerson��ID
			//������IdCard������ڣ����������ContactPerson�����Ȼ����
			//��ˣ����������Ӽ���֪��IdCard��person�����Ƿǿյ�
			//��ˣ�hibernate�Ϳ��Դ�������������󣨴����Ǵ���
			IdCard ic = (IdCard)session.load(IdCard.class,1);
			System.out.println(ic.getSn());
			
			ContactPerson cp = ic.getPerson();
			
			System.out.println(cp.getName());
			
			//System.out.println(cp.getIdCard() == ic);
			
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
	
	public void testOneToOneSearch01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�ܶ�SQL���
			List list = session.createQuery("from ContactPerson").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getName());
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
	
	public void testOneToOneSearch0101(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			List list = session.createQuery("select p.id,p.name,p.address from ContactPerson p").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] cp = (Object[]) iterator.next();
				System.out.println(cp[0]+","+cp[1]+","+cp[2]);
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
	
	public void testOneToOneSearch0102(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			List list = session.createQuery("select new ContactPerson(p.id,p.name,p.address,p.qq) from ContactPerson p").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ContactPerson cp = (ContactPerson) iterator.next();
				System.out.println(cp.getId()+","+cp.getName());
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
	
	public void testOneToOneSearch02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			List list = session.createQuery("from IdCard").list();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				IdCard cp = (IdCard) iterator.next();
				System.out.println(cp.getSn()+","+cp.getPerson().getName());
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

package cn.com.leadfar.hibernate;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class StudentTest extends TestCase{

	public void testPersistent01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Student user = new Student(); //��˲ʱ����
			user.setName("����");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//�������
			session.save(user); // ���־û�����

			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//��⣺���ڳ־û�״̬�Ķ�����״̬�ı仯�ܹ����Զ�ͬ�������ݿ�
	public void testPersistent02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Student user = new Student(); //��˲ʱ����
			user.setName("����");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//�������
			session.save(user); // ���־û�����

			//1���־û�״̬�Ķ���һ���������ݿ��ʶ
			System.out.println(user.getId()); //id���Ƕ�������ݿ��ʶ
			
			//2���־û�״̬�Ķ�������״̬�仯�ܹ����Զ�ͬ�������ݿ�
			user.setName("����"); //name״̬�����˱仯
			user.setAge(30); //age״̬�����˱仯
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	
	
	
	//��⣺���ڳ־û�״̬�Ķ�����״̬�ı仯�ܹ����Զ�ͬ�������ݿ�
	public void testPersistent03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 3); //���־û�״̬����
			
			//1���־û�״̬�Ķ���һ���������ݿ��ʶ
			System.out.println(s.getId()); //id���Ƕ�������ݿ��ʶ
			System.out.println(s.getName()); //name״̬
			System.out.println(s.getAge()); //age״̬
			System.out.println(s.getCreateTime()); //createTime״̬
			
			//2���־û�״̬�Ķ�������״̬�仯�ܹ����Զ�ͬ�������ݿ�
			s.setName("���");
			s.setAge(20);
			s.setCreateTime(new Date());
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//���ڳ־û�״̬�Ķ��󣬵���save��update�����������壡
	public void testPersistent04(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = new Student(); //��˲ʱ����
			s.setName("aaaaa");
			
			for(int i=0; i<5; i++){
				session.save(s); //���־û�����
			}
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//���ڳ־û�״̬�Ķ��󣬵���save��update�����������壡
	public void testPersistent05(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s = (Student)session.get(Student.class, 1);
			
			session.save(s); //��������
			session.save(s); //��������
			session.save(s); //��������
			session.update(s); //��������
			session.update(s); //��������
			session.update(s); //��������
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//���߶��󣺶������߶������������update�����������޷��Զ�ͬ�������ݿ�
	public void testDetached01(){
		
		Student s = null;
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			s = (Student)session.get(Student.class, 3); //���־û�״̬����
			
			//1���־û�״̬�Ķ���һ���������ݿ��ʶ
			System.out.println(s.getId()); //id���Ƕ�������ݿ��ʶ
			System.out.println(s.getName()); //name״̬
			System.out.println(s.getAge()); //age״̬
			System.out.println(s.getCreateTime()); //createTime״̬
			
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
		
		//�����߶���
		System.out.println(s.getId());
		System.out.println(s.getName()); //name״̬
		System.out.println(s.getAge()); //age״̬
		System.out.println(s.getCreateTime()); //createTime״̬
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			s.setName("xxxxxxxxxxxxxxxxxxxxxxxxx");
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//�������߶��󣬱������update�����������ܳ�Ϊ�־û�����
	public void testDetached02(){
		
		Student s = null;
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			s = (Student)session.get(Student.class, 3); //���־û�״̬����
			
			//1���־û�״̬�Ķ���һ���������ݿ��ʶ
			System.out.println(s.getId()); //id���Ƕ�������ݿ��ʶ
			System.out.println(s.getName()); //name״̬
			System.out.println(s.getAge()); //age״̬
			System.out.println(s.getCreateTime()); //createTime״̬
			
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
		
		//�����߶���
		System.out.println(s.getId());
		System.out.println(s.getName()); //name״̬
		System.out.println(s.getAge()); //age״̬
		System.out.println(s.getCreateTime()); //createTime״̬
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//update���������ڰ�һ�����߶���ת����һ���־û�����
			session.update(s);
			
			//��Ϊs�������±�ɳ־û��������Զ���������״̬�ı仯�������Զ�ͬ�������ݿ⣡
			s.setName("xxxxxxxxxxxxxxxxxxxxxxxxx");
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//ӵ�����ݿ��ʶ����δ��session�󶨵Ķ��������߶���
	public void testDetached03(){
		
		Student s = new Student();
		s.setId(3); //��һ��������һ�����ݿ��ʶ��������һ�����߶���
		s.setName("bbbbbbb");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//update���������ڰ�һ�����߶���ת����һ���־û�����
			//���ң������ݿ��ʶ���ֲ���
			session.update(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	//ӵ�����ݿ��ʶ����δ��session�󶨵Ķ��������߶���
	public void testDetached04(){
		
		Student s = new Student();
		s.setId(3); //��һ��������һ�����ݿ��ʶ��������һ�����߶���
		s.setName("ccccccc");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//save���������ڰ�һ�����߶���ת����һ���־û�����
			//���ң����߶�������ݿ��ʶ�����·��䣡����
			session.save(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testDetached05(){
		
		Student s = new Student();
		s.setId(30); //��һ��������һ�����ݿ��ʶ��������һ�����߶���
		s.setName("bbbbbbb");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//update���������ڰ�һ�����߶���ת����һ���־û�����
			//���ң������ݿ��ʶ���ֲ���
			session.update(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testDetached06(){
		
		Student s = new Student();
		s.setName("bbbbbbb");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//update���������ڰ�һ�����߶���ת����һ���־û�����
			//���ң������ݿ��ʶ���ֲ���
			session.update(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testSaveOrUpdate01(){
		
		Student s = new Student();
		s.setName("ddddddd");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����˲ʱ���󣬷���insert���
			session.saveOrUpdate(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testSaveOrUpdate02(){
		
		Student s = new Student();
		s.setId(1);
		s.setName("ddddddd");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//�������߶��󣬷���update���
			session.saveOrUpdate(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testMerge01(){
		
		Student s = new Student();
		s.setId(1); //�����߶������ݿ���1��
		s.setName("eeeeeee");
		s.setAddress("����");
		s.setAge(20);
		s.setCreateTime(new Date());
		s.setPassword("xxxx");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s1 = (Student)session.get(Student.class, 1); //���־û��������ݿ��ʶΪ1��
			
			//�������ݿ��ʶΪ1��Student��������name�ǣ�
			System.out.println(s1.getName());
			
			//��ͬһ��Session�У�������ͬʱ��������������ͬ���ݿ��ʶ��ͬ�����͵ĳ־û�����
			//��һ�����߶������update���������԰�������߶����ɳ־û����󣬵��ڵ�ǰsession
			//���Ѿ���һ��������ͬ���ݿ��ʶ��Student�������ԣ�����Ĳ����޷�ִ��
			session.update(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
	
	public void testMerge02(){
		
		Student s = new Student();
		s.setId(1); //�����߶������ݿ���1��
		s.setName("eeeeeee");
		s.setAddress("����");
		s.setAge(20);
		s.setCreateTime(new Date());
		s.setPassword("xxxx");
		
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		//�򿪵ڶ���session����
		session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			Student s1 = (Student)session.get(Student.class, 1); //���־û��������ݿ��ʶΪ1��
			
			//�������ݿ��ʶΪ1��Student��������name�ǣ�
			System.out.println(s1.getName());
			
			//��ͬһ��Session�У�������ͬʱ��������������ͬ���ݿ��ʶ��ͬ�����͵ĳ־û�����
			//��һ�����߶������update���������԰�������߶����ɳ־û����󣬵��ڵ�ǰsession
			//���Ѿ���һ��������ͬ���ݿ��ʶ��Student�������ԣ�Ҫ���������Ķ��󣬱������merge����
			session.merge(s);
			
			//�ύ����
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			//�����쳣����ع�����
			session.getTransaction().rollback();
		}finally{
			//�ر�session
			session.close(); //�����߶���
		}
	}
}

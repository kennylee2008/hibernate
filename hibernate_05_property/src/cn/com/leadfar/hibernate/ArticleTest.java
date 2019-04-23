package cn.com.leadfar.hibernate;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

public class ArticleTest extends TestCase{

	public void testProperty01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Article a = new Article("�캽��Զ");
			a.setTitle("sdfsdfsdf");
			//a.setContent("���������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ��������������µ�����");
			//a.setCreateTime(new Date());
			//a.setAttachement(FileUtils.readFileToByteArray(new File("D:\\360sd_se.exe")));
			//a.setAuthor("�캽��Զ");
			
			//�������
			session.save(a); 

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
	
	public void testProperty02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
//			//����ʵ�����
//			Article a = new Article();
//			a.setId(3);
//			a.setTitle("ooooooo");
//			a.setContent("���������µ�����");
//			a.setCreateTime(new Date());
			
			//�������
//			session.update(a); 

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
	
	public void testProperty03(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//����ʵ�����
			Article a = (Article)session.get(Article.class, 4);

			System.out.println(a.getTitle());
			
			//byte[] attachment = a.getAttachement();
			
			//FileUtils.writeByteArrayToFile(new File("d:\\temp\\some.exe"), attachment);
			
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

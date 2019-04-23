package cn.com.leadfar.hibernate;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

public class ArticleTest extends TestCase{

	public void testProperty01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Article a = new Article("领航致远");
			a.setTitle("sdfsdfsdf");
			//a.setContent("这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容这里是文章的内容");
			//a.setCreateTime(new Date());
			//a.setAttachement(FileUtils.readFileToByteArray(new File("D:\\360sd_se.exe")));
			//a.setAuthor("领航致远");
			
			//保存对象
			session.save(a); 

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
	
	public void testProperty02(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
//			//创建实体对象
//			Article a = new Article();
//			a.setId(3);
//			a.setTitle("ooooooo");
//			a.setContent("这里是文章的内容");
//			a.setCreateTime(new Date());
			
			//保存对象
//			session.update(a); 

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
	
	public void testProperty03(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Article a = (Article)session.get(Article.class, 4);

			System.out.println(a.getTitle());
			
			//byte[] attachment = a.getAttachement();
			
			//FileUtils.writeByteArrayToFile(new File("d:\\temp\\some.exe"), attachment);
			
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

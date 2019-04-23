package cn.com.leadfar.hibernate;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

public class DocumentTest extends TestCase{

	public void testProperty01(){
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			Document d = new Document();
			d.setDvalue(2345678.83764874938374548);
			d.setFvalue(23456.457f);
			session.save(d);

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

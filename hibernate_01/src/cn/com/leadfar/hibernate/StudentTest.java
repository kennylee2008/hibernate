package cn.com.leadfar.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;

public class StudentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//默认读取类路径根目录下的hibernate.cfg.xml配置文件
		Configuration cfg = new Configuration().configure();
		
		//创建SessionFactory
		SessionFactory factory = cfg.buildSessionFactory();
				
		//创建Hibenate Session
		Session session = factory.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//创建实体对象
			Student user = new Student();
			user.setName("张三");
			user.setPassword("zhangsan");
			user.setAge(19);
			user.setCreateTime(new Date());
			
			//保存对象
			session.save(user);

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

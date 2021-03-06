package cn.com.leadfar.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory;
	static{
		factory = new Configuration().configure().buildSessionFactory();
	}
	
	public static Session openSession(){
		return factory.openSession();
	}
}

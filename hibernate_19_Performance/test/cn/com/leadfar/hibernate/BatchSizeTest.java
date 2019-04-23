package cn.com.leadfar.hibernate;

import junit.framework.TestCase;

import org.hibernate.CacheMode;
import org.hibernate.Session;

public class BatchSizeTest extends TestCase{
	
	public void testBatchsize01(){
		
		long begin = System.currentTimeMillis();
		
		//创建Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//开启事务
			session.beginTransaction();
			
			//假如本实体类启用了二级缓存，在批量操作之前，请把二级缓存关闭
			//或者设置CacheMode.IGNORE，不要在批量操作期间与二级缓存交互
			session.setCacheMode(CacheMode.IGNORE);
			
			/**
			 * 假如实体类的ID生成策略是native，save的时候，batch_size配置不会起作用
			 */
			for(int i=0; i<100000; i++){
				ContactPerson cp = new ContactPerson("测试人员");
				session.save(cp);
				
				/**
				 * 批量插入的数量最好与batch_size配置一致！
				 */
				if(i % 25 == 0){
					session.flush();
					session.clear();
				}
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
		
		long end = System.currentTimeMillis();
		
		/**
		 * mysql的JDBC驱动URL需加上特殊的配置参数，如下所示：
		 * jdbc:mysql://localhost/hibernate?rewriteBatchedStatements=true
		 * batch_size=1,总耗时：大约27秒
		 * batch_size=25,总耗时：大约12秒
		 */
		System.out.println("总耗时："+(end - begin)+"ms");
	}	
}

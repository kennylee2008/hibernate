package cn.com.leadfar.hibernate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

public class TreeTest extends TestCase {
	
	public void testSaveTree(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			String rootDirPath = "D:\\work\\projects\\ejb3\\ejb_01";
			
			File root = new File(rootDirPath);
			
			save(session,root,null,1);
			
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
	
	public void testListTree01(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��������һ���ڵ��ID���г�����ڵ㼰���������е��ӽڵ�
			int id = 1;
			
			Node root = (Node)session.load(Node.class, id);
			removeDenys(root.getChildren());
			printChildren(root);
			
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
	
	private void removeDenys(Collection nodes){
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			Node n = (Node) iterator.next();
			if(n.getName().equals("ejb3")){
				iterator.remove();
			}else{
				if(n.getChildren().size() != 0){
					removeDenys(n.getChildren());
				}
			}
		}
	}
	
	public void testListTree02(){
		//����Hibenate Session
		Session session = HibernateUtil.openSession();
		
		try{
			//��������
			session.beginTransaction();
			
			//��������һ���ڵ��ID���г�����ڵ㼰���������е��ӽڵ�
			int id = 7;
			
			Node root = (Node)session.load(Node.class, id);
			
			printParent(root);
			
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
	
	private void save(Session session,File file,Node parent,int level){
		Node node = new Node();
		node.setLevel(level);
		node.setName(file.getName());
		node.setParent(parent);
		session.save(node);
		
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f:files){
				save(session,f,node,level+1);
			}
		}
	}
	
	private void printChildren(Node node){
		for(int i=1; i<node.getLevel(); i++){
			System.out.print("   ");
		}
		System.out.println(node.getName());
		if(node.getChildren().size() > 0){
			for(Node child:node.getChildren()){
				printChildren(child);
			}
		}
	}
	
	private void printParent(Node node){
		System.out.print(node.getName());
		if(node.getParent() != null){
			System.out.print(" --> ");
			printParent(node.getParent());
		}
	}
}

package ua.ishchenko.core;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ua.ishchenko.database.DBUser;
import ua.ishchenko.database.util.HibernateUtil;

public class test {
	
	public static void main(String [] str)
	{
		System.out.println("Maven + Hibernate + Oracle");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		/*DBUser user = new DBUser();
		user.setUserId(112);
		user.setUsername("Bolek");
		user.setCreatedDate(new Date());
		
		session.save(user);
		DBUser usr = session.load(arg0, arg1);
		session.getTransaction().commit();
		*/
		//Query queryResult = session.createQuery("from DBUSER");
		//List<Products> products  = (List<Products>) session.createQuery("from DBUSER").list();
		List<DBUser> allUsers = session.createCriteria(DBUser.class).list();
		  
		  for (int i = 0; i < allUsers.size(); i++) {  
			  DBUser user = (DBUser) allUsers.get(i);  
			  System.out.println("User "+user.getUsername()); 
		  }  
		
	}
}

package com.ml.weatherforecaster.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public String out="<br>HibernateUtil LOG:<br>";
    private static HibernateUtil uniqueInstance;
    private SessionFactory sessionFactory;

    private HibernateUtil() {
	    try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
	    } catch (Exception e) {
			String output= e.toString();
			out+=output;
			System.err.println("Exception while trying to do 'sessionFactory = new Configuration().configure().buildSessionFactory()'. Error message: "+output);
	    }
    }

    public static HibernateUtil getInstance() {
	  if (uniqueInstance == null) {
		  uniqueInstance = new HibernateUtil();
	  }
	  return uniqueInstance;
    }
  
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
 
	
}
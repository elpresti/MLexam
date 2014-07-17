package com.ml.weatherforecaster.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  public static String out="<br>HibernateUtil LOG:<br>";

  private static final SessionFactory sessionFactory;
	
  static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			String output= "Initial SessionFactory creation failed." + ex.toString();
			HibernateUtil.out+=output;
			System.err.println(output);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
}
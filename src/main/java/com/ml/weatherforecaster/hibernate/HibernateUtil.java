package com.ml.weatherforecaster.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;

public class HibernateUtil {

  public static String out="<br>HibernateUtil LOG:<br>";
  private static Session session;
  private static SessionFactory sessionFactory;
  
  public static Session getOpenedSession(){
    try{
      if (session == null){
         sessionFactory = new Configuration().configure().buildSessionFactory();
         session = sessionFactory.openSession();
      }else{
        if (!session.isOpen()){
          session = sessionFactory.openSession();
        }
      }
    }catch(Exception e){
      out += "<br>"+e.toString();
      System.out.println(e.toString());
    }
    return session;
  }
  
  
}
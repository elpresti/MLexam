package com.ml.weatherforecaster.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ml.weatherforecaster.model.Galaxy;
import com.ml.weatherforecaster.util.GalaxyUtil;

public class HibernateGalaxy {
	
	private static HibernateGalaxy uniqueInstance;

	private HibernateGalaxy() {
	}

	public static HibernateGalaxy getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new HibernateGalaxy();
		}
		return uniqueInstance;
	}
	
	public boolean savePlanetsInfo(){
		boolean jobDone=false;
		
		return jobDone;
	}
	
	public int getOptimumConditionsPeriods(){
		int periodsAmount=-1;
		
		Session session = HibernateUtil.getOpenedSession();
		session.beginTransaction();
		/* parte nueva */
		Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
		if (galaxy != null  &&  galaxy.getDroughtDays() != null){
			periodsAmount = GalaxyUtil.getInstance().getNumberOfPeriods(new ArrayList<Integer>(galaxy.getDroughtDays()));
			session.getTransaction().commit();
		}
		
		/* -- parte vieja --
		Query query = session.createQuery("from Galaxy.droughtDays");
		int pageIndex=0;
		int pageResults=100;
		
		query.setFirstResult(pageIndex*pageResults);
		query.setMaxResults(pageResults);
		List<Integer> optimumDays = (List<Integer>) query.list();
		session.getTransaction().commit();
		periodsAmount=GalaxyUtil.getInstance().getNumberOfPeriods(new ArrayList<Integer>(optimumDays));
		while (optimumDays.size()==pageResults){
			pageIndex++;
			query.setFirstResult(pageIndex*pageResults);
			optimumDays = (List<Integer>) query.list();
			session.getTransaction().commit();
			periodsAmount+=GalaxyUtil.getInstance().getNumberOfPeriods(new ArrayList<Integer>(optimumDays));
		}
		*/
		session.close();
		
		return periodsAmount;
	}
	
	public ArrayList<Integer> getOptimumConditionsDays(){
		ArrayList<Integer> optimumDays= new ArrayList<Integer>();
		
		Session session = HibernateUtil.getOpenedSession();
		session.beginTransaction();
		Query query = session.createQuery("from Galaxy.droughtDays");
		int pageIndex=0;
		int pageResults=100;
		
		query.setFirstResult(pageIndex*pageResults);
		query.setMaxResults(pageResults);
		List<Integer> optimumDaysPage = (List<Integer>) query.list();
		session.getTransaction().commit();
		optimumDays.addAll(optimumDaysPage);
		while (optimumDaysPage.size()==pageResults){
			pageIndex++;
			query.setFirstResult(pageIndex*pageResults);
			optimumDaysPage = (List<Integer>) query.list();
			session.getTransaction().commit();
			optimumDays.addAll(optimumDaysPage);
		}
		
		session.close();
		
		return optimumDays;
	}
	
}

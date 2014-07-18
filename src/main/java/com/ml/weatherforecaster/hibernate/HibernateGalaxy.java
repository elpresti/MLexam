package com.ml.weatherforecaster.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ml.weatherforecaster.model.Galaxy;
import com.ml.weatherforecaster.util.GalaxyUtil;
import com.ml.weatherforecaster.model.RainyDay;

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
	
	
	public int getOptimumConditionsPeriods(){
		int periodsAmount=0;
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
			if (galaxy != null  &&  galaxy.getDroughtDays() != null){
				periodsAmount = GalaxyUtil.getInstance().getNumberOfPeriods(new ArrayList<Integer>(galaxy.getOptimumConditionsDays()));
				tx.commit();
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return periodsAmount;
	}
	
	public ArrayList<Integer> getOptimumConditionsDays(){
		ArrayList<Integer> optimumDays= new ArrayList<Integer>();
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			Galaxy galaxy = (Galaxy) session.get(Galaxy.class,1);
			tx.commit();
			if (galaxy != null  &&  galaxy.getOptimumConditionsDays() != null  && 
					galaxy.getOptimumConditionsDays().size()>0){
				optimumDays = new ArrayList<Integer>(galaxy.getOptimumConditionsDays());
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return optimumDays;
	}
	
	public int getDroughtDaysPeriods(){
		int periodsAmount=0;
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
			if (galaxy != null  &&  galaxy.getDroughtDays() != null){
				periodsAmount = GalaxyUtil.getInstance().getNumberOfPeriods(new ArrayList<Integer>(galaxy.getDroughtDays()));
				tx.commit();
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return periodsAmount;
	}
	
	public ArrayList<Integer> getDroughtDays(){
		ArrayList<Integer> droughtDays= new ArrayList<Integer>();
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
	
			Galaxy galaxy = (Galaxy) session.get(Galaxy.class,1);
			tx.commit();
			if (galaxy != null  &&  galaxy.getDroughtDays() != null  && 
					galaxy.getDroughtDays().size()>0){
				droughtDays = new ArrayList<Integer>(galaxy.getDroughtDays());
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return droughtDays;
	}

	public int getRainyDaysPeriods(){
		int periodsAmount=0;
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
			if (galaxy != null  &&  galaxy.getRainyDays() != null){
				ArrayList<Integer> rainyDayNumbers = new ArrayList<Integer>();
				for (RainyDay rainyDay : galaxy.getRainyDays()){
					rainyDayNumbers.add(rainyDay.getDayNumber());
				}
				periodsAmount = GalaxyUtil.getInstance().getNumberOfPeriods(rainyDayNumbers);
				tx.commit();
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return periodsAmount;
	}

	public int getTodayDayNumber(){
		int todayDayNumber=0;
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
			if (galaxy != null  &&  galaxy.getDayNumber() > 0){
				todayDayNumber = galaxy.getDayNumber();
				tx.commit();
			}
			session.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return todayDayNumber;
	}

	
	public ArrayList<Integer> getRainyDays(){
		ArrayList<Integer> rainyDayNumbers= new ArrayList<Integer>();
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy) session.get(Galaxy.class,1);
			tx.commit();
			if (galaxy != null  &&  galaxy.getRainyDays() != null  && 
					galaxy.getRainyDays().size()>0){
				for (RainyDay rainyDay : galaxy.getRainyDays()){
					rainyDayNumbers.add(rainyDay.getDayNumber());
				}
			}
			session.close();
		}catch(Exception e){
			System.err.println(e.toString());
		}
		return rainyDayNumbers;
	}
	
	public ArrayList<RainyDay> getMostRainyDays(){
		ArrayList<RainyDay> mostRainyDays = new ArrayList<RainyDay>();
		try{
			Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Galaxy galaxy = (Galaxy) session.get(Galaxy.class,1);
			tx.commit();
			if (galaxy != null  &&  galaxy.getMaxTrianglePerimeter() != null ){
				double maxTrianglePerimeter = galaxy.getMaxTrianglePerimeter().getTrianglePerimeter();
				for (RainyDay rainyDay : galaxy.getRainyDays()){
					if (rainyDay.getTrianglePerimeter()  ==  maxTrianglePerimeter ){
						mostRainyDays.add(rainyDay);
					}
				}
			}
			session.close();
		}catch(Exception e){
			System.err.println(e.toString());
		}
		return mostRainyDays;
	}

	public String getWeatherOfAday(int dayNumber) {
		String weather="UNKNOWN";
		if (this.getRainyDays().contains(dayNumber)){
			weather="Rainy";
		}else{
			if (this.getOptimumConditionsDays().contains(dayNumber)){
				weather="Optimum Weather Conditions";
			}else{
				if (this.getDroughtDays().contains(dayNumber)){
					weather="Drought";
				}else{
					weather="UNKNOWN weather conditions! (but: it's NOT a rainy day, NOT an Optimum Weather Conditions day and NOT a Drought day)";
				}
			}
		}
		return weather;
	}
	
}

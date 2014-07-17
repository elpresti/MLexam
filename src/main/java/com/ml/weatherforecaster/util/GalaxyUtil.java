package com.ml.weatherforecaster.util;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ml.weatherforecaster.hibernate.HibernateUtil;
import com.ml.weatherforecaster.model.Galaxy;
import com.ml.weatherforecaster.model.RainyDay;
import com.ml.weatherforecaster.model.Planet;

public class GalaxyUtil {
	private Galaxy galaxy;
	private String statusTxt="";
	
	private static GalaxyUtil uniqueInstance;

	private GalaxyUtil() {
	}

	public static GalaxyUtil getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new GalaxyUtil();
		}
		return uniqueInstance;
	}
	
	public boolean advanceDaysInGalaxy(int daysQuantity){
		boolean canDoIt=false;
		Logger logger = Logger.getLogger("API_Logger");
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			session.save(getGalaxy());

			for (int dayIndex=0; dayIndex<daysQuantity; dayIndex++){
				getGalaxy().advanceAday();
				setStatusTxt(getStatusTxt()+"<br> <br> Day number: "+getGalaxy().getDayNumber() );
				setStatusTxt(getStatusTxt()+"<br> Planet "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(0).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(0).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(0).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(0).getyCoordinate()+" km");
				
				setStatusTxt(getStatusTxt()+"<br> Planet "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(1).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(1).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(1).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(1).getyCoordinate()+" km");
				
				setStatusTxt(getStatusTxt()+"<br> Planet "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(2).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(2).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(2).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+new ArrayList<Planet>(getGalaxy().getPlanets()).get(2).getyCoordinate()+" km");
			}
			tx.commit();
			session.close();
			logger.info(daysQuantity + " days gone in the galaxy, planets info saved on DB! Today is day number: "+getGalaxy().getDayNumber());
			canDoIt=true;
		}catch (Exception e) {
			System.out.println("EXEPTION!--> in advanceDaysInGalaxy(): "+e.toString());
			logger.warning(e.toString());
		}
		return canDoIt;
	}

	public Galaxy getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
	}
	
	public void estimateWeatherStoreResultsAndShow(int nextDaysToEstimate){
		if (getGalaxy() == null){
			try{
				Session session = HibernateUtil.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				Galaxy galaxy = (Galaxy)session.get(Galaxy.class, 1);
				tx.commit();
				session.close();
				if (galaxy != null){
					setGalaxy(galaxy);
				}else{
					setGalaxy(new Galaxy());
					getGalaxy().createGalaxy();
				}
			}catch(Exception e){
				System.out.println(e.toString());
			}
		}
		setStatusTxt("");
		advanceDaysInGalaxy(nextDaysToEstimate);
		
		//print summary of stats
		if (getGalaxy().getDroughtDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> List of drought days founded:");
			for (int i=0; i<getGalaxy().getDroughtDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+new ArrayList<Integer>(getGalaxy().getDroughtDays()).get(i) );
			}
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> There where no drought days");
		}
		
		if (getGalaxy().getOptimumConditionsDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> "+getGalaxy().getOptimumConditionsDays().size()+" Optimum Conditions days founded:");
			for (int i=0; i<getGalaxy().getOptimumConditionsDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+new ArrayList<Integer>(getGalaxy().getOptimumConditionsDays()).get(i) );
			}
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> There where no Optimum Conditions Days");
		}
		
		if (getGalaxy().getRainyDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> "+getGalaxy().getRainyDays().size()+" Rainy Days founded:");
			for (int i=0; i<getGalaxy().getRainyDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+new ArrayList<RainyDay>(getGalaxy().getRainyDays()).get(i).getDayNumber() );
				setStatusTxt(getStatusTxt()+"<br> - Triangle perimeter: "+new ArrayList<RainyDay>(getGalaxy().getRainyDays()).get(i).getTrianglePerimeter() );
			}
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> There where no Rainy Days");
		}
		
		if (getGalaxy().getMaxTrianglePerimeter() !=  null ){
			setStatusTxt(getStatusTxt()+"<br><br> The most rainy day was the day number: "+getGalaxy().getMaxTrianglePerimeter().getDayNumber());
			setStatusTxt(getStatusTxt()+", with a triangle perimeter of: "+getGalaxy().getMaxTrianglePerimeter().getTrianglePerimeter());
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> So, there where no max triangle perimeter");
		}
		
	}

	public int getNumberOfPeriods(ArrayList<Integer> numbers){
		int periods=0;
		if (numbers!= null  &&  numbers.size()>0){
			periods++;
			int lastNumber=numbers.get(0);
			for (int i=1;i<numbers.size();i++){
				if (lastNumber+1  !=  numbers.get(i)){
					periods++;
				}
				lastNumber=numbers.get(i);
			}
		}
		
		return periods;
	}
	
	public String getStatusTxt() {
		return statusTxt;
	}

	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}
	
	
}


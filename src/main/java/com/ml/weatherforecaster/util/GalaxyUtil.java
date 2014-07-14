package com.ml.weatherforecaster.util;

import com.ml.weatherforecaster.model.Galaxy;
import com.ml.weatherforecaster.model.Planet;

public class GalaxyUtil {
	private Galaxy galaxy;
	private String statusTxt="";
	
	public boolean advanceDaysInGalaxy(int daysQuantity){
		boolean sePudo=false;
		try{
			for (int diaActual=0; diaActual<daysQuantity; diaActual++){
				getGalaxy().advanceAday();
				setStatusTxt(getStatusTxt()+"<br> <br> Day number: "+getGalaxy().getDayNumber() );
				setStatusTxt(getStatusTxt()+"<br> Planet "+getGalaxy().getPlanets().get(0).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+getGalaxy().getPlanets().get(0).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+getGalaxy().getPlanets().get(0).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+getGalaxy().getPlanets().get(0).getyCoordinate()+" km");
				
				setStatusTxt(getStatusTxt()+"<br> Planet "+getGalaxy().getPlanets().get(1).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+getGalaxy().getPlanets().get(1).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+getGalaxy().getPlanets().get(1).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+getGalaxy().getPlanets().get(1).getyCoordinate()+" km");
				
				setStatusTxt(getStatusTxt()+"<br> Planet "+getGalaxy().getPlanets().get(2).getCivilizationName()+"= ");
				setStatusTxt(getStatusTxt()+"Orientation: "+getGalaxy().getPlanets().get(2).getOrientation()+"°, ");
				setStatusTxt(getStatusTxt()+"X: "+getGalaxy().getPlanets().get(2).getxCoordinate()+" km, ");
				setStatusTxt(getStatusTxt()+"Y: "+getGalaxy().getPlanets().get(2).getyCoordinate()+" km");
			}
			sePudo=true;
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return sePudo;
	}

	public Galaxy getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(Galaxy galaxy) {
		this.galaxy = galaxy;
	}
	
	public void doAll(){
		setGalaxy(new Galaxy());
		getGalaxy().createGalaxy();
		advanceDaysInGalaxy(400);
		
		//print summary of stats
		if (getGalaxy().getDroughtDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> List of drought days founded:");
			for (int i=0; i<getGalaxy().getDroughtDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+getGalaxy().getDroughtDays().get(i) );
			}			
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> There where no drought days");
		}
		
		if (getGalaxy().getOptimumConditionsDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> List of Optimum Conditions days founded:");
			for (int i=0; i<getGalaxy().getOptimumConditionsDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+getGalaxy().getOptimumConditionsDays().get(i) );
			}
		}else{
			setStatusTxt(getStatusTxt()+"<br><br> There where no Optimum Conditions Days");
		}
		
		if (getGalaxy().getRainyDays().size() > 0 ){
			setStatusTxt(getStatusTxt()+"<br><br> List of Rainy Days founded:");
			for (int i=0; i<getGalaxy().getRainyDays().size(); i++){
				setStatusTxt(getStatusTxt()+"<br> - Day number: "+getGalaxy().getRainyDays().get(i).getDayNumber() );
				setStatusTxt(getStatusTxt()+"<br> - Triangle perimeter: "+getGalaxy().getRainyDays().get(i).getTrianglePerimeter() );
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

	public String getStatusTxt() {
		return statusTxt;
	}

	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}
	
	
}


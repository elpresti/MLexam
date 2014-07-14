package com.ml.weatherforecaster.util;

import com.ml.weatherforecaster.model.Galaxy;
import com.ml.weatherforecaster.model.Planet;

public class GalaxyUtil {
	private Galaxy galaxy;
	
	
	public boolean advanceDaysInGalaxy(int daysQuantity){
		boolean sePudo=false;
		try{
			for (int diaActual=0; diaActual<daysQuantity; diaActual++){
				getGalaxy().advanceAday();
				System.out.println("\n Day number: "+getGalaxy().getDayNumber() );
				System.out.println("Planet "+getGalaxy().getPlanets().get(0).getCivilizationName()+"= ");
				System.out.print("Orientation: "+getGalaxy().getPlanets().get(0).getOrientation()+"°, ");
				System.out.print("X: "+getGalaxy().getPlanets().get(0).getxCoordinate()+"km, ");
				System.out.print("Y: "+getGalaxy().getPlanets().get(0).getyCoordinate()+"km");
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
		advanceDaysInGalaxy(50);
		
		//print summary of stats
		if (getGalaxy().getDroughtDays().size() > 0 ){
			System.out.println("List of drought days founded:");
			for (int i=0; i<getGalaxy().getDroughtDays().size(); i++){
				System.out.println("- Day number: "+getGalaxy().getDroughtDays().get(i) );
			}			
		}else{
			System.out.println("There where no drought days");
		}
		
		if (getGalaxy().getOptimumConditionsDays().size() > 0 ){
			System.out.println("List of Optimum Conditions days founded:");
			for (int i=0; i<getGalaxy().getOptimumConditionsDays().size(); i++){
				System.out.println("- Day number: "+getGalaxy().getOptimumConditionsDays().get(i) );
			}
		}else{
			System.out.println("There where no Optimum Conditions Days");
		}
		
		if (getGalaxy().getRainyDays().size() > 0 ){
			System.out.println("List of Rainy Days founded:");
			for (int i=0; i<getGalaxy().getRainyDays().size(); i++){
				System.out.println("- Day number: "+getGalaxy().getRainyDays().get(i).getDayNumber() );
				System.out.println("- Triangle perimeter: "+getGalaxy().getRainyDays().get(i).getTrianglePerimeter() );
			}
		}else{
			System.out.println("There where no Rainy Days");
		}
		
		if (getGalaxy().getMaxTrianglePerimeter() !=  null ){
			System.out.println("The most rainy day was the day number: "+getGalaxy().getMaxTrianglePerimeter().getDayNumber());
			System.out.print(", with a triangle perimeter of: "+getGalaxy().getMaxTrianglePerimeter().getTrianglePerimeter());
		}else{
			System.out.println("So, there where no max triangle perimeter");
		}
		
	}
	
}


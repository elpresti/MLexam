package com.ml.weatherforecaster.model;

public class Planet {
	private String civilizationName;
	private int distanceToSun; // [km]
	private int angularSpeed;  // [degrees/day]
	private boolean clockWise; // rotation direction
	
	public int getPosition(int dayNumber){
		int planetPosition=-1;
		if ( isAvalidDay(dayNumber)  &&  getAngularSpeed()  ){
			//to be continued
		}
		return planetPosition;
	}
	
	public boolean isAvalidDay(int dayNumber){
		boolean isValid=false;
		if ( (dayNumber > 0) && (dayNumber < (365*10)) ){
			isValid=true;
		}
		return isValid;
	}

	
	public String getCivilizationName() {
		return civilizationName;
	}

	public void setCivilizationName(String civilizationName) {
		this.civilizationName = civilizationName;
	}

	public int getDistanceToSun() {
		return distanceToSun;
	}

	public void setDistanceToSun(int distanceToSun) {
		this.distanceToSun = distanceToSun;
	}

	public int getAngularSpeed() {
		return angularSpeed;
	}

	public void setAngularSpeed(int angularSpeed) {
		this.angularSpeed = angularSpeed;
	}

	public boolean isClockWise() {
		return clockWise;
	}

	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}
	
}

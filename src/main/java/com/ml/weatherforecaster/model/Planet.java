package com.ml.weatherforecaster.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PLANETS")
public class Planet {
	@Id @GeneratedValue 
	private int planetId;
	private String civilizationName;
	private int distanceToSun; // [km]
	private int angularSpeed;  // [degrees/day]
	private boolean clockWise; // rotation direction
	private int orientation; // orientation to the sun
	private double xCoordinate; // value of X coordinate from orientation, on a 2D plane
	private double yCoordinate; // value of Y coordinate from orientation, on a 2D plane

	public Planet(String civilizationName, int distanceToSun, int angularSpeed, boolean clockWise, int orientation){
		setOrientation(orientation);
		setAngularSpeed(angularSpeed);
		setDistanceToSun(distanceToSun);
		setCivilizationName(civilizationName);
		setClockWise(clockWise);
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

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
		updateXYcoordinates();
	}
	
	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public void advanceAday(){
		if ( isClockWise() ){
			int newOrientation = getOrientation();
			newOrientation = newOrientation + getAngularSpeed();
			if (newOrientation >= 360){
				setOrientation(newOrientation - 360);
			}else{
				setOrientation(newOrientation);
			}
		}else{
			int newOrientation = getOrientation();
			newOrientation = newOrientation - getAngularSpeed();
			if (newOrientation < 0){
				setOrientation(newOrientation + 360);
			}else{
				setOrientation(newOrientation);
			}
		}
	}
	
	private double getAdjacentValueOfRightTriangleFormed(int angle){
		double value = 0;
		double radiansAngle = Math.toRadians(angle);
		value = Math.cos(radiansAngle) * getDistanceToSun();
		return value; //[km]
	}
	
	private double getOppositeValueOfRightTriangleFormed(int angle){
		double value = 0;
		double radiansAngle = Math.toRadians(angle);
		value = Math.sin(radiansAngle) * getDistanceToSun();
		return value; //[km]
	}
	
	private void updateXYcoordinates(){
		int LIMIT_1ST_QUADRANT=90; //1ST: OPP= +Y, ADJ= +X 
		int LIMIT_2ND_QUADRANT=180; //2ND: OPP= +X, ADJ= -Y
		int LIMIT_3TH_QUADRANT=270; //3TH: OPP= -Y, ADJ= -X
		int LIMIT_4TH_QUADRANT=360; //4TH: OPP= -X, ADJ= +Y
		if ( (getOrientation()>=0) && (getOrientation()<LIMIT_1ST_QUADRANT) ){
			setyCoordinate( (getOppositeValueOfRightTriangleFormed( LIMIT_1ST_QUADRANT - getOrientation())) );
			setxCoordinate( (getAdjacentValueOfRightTriangleFormed( LIMIT_1ST_QUADRANT - getOrientation())) );
		}else{
			if ( (getOrientation()>=LIMIT_1ST_QUADRANT) && (getOrientation()<LIMIT_2ND_QUADRANT) ){
				setxCoordinate( (getOppositeValueOfRightTriangleFormed( LIMIT_2ND_QUADRANT - getOrientation())) );
				setyCoordinate( (getAdjacentValueOfRightTriangleFormed( LIMIT_2ND_QUADRANT - getOrientation())) * (-1) );
			}else{
				if ( (getOrientation()>=LIMIT_2ND_QUADRANT) && (getOrientation()<LIMIT_3TH_QUADRANT) ){
					setyCoordinate( (getOppositeValueOfRightTriangleFormed( LIMIT_3TH_QUADRANT - getOrientation())) * (-1) );
					setxCoordinate( (getAdjacentValueOfRightTriangleFormed( LIMIT_3TH_QUADRANT - getOrientation())) * (-1) );
				}else{
					if ( (getOrientation()>=LIMIT_3TH_QUADRANT) && (getOrientation()<LIMIT_4TH_QUADRANT) ){
						setxCoordinate( (getOppositeValueOfRightTriangleFormed( LIMIT_4TH_QUADRANT - getOrientation())) * (-1) );
						setyCoordinate( (getAdjacentValueOfRightTriangleFormed( LIMIT_4TH_QUADRANT - getOrientation())) );
					}
				}
			}
		}
	}
	
}

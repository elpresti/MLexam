package com.ml.weatherforecaster.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name="RAINY_DAYS")
public class RainyDay {
	@Id @GeneratedValue
	private int rainyDayId;
	private int dayNumber;
	private double trianglePerimeter;
	
	public RainyDay(){
		
	}
	
	public void initialize(int dayNumber,double tringlePerimeter){
		setDayNumber(dayNumber);
		setTrianglePerimeter(tringlePerimeter);
	}
	
	public int getRainyDayId() {
		return rainyDayId;
	}
	public void setRainyDayId(int rainyDayId) {
		this.rainyDayId = rainyDayId;
	}
	public int getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}
	public double getTrianglePerimeter() {
		return trianglePerimeter;
	}
	public void setTrianglePerimeter(double trianglePerimeter) {
		this.trianglePerimeter = trianglePerimeter;
	}
	
}

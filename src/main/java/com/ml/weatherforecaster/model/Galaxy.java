package com.ml.weatherforecaster.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name="GALAXYS")
public class Galaxy {
    @OneToMany (cascade=CascadeType.ALL)
    private ArrayList<Planet> planets = new ArrayList();
    
    private Map<Integer,Integer> maxTrianglePerimeter;
    
    @ElementCollection
    @JoinTable(name="DROUGHTDAYS")
    @GenericGenerator(name="hilo-gen", strategy="hilo")
    @CollectionId(columns = { @Column(name="DAY_ID") }, generator = "hilo-gen", type = @Type(type="long"))
    private Collection<Integer> droughtDays = new ArrayList();
    
    
    public void createGalaxy(){
    	getPlanets().add(new Planet("Ferengi",500,1,true,0));
    	getPlanets().add(new Planet("Betasoide",2000,3,true,0));
    	getPlanets().add(new Planet("Vulcano",1000,5,false,0));
    }
    
    public void advanceAday(){
    	//TO DO
    }
    
    public boolean areAlignedWithTheSun(){
    	boolean alignedWithSun = false;
    	int posF = getPlanets().get(0).getOrientation();
    	int posB = getPlanets().get(1).getOrientation();
    	int posV = getPlanets().get(2).getOrientation();
    	/*
			1. posF = posB = posV
			2. posF = posB = (posV+180)<360
			3. posF = (posB+180)<360 = posV
			4. (posF+180)<360 = posB = posV
		*/
		if ( (posF == posB) && (posB == posV) ) {
			alignedWithSun = true;
		}else{
			if (posF == posB) {
				if ( isAlignedInOpposite(posF,posV) ) {
					alignedWithSun = true;
				}
			}else{
				if (posF == posV) {
					if ( isAlignedInOpposite(posF,posB) ) {
						alignedWithSun = true;
					}
				}else{
					if (posB == posV){
						if ( isAlignedInOpposite(posB, posF) ){
							alignedWithSun = true;
						}
					}
				}
			}
		}
		return alignedWithSun;
    } 

    public boolean alignedBetweenThem(){
    	// Condition when three points are aligned:
    	// (X1-X0)/(X2-X1) = (Y1-Y0)/(Y2-Y1)
    	boolean areAligned=false;    	
    	double firstMember = getPlanets().get(1).getxCoordinate() - getPlanets().get(0).getxCoordinate();
    	firstMember = firstMember / (getPlanets().get(2).getxCoordinate() - getPlanets().get(1).getxCoordinate());
    	double secondMember = getPlanets().get(1).getyCoordinate() - getPlanets().get(0).getyCoordinate();
    	secondMember = secondMember / (getPlanets().get(2).getyCoordinate() - getPlanets().get(1).getyCoordinate());
    	if (firstMember == secondMember){
    		areAligned=true;
    	}
    	return areAligned;
    }
    
    public boolean isSunInPlanetsTriangle(){
    	//Need to estimate if a point is inside the triangle or not
    	//1.  Get Triangle Orientation
    	//2.a If triangle orientation is positive, the point is inside the triangle only if the orientation of all the 
    	//		triangles that have the point as a vertex, are positive. Other way, the point is outside the triangle.
    	//2.b If triangle orientation is negative, the point is inside the triangle only if the orientation of all the 
    	//		triangles that have the point as a vertex, are negative. Other way, the point is outside the triangle.
    	// NOTE: Sun coordinates (0,0)
    	boolean sunInsideTriangle=false;
    	if (isTriangleOrientationPositive(getPlanets().get(2).getxCoordinate(), getPlanets().get(2).getyCoordinate(), 
    			getPlanets().get(1).getxCoordinate(), getPlanets().get(1).getyCoordinate(), 
    			getPlanets().get(0).getxCoordinate(), getPlanets().get(0).getyCoordinate())){
    		if (isTriangleOrientationPositive(getPlanets().get(0).getxCoordinate(), getPlanets().get(0).getyCoordinate(), 
    			getPlanets().get(1).getxCoordinate(), getPlanets().get(1).getyCoordinate(), 
    			0, 0)){
    			if (isTriangleOrientationPositive(getPlanets().get(1).getxCoordinate(), getPlanets().get(1).getyCoordinate(), 
    	    			getPlanets().get(2).getxCoordinate(), getPlanets().get(2).getyCoordinate(), 
    	    			0, 0)){
    				if (isTriangleOrientationPositive(getPlanets().get(2).getxCoordinate(), getPlanets().get(2).getyCoordinate(), 
        	    			getPlanets().get(0).getxCoordinate(), getPlanets().get(0).getyCoordinate(), 
        	    			0, 0)){
    					sunInsideTriangle=true;
        			}
    			}
    		}
    	}else{
    		if (!(isTriangleOrientationPositive(getPlanets().get(0).getxCoordinate(), getPlanets().get(0).getyCoordinate(), 
        			getPlanets().get(1).getxCoordinate(), getPlanets().get(1).getyCoordinate(), 
        			0, 0))){
        			if (!(isTriangleOrientationPositive(getPlanets().get(1).getxCoordinate(), getPlanets().get(1).getyCoordinate(), 
        	    			getPlanets().get(2).getxCoordinate(), getPlanets().get(2).getyCoordinate(), 
        	    			0, 0))){
        				if (!(isTriangleOrientationPositive(getPlanets().get(2).getxCoordinate(), getPlanets().get(2).getyCoordinate(), 
            	    			getPlanets().get(0).getxCoordinate(), getPlanets().get(0).getyCoordinate(), 
            	    			0, 0))){
        					sunInsideTriangle=true;
            			}
        			}
        	}
    	}
    	return sunInsideTriangle;
    }
    
    private boolean isTriangleOrientationPositive(double x2, double y2, double x1, double y1, double x0, double y0){
    	double orientation = (x0 - x2) * (y1 - y2) - (y0 - y2) * (x1 - x2);
    	return (orientation >= 0);
    }
    
    public double getTrianglePerimeter(){
    	// Perimeter of a triangle = dAB + dAC + dBC
    	// Distance between two points (d) = SQR( (X1-X0)^2 + (Y1-Y0)^2 )
    	double perimeter=0;
    	perimeter += Math.sqrt( Math.pow(getPlanets().get(1).getxCoordinate() - getPlanets().get(0).getxCoordinate(),2) + 
    			Math.pow(getPlanets().get(1).getyCoordinate() - getPlanets().get(0).getyCoordinate(),2) );
    	perimeter += Math.sqrt( Math.pow(getPlanets().get(2).getxCoordinate() - getPlanets().get(0).getxCoordinate(),2) + 
    			Math.pow(getPlanets().get(2).getyCoordinate() - getPlanets().get(0).getyCoordinate(),2) );
    	perimeter += Math.sqrt( Math.pow(getPlanets().get(2).getxCoordinate() - getPlanets().get(1).getxCoordinate(),2) + 
    			Math.pow(getPlanets().get(2).getyCoordinate() - getPlanets().get(1).getyCoordinate(),2) );
    	return perimeter;
    }
    
    public boolean isAlignedInOpposite(int posA, int posB){
    	boolean isIt = false;
		int tmp = posB + 180;
		if (tmp >= 360){
			tmp = tmp - 360;
		}
		if (posA == tmp ) {
			isIt = true;
		}
		return isIt;
    }
    
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}

	public Map<Integer, Integer> getMaxTrianglePerimeter() {
		return maxTrianglePerimeter;
	}

	public void setMaxTrianglePerimeter(Map<Integer, Integer> maxTrianglePerimeter) {
		this.maxTrianglePerimeter = maxTrianglePerimeter;
	}

	public Collection<Integer> getDroughtDays() {
		return droughtDays;
	}

	public void setDroughtDays(Collection<Integer> droughtDays) {
		this.droughtDays = droughtDays;
	}
    
}

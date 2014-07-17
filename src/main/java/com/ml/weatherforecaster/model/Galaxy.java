package com.ml.weatherforecaster.model;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name="GALAXYS")
public class Galaxy {
	@Id @GeneratedValue
	private int galaxyId;
	
	private int dayNumber; //today
	
    @OneToMany (cascade=CascadeType.ALL)
    private Collection<Planet> planets = new ArrayList();
    
    @OneToOne (cascade=CascadeType.ALL)
    private RainyDay maxTrianglePerimeter;
    
    @OneToMany (cascade=CascadeType.ALL)
    private Collection<RainyDay> rainyDays = new ArrayList();
    
    @ElementCollection
    @JoinTable(name="DROUGHT_DAYS")
    @GenericGenerator(name="hilo-gen", strategy="hilo")
    @CollectionId(columns = { @Column(name="DAY_ID") }, generator = "hilo-gen", type = @Type(type="long"))
    private Collection<Integer> droughtDays = new ArrayList();
    
    @ElementCollection
    @JoinTable(name="OPTIMUM_DAYS")
    @GenericGenerator(name="hilo-gen", strategy="hilo")
    @CollectionId(columns = { @Column(name="DAY_ID") }, generator = "hilo-gen", type = @Type(type="long"))
    private Collection<Integer> optimumConditionsDays = new ArrayList();
    
    
    public void createGalaxy(){
    	Planet pFerengi = new Planet();
    	pFerengi.initialize("Ferengi",500,1,true,0);
    	Planet pBetasoide = new Planet();
    	pBetasoide.initialize("Betasoide",2000,3,true,0);
    	Planet pVulcano = new Planet();
    	pVulcano.initialize("Vulcano",1000,5,false,0);
    	getPlanets().add(pFerengi);
    	getPlanets().add(pBetasoide);
    	getPlanets().add(pVulcano);
    	setMaxTrianglePerimeter(null);
    }
    
    public void advanceAday(){
    	try{
    		new ArrayList<Planet>(getPlanets()).get(0).advanceAday();
        	new ArrayList<Planet>(getPlanets()).get(1).advanceAday();
        	new ArrayList<Planet>(getPlanets()).get(2).advanceAday();
        	setDayNumber(getDayNumber()+1);
        	analyzeCurrentPlanetsPositions();
    	}catch(Exception e){
    		System.out.println("EXCEPTION on Galaxy.advanceAday(): "+e.toString());
    	}
    }
    
    public void analyzeCurrentPlanetsPositions(){
    	if (areAlignedWithTheSun()){
    		getDroughtDays().add(getDayNumber());
    	}else{
    		if (isSunInPlanetsTriangle()){
    			RainyDay rainyDay = new RainyDay();
    			rainyDay.initialize(getDayNumber(),calculateTrianglePerimeter());
    			getRainyDays().add(rainyDay);
    			if ( (getMaxTrianglePerimeter() == null) || (getMaxTrianglePerimeter().getTrianglePerimeter() < rainyDay.getTrianglePerimeter()) ) {
    				setMaxTrianglePerimeter(rainyDay);
    			}
    		}else{
    			if (areAlignedBetweenThemButNotWithSun()){
    				getOptimumConditionsDays().add(getDayNumber());
    			}
    		}
    	}
    }
    
    public boolean areAlignedWithTheSun(){
    	/*	Are aligned with the sun when:
			1. posF = posB = posV
			2. posF = posB = (posV+180)<360
			3. posF = (posB+180)<360 = posV
			4. (posF+180)<360 = posB = posV
		*/
    	boolean alignedWithSun = false;
    	int posF = new ArrayList<Planet>(getPlanets()).get(0).getOrientation();
    	int posB = new ArrayList<Planet>(getPlanets()).get(1).getOrientation();
    	int posV = new ArrayList<Planet>(getPlanets()).get(2).getOrientation();
    	
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

    public boolean areAlignedBetweenThemButNotWithSun(){ //(using precision < 0.1km) 
    	// Condition when three points are aligned:
    	// (X1-X0)/(X2-X1) = (Y1-Y0)/(Y2-Y1)
    	boolean areAligned=false;    	
    	double firstMember = new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getxCoordinate();
    	firstMember = firstMember / (new ArrayList<Planet>(getPlanets()).get(2).getxCoordinate() - new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate());
    	double secondMember = new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getyCoordinate();
    	secondMember = secondMember / (new ArrayList<Planet>(getPlanets()).get(2).getyCoordinate() - new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate());
    	if ( (((firstMember - secondMember)>=0)  &&  ((firstMember - secondMember) < 0.1))  || 
    			( ((secondMember - firstMember)>=0)  &&  ((secondMember - firstMember) < 0.1)) ){
    		areAligned=true;
    	}
    	return areAligned;
    }
    
    public boolean areAlignedBetweenThemButNotWithSun_morePrecision(){ //(Using BigDecimal) not working!
    	// Condition when three points are aligned:
    	// (X1-X0)/(X2-X1) = (Y1-Y0)/(Y2-Y1)
    	boolean areAligned=false;
    	try{
    		BigDecimal firstMemberPart1 = BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate()).subtract(BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(0).getxCoordinate()));
        	BigDecimal firstMemberPart2 = BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(2).getxCoordinate()).subtract(BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate()));
        	BigDecimal firstMember = firstMemberPart1.divide(firstMemberPart2);

        	BigDecimal secondMemberPart1 = BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate()).subtract(BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(0).getyCoordinate()));
        	BigDecimal secondMemberPart2 = BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(2).getyCoordinate()).subtract(BigDecimal.valueOf(new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate()));
        	BigDecimal secondMember = secondMemberPart1.divide(secondMemberPart2);
        	int firstCompare = firstMember.subtract(secondMember).compareTo(BigDecimal.valueOf(0));
        	int secondCompare = secondMember.subtract(firstMember).compareTo(BigDecimal.valueOf(0));
        	
        	if ( (firstCompare == 0)  ||  (secondCompare == 0) ){
        		areAligned=true;
        	}	
    	}catch(Exception e){
    		System.out.println(e.toString());
    	}
    	return areAligned;
    }
        
    private double triangleOrientation(double x1, double y1, double x2, double y2, double x3, double y3){
    	//area of the triangle P1P2P3, return > 0 if it's positive oriented, return < 0 in other case
    	return (x1 - x3)*(y2 - y3)-(y1 - y3)*(x2 - x3);
    }

    public boolean isSunInPlanetsTriangle() {
    	//Need to estimate if a point is inside the triangle or not, calculating the triangle orientation using the sun as a vertex
    	boolean sunInsideTriangle= false;
    	double x1 = new ArrayList<Planet>(getPlanets()).get(0).getxCoordinate();
    	double x2 = new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate();
    	double x3 = new ArrayList<Planet>(getPlanets()).get(2).getxCoordinate();

    	double y1 = new ArrayList<Planet>(getPlanets()).get(0).getyCoordinate();
    	double y2 = new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate();
    	double y3 = new ArrayList<Planet>(getPlanets()).get(2).getyCoordinate();
    	
    	double x4 = 0; //Sun Coordinates
    	double y4 = 0;
 
        if(triangleOrientation(x1,y1,x2,y2,x3,y3)>=0){
        	 sunInsideTriangle = (triangleOrientation(x1,y1,x2,y2,x4,y4) >= 0)  &&  (triangleOrientation(x2,y2,x3,y3,x4,y4) >= 0)  &&  (triangleOrientation(x3,y3,x1,y1,x4,y4) >= 0);
        }else{
        	 sunInsideTriangle = (triangleOrientation(x1,y1,x2,y2,x4,y4) <= 0)  &&  (triangleOrientation(x2,y2,x3,y3,x4,y4) <= 0)  &&  (triangleOrientation(x3,y3,x1,y1,x4,y4) <= 0);  
        }
        return sunInsideTriangle;
    }
    
    public double calculateTrianglePerimeter(){
    	// Perimeter of a triangle = dAB + dAC + dBC
    	// Distance between two points (d) = SQR( (X1-X0)^2 + (Y1-Y0)^2 )
    	double perimeter=0;
    	perimeter += Math.sqrt( Math.pow(new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getxCoordinate(),2) + 
    			Math.pow(new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getyCoordinate(),2) );
    	perimeter += Math.sqrt( Math.pow(new ArrayList<Planet>(getPlanets()).get(2).getxCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getxCoordinate(),2) + 
    			Math.pow(new ArrayList<Planet>(getPlanets()).get(2).getyCoordinate() - new ArrayList<Planet>(getPlanets()).get(0).getyCoordinate(),2) );
    	perimeter += Math.sqrt( Math.pow(new ArrayList<Planet>(getPlanets()).get(2).getxCoordinate() - new ArrayList<Planet>(getPlanets()).get(1).getxCoordinate(),2) + 
    			Math.pow(new ArrayList<Planet>(getPlanets()).get(2).getyCoordinate() - new ArrayList<Planet>(getPlanets()).get(1).getyCoordinate(),2) );
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
    
	public Collection<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(Collection<Planet> planets) {
		this.planets = planets;
	}

	public RainyDay getMaxTrianglePerimeter() {
		return maxTrianglePerimeter;
	}

	public void setMaxTrianglePerimeter(RainyDay maxTrianglePerimeter) {
		this.maxTrianglePerimeter = maxTrianglePerimeter;
	}

	public Collection<Integer> getDroughtDays() {
		return droughtDays;
	}

	public void setDroughtDays(Collection<Integer> droughtDays) {
		this.droughtDays = droughtDays;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Collection<RainyDay> getRainyDays() {
		return rainyDays;
	}

	public void setRainyDays(Collection<RainyDay> rainyDays) {
		this.rainyDays = rainyDays;
	}

	public Collection<Integer> getOptimumConditionsDays() {
		return optimumConditionsDays;
	}

	public void setOptimumConditionsDays(Collection<Integer> optimumConditionsDays) {
		this.optimumConditionsDays = optimumConditionsDays;
	}

	public int getGalaxyId() {
		return galaxyId;
	}

	public void setGalaxyId(int galaxyId) {
		this.galaxyId = galaxyId;
	}
	
}

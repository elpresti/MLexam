package com.ml.weatherforecaster.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ml.weatherforecaster.hibernate.HibernateGalaxy;
import com.ml.weatherforecaster.util.GalaxyUtil;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Path("/v1/clima/")
public class V1_Weather {

	private String defaultValue="UNKNOWN";
	private int estimationDaysLimit = 10*360;
	
	@GET
	@Path("/getWeather")
	@Produces(MediaType.TEXT_HTML)
	public String getWeather(){
	    String output="No Data";
	    output= "HOLA MUNDO!";
	    return output;
	}
	
	@GET
	@Path("/getTodayDayNumber")
	@Produces(MediaType.TEXT_HTML)
	public String doAll(){
	    String output="No Data";
	    if ( (GalaxyUtil.getInstance().getGalaxy() != null) ) {
	    	output="Today day number is: "+String.valueOf(GalaxyUtil.getInstance().getGalaxy().getDayNumber());
	    }else{
	    	output="APP not Started yet!";
	    }
	    return output;
	}
	
	@GET
	@Path("/estimateWeather")
	@Produces(MediaType.TEXT_HTML)
	public String estimateWeather(@QueryParam("nextDaysToEstimate") int nextDaysToEstimate){
		String output="No Data";
		if ( (GalaxyUtil.getInstance().getGalaxy() != null)  &&  (GalaxyUtil.getInstance().getGalaxy().getDayNumber() + nextDaysToEstimate) <= (getEstimationDaysLimit()) 
				|| (GalaxyUtil.getInstance().getGalaxy() == null) ) {
			if (nextDaysToEstimate>0){
				GalaxyUtil.getInstance().estimateWeatherStoreResultsAndShow(nextDaysToEstimate);				
			}else{
				output="Invalid day. Please especify a valid number of days to estimate";
			}
		}
		output= GalaxyUtil.getInstance().getStatusTxt();
	    return output;
	}
	
	@GET
	@Path("/diasDeCondicionesOptimas")
	@Produces(MediaType.TEXT_HTML)
	public String diasDeCondicionesOptimas(){
		String output="No hay dias de condiciones climáticas óptimas";
		ArrayList<Integer> optimumDays = HibernateGalaxy.getInstance().getOptimumConditionsDays();
		if (optimumDays.size()>0){
			output="Los dias de condiciones climaticas ópimas que se encontraron en la BD son los siguientes:<br>";
			for(Integer dayNumber : optimumDays){
				output +="- Dia número: "+dayNumber+"<br>";
			}
		}
	    return output;
	}
	
	@GET
	@Path("/periodosDeCondicionesOptimas")
	@Produces(MediaType.TEXT_HTML)
	public String periodosDeCondicionesOptimas(){
		String output="No hay períodos de condiciones climáticas óptimas";
		Integer optimumPeriods = HibernateGalaxy.getInstance().getOptimumConditionsPeriods();
		if (optimumPeriods>0){
			output="En la DB se encontraron "+optimumPeriods+" períodos de condiciones climaticas ópimas<br>";
		}
	    return output;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getEstimationDaysLimit() {
		return estimationDaysLimit;
	}

	public void setEstimationDaysLimit(int estimationDaysLimit) {
		this.estimationDaysLimit = estimationDaysLimit;
	}
	
}

package com.ml.weatherforecaster.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ml.weatherforecaster.util.GalaxyUtil;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
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
			GalaxyUtil.getInstance().estimateWeatherStoreResultsAndShow(nextDaysToEstimate);
		}
		output= GalaxyUtil.getInstance().getStatusTxt();
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

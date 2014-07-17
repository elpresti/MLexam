package com.ml.weatherforecaster.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import com.ml.weatherforecaster.hibernate.HibernateGalaxy;
import com.ml.weatherforecaster.model.RainyDay;
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
	@Path("/optimumConditionsDays")
	@Produces(MediaType.TEXT_HTML)
	public String optimumConditionsDays(){
		String output="There are no days of optimum weather conditions saved on DB";
		ArrayList<Integer> optimumDays = HibernateGalaxy.getInstance().getOptimumConditionsDays();
		if (optimumDays.size()>0){
			output="Total: "+optimumDays.size()+".<br><br>Calculated using an estimation error of up to 0.1km.<br><br>Optimum weather conditions days found on DB are:<br>";
			for(Integer dayNumber : optimumDays){
				output +="- Day number: "+dayNumber+"<br>";
			}
		}
	    return output;
	}
	
	@GET
	@Path("/optimumConditionsPeriods")
	@Produces(MediaType.TEXT_HTML)
	public String optimumConditionsPeriods(){
		String output="There are no days of optimum weather conditions saved on DB. Periods count: 0";
		Integer optimumPeriods = HibernateGalaxy.getInstance().getOptimumConditionsPeriods();
		if (optimumPeriods>0){
			output="There are "+optimumPeriods+" optimum weather contidions periods saved in DB<br>";
		}
		output+="<br><br>Calculated using an estimation error of up to 0.1km";
	    return output;
	}

	@GET
	@Path("/rainyDays")
	@Produces(MediaType.TEXT_HTML)
	public String rainyDays(){
		String output="There are no rainy days saved on DB";
		ArrayList<Integer> rainyDays = HibernateGalaxy.getInstance().getRainyDays();
		if (rainyDays.size()>0){
			output="Total: "+rainyDays.size()+". Rainy days found on DB are:<br>";
			for(Integer dayNumber : rainyDays){
				output +="- Day number: "+dayNumber+"<br>";
			}
		}
	    return output;
	}
	
	@GET
	@Path("/rainyPeriods")
	@Produces(MediaType.TEXT_HTML)
	public String rainyPeriods(){
		String output="There are no Rainy Periods saved on DB. Periods count: 0";
		Integer rainyPeriods = HibernateGalaxy.getInstance().getRainyDaysPeriods();
		if (rainyPeriods>0){
			output="There are "+rainyPeriods+" Rainy Periods saved on DB<br>";
		}
	    return output;
	}

	@GET
	@Path("/droughtDays")
	@Produces(MediaType.TEXT_HTML)
	public String droughtDays(){
		String output="There are no Drought Days saved on DB";
		ArrayList<Integer> droughtDays = HibernateGalaxy.getInstance().getDroughtDays();
		if (droughtDays.size()>0){
			output="Total: "+droughtDays.size()+". Drought Days found on DB are:<br>";
			for(Integer dayNumber : droughtDays){
				output +="- Day number: "+dayNumber+"<br>";
			}
		}
	    return output;
	}
	
	@GET
	@Path("/droughtPeriods")
	@Produces(MediaType.TEXT_HTML)
	public String droughtPeriods(){
		String output="There are no Drought Days saved on DB. Periods count: 0";
		Integer droughtPeriods = HibernateGalaxy.getInstance().getDroughtDaysPeriods();
		if (droughtPeriods>0){
			output="There are "+droughtPeriods+" drought periods saved on DB<br>";
		}
	    return output;
	}
	
	@GET
	@Path("/dayWeather")
	@Produces(MediaType.TEXT_HTML)
	public String dayWeather(@QueryParam("day") int dayNumber){
	    String output="No Data";
	    try{
	      String weatherCondition = HibernateGalaxy.getInstance().getWeatherOfAday(dayNumber);
	      JSONObject dayWeather = new JSONObject();
	      dayWeather.put("dia", dayNumber);
	      dayWeather.put("clima", weatherCondition);
	      output = dayWeather.toString();
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    	output += "<br>"+e.toString();
	    }
	    return output;
	}
	
	@GET
	@Path("/maxRainDay")
	@Produces(MediaType.TEXT_HTML)
	public String maxRainDay(){
	    String output="No Data";
	    try{
	      ArrayList<RainyDay> maxRainyDays = HibernateGalaxy.getInstance().getMostRainyDays();
	      JSONObject dayWeather = new JSONObject();
	      if (maxRainyDays.size()>0){
	    	  for (RainyDay rainyDay : maxRainyDays){
	    		  dayWeather.put("dia", rainyDay.getDayNumber());
	    		  dayWeather.put("perimetro", rainyDay.getTrianglePerimeter());
	    	  }
	      }else{
	    	  dayWeather.put("No rainy days", "found on DB");
	      }
	      output = dayWeather.toString();
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    	output += "<br>"+e.toString();
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

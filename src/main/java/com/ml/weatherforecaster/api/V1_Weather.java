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
	
	@GET
	@Path("/getWeather")
	@Produces(MediaType.TEXT_HTML)
	public String getWeather(){
	    String output="No Data";
	    output= "HOLA MUNDO!";
	    return output;
	}
	
	@GET
	@Path("/doAll")
	@Produces(MediaType.TEXT_HTML)
	public String doAll(){
	    String output="No Data";
	    GalaxyUtil galaxyUtil = new GalaxyUtil();
	    galaxyUtil.doAll();
	    output= "Job started, status printed in console";
	    return output;
	}
	
}

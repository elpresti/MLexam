package com.ml.weatherforecaster.model;

public class PlanetUtil {

	
	private boolean haySequia(int posF,int posB,int posV) {
		/*
			1. posF = posB = posV
			2. posF = posB = (posV+180)<360
			3. posF = (posB+180)<360 = posV
			4. (posF+180)<360 = posB = posV
		*/
		boolean haySequia=false;
		if ( (posF == posB) && (posB == posV) ) {
			haySequia = true;
		}else{
			if (posF == posB) {
				if ( estaAlineado(posF,posV) ) {
					haySequia = false;
				}
			}else{
				if (posF == posV) {
					if ( estaAlineado(posF,posB) ) {
						haySequia = false;
					}
				}
			}
		}
		return haySequia;
	}

	private boolean estaAlineado(int posA, int posB){
		boolean esContraria = false;
		int tmp = posB + 180;
		if (tmp >= 360){
			tmp = tmp - 360;
		}
		if (posA == tmp ) {
			esContraria = true;
		}
		return esContraria;
	}
	
	public static boolean calcularPosicionesPorDia(Planet planet){
		boolean sePudo=false;
		try{
			sePudo=true;
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return sePudo;
	}
	
	public static void main(String[] args){
		Planet planetaF = new Planet();
		planetaF.setCivilizationName("Ferengi");
		planetaF.setAngularSpeed(1);
		planetaF.setDistanceToSun(500);
		planetaF.setClockWise(true);
		
		Planet planetaB = new Planet();
		planetaB.setCivilizationName("Betasoide");
		planetaB.setAngularSpeed(3);
		planetaB.setDistanceToSun(2000);
		planetaB.setClockWise(true);
		
		Planet planetaV = new Planet();
		planetaV.setCivilizationName("Betasoide");
		planetaV.setAngularSpeed(3);
		planetaV.setDistanceToSun(2000);
		planetaV.setClockWise(false);
		
	}
}


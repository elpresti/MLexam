package com.ml.weatherforecaster.util;

import com.ml.weatherforecaster.model.Planet;

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
		
		
	}
}


package com.interfaces;

/**
 * 
 * @author mpo
 *
 */
public interface OnLocalisation
{
	/**
	 * 
	 * @param city City Name
	 * @param raduis Radius around the city
	 */
	void sendCityAndRaduis(String city,String radius);
	/**
	 * 
	 * @param raduis raduis arround the currentPosition
	 */
	void sendRaduis(String radius);
}

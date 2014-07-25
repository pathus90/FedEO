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
	 * @param city country Name
	 * @param raduis Raduis around the city
	 */
	void sendCityAndRaduis(String city,String radius);
	/**
	 * 
	 * @param raduis raduis arround the currentPosition
	 */
	void sendRaduis(String radius);
}

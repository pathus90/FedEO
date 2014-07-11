package com.interfaces;

/**
 * 
 * @author mpo
 *
 */
public interface Localisation
{
	/**
	 * 
	 * @param city country Name
	 * @param raduis Raduis around the city
	 */
	void sendCityAndRaduis(String city,String raduis);
	/**
	 * 
	 * @param raduis raduis arround the currentPosition
	 */
	void sendRaduis(String raduis);
}

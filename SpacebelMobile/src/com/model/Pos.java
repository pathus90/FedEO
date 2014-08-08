package com.model;

import java.io.Serializable;
/**
 * class representing a latitude and longitude of geographic location
 * @author mpo
 * @version 1.0
 */
public class Pos implements Serializable
{
	private static final long serialVersionUID = 6283784474975408307L;
	private String mLatitude;
	private String mLongitude;
	/**
	 * Empty Constructor
	 * 
	 */
	public Pos()
	{
		super();
	}
	/**
	 * Constructor
	 * @param latitude latitude
	 * @param longitude longitude
	 */
	public Pos(String latitude,String longitude)
	{
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	/**
	 * 
	 * @return the latitude
	 */
	public String getLatitude() {
		return mLatitude;
	}
	/**
	 * 
	 * @param latitude latitude
	 */
	public void setLatitude(String latitude) {
		this.mLatitude = latitude;
	}
	/**
	 * 
	 * @return the longitude
	 */
	public String getLongitude() {
		return mLongitude;
	}
	/**
	 * 
	 * @param longitude longitude
	 */
	public void setLongitude(String longitude) {
		this.mLongitude = longitude;
	}
	@Override
	public String toString() {
		return "Pos [lat=" + mLatitude + ", lon=" + mLongitude + "]";
	}
}
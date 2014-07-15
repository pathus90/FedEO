package com.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
/**
 * Entry class representing an atom entry
 * @author mpo
 *
 */
public class ProductEntry implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4140023801030846331L;
	/**
	 * 
	 */
	private String id;
	private String link;
	private String published;
	private String title;
	//StartDate
	private String StartDate;
	private String EndDate;
	private String updated;
	//Acquisition
	private String OrbitNumber;
	private String OrbitDirection;
	private String LastOrbitreNumber;
	private String illuminationAzimuthAngle;
	private String illuminationElevationAngle;
	private String incidenceAngle;
	//
	private String ShortName;
	private String SerialIdentifier;
	private String OrbiteType;
	private String InstrumentShortName;
	private String SensorType;
	private String SensorOperationalMode;
	private String SensorResolution;
	private String SwathIdentifier;
	//Sensing information 
	private String PolarisationMode;
	private String PolarisationChannels;
	private String AntenaLookDirection;
	private String MinimumIncidentAngle;
	//image thumbnail        -
	private String thumbnail;
	private Bitmap bitmapThumbnail;
	//imageQuickLook
	private Bitmap bitmapQuicklook;
	private String Quicklook;
	//Polygon
	private ArrayList<Pos> pos;
	//Center of the polygon
	private Pos centerOf;
	public ProductEntry()
	{
		super();
	}
	/**
	 * 
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	/**
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * 
	 * @return date of publication
	 */
	public String getPublished() {
		return published;
	}
	/**
	 * 
	 * @param published
	 */
	public void setPublished(String published) {
		this.published = published;
	}
	/**
	 * 
	 * @return entry title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return updated date
	 */
	public String getUpdated() {
		return updated;
	}
	/**
	 * 
	 * @param updated
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	/**
	 * 
	 * @return the orbit Number
	 */
	public String getOrbitNumber() {
		return OrbitNumber;
	}
	/**
	 * 
	 * @param orbitNumber
	 */
	public void setOrbitNumber(String orbitNumber) {
		OrbitNumber = orbitNumber;
	}
	/**
	 * 
	 * @return the orbit Direction
	 */
	public String getOrbitDirection() {
		return OrbitDirection;
	}
	/**
	 * 
	 * @param orbitDirection
	 */
	public void setOrbitDirection(String orbitDirection) {
		OrbitDirection = orbitDirection;
	}
	/**
	 * 
	 * @return the StartDate
	 */
	public String getStartDate() {
		return StartDate;
	}
	/**
	 * 
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	/**
	 * 
	 * @return the EndDate
	 */
	public String getEndDate() {
		return EndDate;
	}
	/**
	 * 
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	/**
	 * 
	 * @return the ShortName
	 */
	public String getShortName() {
		return ShortName;
	}
	/**
	 * 
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		ShortName = shortName;
	}
	/**
	 * 
	 * @return the serial Identifier
	 */
	public String getSerialIdentifier() {
		return SerialIdentifier;
	}
	/**
	 * 
	 * @param serialIdentifier
	 */
	public void setSerialIdentifier(String serialIdentifier) {
		SerialIdentifier = serialIdentifier;
	}
	/**
	 * 
	 * @return the Orbit Type
	 */
	public String getOrbiteType() {
		return OrbiteType;
	}
	/**
	 * 
	 * @param orbiteType
	 */
	public void setOrbiteType(String orbiteType) {
		OrbiteType = orbiteType;
	}
	/**
	 * 
	 * @return the Instrument ShortName
	 */
	public String getInstrumentShortName() {
		return InstrumentShortName;
	}
	/**
	 * 
	 * @param instrumentShortName
	 */
	public void setInstrumentShortName(String instrumentShortName) {
		InstrumentShortName = instrumentShortName;
	}
	/**
	 * 
	 * @return the sensorType
	 */
	public String getSensorType() {
		return SensorType;
	}
	/**
	 * 
	 * @param sensorType
	 */
	public void setSensorType(String sensorType) {
		SensorType = sensorType;
	}
	/**
	 * 
	 * @return the sensorOperationalMode
	 */
	public String getSensorOperationalMode() {
		return SensorOperationalMode;
	}
	/**
	 * 
	 * @param sensorOperationalMode
	 */
	public void setSensorOperationalMode(String sensorOperationalMode) {
		SensorOperationalMode = sensorOperationalMode;
	}
	/**
	 * 
	 * @return the sensor Resolution
	 */
	public String getSensorResolution() {
		return SensorResolution;
	}
	/**
	 * 
	 * @param sensorResolution
	 */
	public void setSensorResolution(String sensorResolution) {
		SensorResolution = sensorResolution;
	}
	/**
	 * 
	 * @return
	 */
	public String getSwathIdentifier() {
		return SwathIdentifier;
	}
	public void setSwathIdentifier(String swathIdentifier) {
		SwathIdentifier = swathIdentifier;
	}
	public String getLastOrbitreNumber() {
		return LastOrbitreNumber;
	}
	public void setLastOrbitreNumber(String lastOrbitreNumber) {
		LastOrbitreNumber = lastOrbitreNumber;
	}
	public void setIlluminationAzimuthAngle(String illuminationAzimuthAngle) {
		this.illuminationAzimuthAngle = illuminationAzimuthAngle;
	}
	public String getIlluminationElevationAngle() {
		return illuminationElevationAngle;
	}
	public void setIlluminationElevationAngle(String illuminationElevationAngle) {
		this.illuminationElevationAngle = illuminationElevationAngle;
	}
	public String getIncidenceAngle() {
		return incidenceAngle;
	}
	public void setIncidenceAngle(String incidenceAngle) {
		this.incidenceAngle = incidenceAngle;
	}

	public String getPolarisationMode() {
		return PolarisationMode;
	}
	public void setPolarisationMode(String polarisationMode) {
		PolarisationMode = polarisationMode;
	}
	public String getAntenaLookDirection() {
		return AntenaLookDirection;
	}
	public void setAntenaLookDirection(String antenaLookDirection) {
		AntenaLookDirection = antenaLookDirection;
	}
	public String getPolarisationChannels() {
		return PolarisationChannels;
	}
	public void setPolarisationChannels(String polarisationChannels) {
		PolarisationChannels = polarisationChannels;
	}
	public String getMinimumIncidentAngle() {
		return MinimumIncidentAngle;
	}
	public void setMinimumIncidentAngle(String minimumIncidentAngle) {
		MinimumIncidentAngle = minimumIncidentAngle;
	}
	/**
	 * 
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	/**
	 * 
	 * @param thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * 
	 * @return the quickLook
	 */
	public String getQuicklook() {
		return Quicklook;
	}
	public void setQuicklook(String quicklook) {
		Quicklook = quicklook;
	}
	public ArrayList<Pos> getPos() {
		return pos;
	}
	public void setPos(ArrayList<Pos> pos) {
		this.pos = pos;
	}
	public Pos getCenterOf() {
		return centerOf;
	}
	public void setCenterOf(Pos centerOf) {
		this.centerOf = centerOf;
	}
	public Bitmap getBitmapThumbnail() {
		return bitmapThumbnail;
	}
	public void setBitmapThumbnail(Bitmap bitmapThumbnail) {
		this.bitmapThumbnail = bitmapThumbnail;
	}
	public Bitmap getBitmapQuicklook() {
		return bitmapQuicklook;
	}
	public void setBitmapQuicklook(Bitmap bitmapQuicklook) {
		this.bitmapQuicklook = bitmapQuicklook;
	}
	public String getIlluminationAzimuthAngle() {
		return illuminationAzimuthAngle;
	}
}

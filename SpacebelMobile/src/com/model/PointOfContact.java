package com.model;

import java.io.Serializable;
/**
 * 
 * @author mpo
 * @version 1.0
 */
public class PointOfContact implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID=1L;
	private String mIndividualName;
	private String mOrganisation;
	private String mPositionName;
	private String mPhone;
	private String mFacsimile;
	private String mDelivery;
	private String mCodePostal;
	private String mCountry;
	private String mCity;
	private String mEmail;
	private String mResourceUrl;

	public static boolean isIndividualName=false;
	public static boolean isOrganisation=false;
	public static boolean isPositionName=false;
	public static boolean isPhone=false;
	public static boolean isFax=false;
	public static boolean isDelivery=false;
	public static boolean isCP=false;
	public static boolean isCity=false;
	public static boolean isEmail=false;
	public static boolean isCountry=false;
	public static boolean isRole=false;
	
	public PointOfContact()
	{	
		
	}
	public String getIndividualName() {
		return mIndividualName;
	}
	public void setIndividualName(String individualName) {
		mIndividualName=individualName;
	}
	public String getOrganisation() {
		return mOrganisation;
	}
	public void setOrganisation(String organisation) { 
		mOrganisation=organisation;
	}
	public String getPositionName() {
		return mPositionName;
	}
	public void setPositionName(String positionName) {
		mPositionName=positionName;
	}
	public String getPhone() {
		return mPhone;
	}
	public void setPhone(String phone) {
		mPhone=phone;
	}
	public String getFacsimile() {
		return mFacsimile;
	}
	public void setFacsimile(String facsimile) {
		mFacsimile=facsimile;
	}
	public String getDelivery() {
		return mDelivery;
	}
	public void setDelivery(String delivery) {
		this.mDelivery=delivery;
	}
	public String getCodePostal() {
		return mCodePostal;
	}
	public void setCodePostal(String codePostal) {
		this.mCodePostal=codePostal;
	}
	public String getCity() {
		return mCity;
	}
	public void setCity(String city) {
		this.mCity = city;
	}   
	public String getEmail() {
		return mEmail;
	}
	public void setEmail(String email) {
		this.mEmail=email;
	}
	public String getResourceUrl() {
		return mResourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.mResourceUrl=resourceUrl;
	} 
	public String getCountry() {
		return mCountry;
	}
	public void setCountry(String country){
		this.mCountry = country;
	}
}

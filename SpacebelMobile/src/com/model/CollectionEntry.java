package com.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.R.array;

/**
 * 
 * @author mpo
 * @version 1.0
 */
public class CollectionEntry implements Serializable
{
	private static final long serialVersionUID = -5513349243720516404L;
	private int mId;
	private String mTitle;
	private String mDate;
	private String mIdentifier;
	private String mAbstract;
	private PointOfContact contact;
	private BoundingBox boundingBox;
	// general information
	private String metadataStandardName;
	private String metadataStandardVersion;
	private String dateStamp;
	private String language;
	// Constraints information
	private String mNext;
	private ArrayList<KeyWord>keyWords=new  ArrayList<KeyWord>();
	//Constrains information
	private String limiteConstraints;
	private String AccesConstraints;
	private String otherConstraints;
	//category
	private ArrayList<Category>categories=new ArrayList<Category>();
	public CollectionEntry()
	{
		super();
	}
	/**
	 * Constructor
	 * @param identifier the collection identifier
	 */
	public CollectionEntry(String identifier) {
		// TODO Auto-generated constructor stub
		this.mIdentifier=identifier;
	}
	/**
	 * Constructor
	 * @param id the collection id
	 * @param title the collection title
	 * @param the collection identifier
	 */
	public CollectionEntry(int id, String title, String identifier) 
	{
		// TODO Auto-generated constructor stub
		mId=id;
		this.mTitle=title;
		this.mIdentifier=identifier;
	}
	/**
	 * 
	 * @return title
	 */
	public String getTitle() {
		return mTitle;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.mTitle = title;
	}
	/**
	 * 
	 * @return the Identifier
	 */
	public String getIdentifier() {
		return mIdentifier;
	}
	/**
	 * 
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.mIdentifier = identifier;
	}
	/**
	 * 
	 * @return mDate the Date
	 */
	public String getDate() {
		return mDate;
	}
	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		mDate = date;
	}
	/**
	 * 
	 * @return the Abstract
	 */
	public String getDescription() {
		return mAbstract;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.mAbstract = description;
	}
	/**
	 * 
	 * @return the next collection
	 */
	public String getNextLink() {
		return mNext;
	}
	/**
	 *        
	 * @param next
	 */
	public void setNextLink(String next) {
		this.mNext = next;
	}
	/**
	 * 
	 * @param id
	 */
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.mId=id;
	}
	/**
	 * 
	 * @return the collection ID
	 */
	public int getID() {
		// TODO Auto-generated method stub
		return mId;
	} 
	/**
	 * 
	 * @return 
	 */
	public PointOfContact getContact() {
		return contact;
	}
	/**
	 * 
	 * @param contact
	 */
	public void setContact(PointOfContact contact) {
		this.contact = contact;
	}

	public String getMetadataStandardName() {
		return metadataStandardName;
	}
	public void setMetadataStandardName(String metadataStandardName) {
		this.metadataStandardName = metadataStandardName;
	}
	public String getMetadataStandardVersion() {
		return metadataStandardVersion;
	}
	public void setMetadataStandardVersion(String metadataStandardVersion) {
		this.metadataStandardVersion = metadataStandardVersion;
	}
	public String getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}
	public ArrayList<KeyWord> getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(ArrayList<KeyWord> keyWords) {
		this.keyWords = keyWords;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLimiteConstraints() {
		return limiteConstraints;
	}
	public void setLimiteConstraints(String limiteConstraints) {
		this.limiteConstraints = limiteConstraints;
	}
	public String getAccesConstraints() {
		return AccesConstraints;
	}
	public void setAccesConstraints(String accesConstraints) {
		AccesConstraints = accesConstraints;
	}
	public String getOtherConstraints() {
		return otherConstraints;
	}
	public void setOtherConstraints(String otherConstraints) {
		this.otherConstraints = otherConstraints;
	}
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	@Override
	public String toString() {
		return "Collection [title=" + mTitle  + ", Date=" + mDate + ", identifier="
				+ mIdentifier + ", Abstract=" + mAbstract + "]";
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
}

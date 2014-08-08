package com.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author mpo
 *
 */
public class CollectionFeed implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String next=null;
	private String previous=null;
	private ArrayList<CollectionEntry> collectionEntries;
	
	public CollectionFeed()
	{
		super();
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrev(String previous) 
	{
		this.previous=previous;
	}

	public ArrayList<CollectionEntry> getCollectionEntries() {
		return collectionEntries;
	}

	public void setCollectionEntries(ArrayList<CollectionEntry> mCollections) {
		this.collectionEntries = mCollections;
	}
	
}

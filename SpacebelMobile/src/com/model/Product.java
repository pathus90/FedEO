package com.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author mpo
 *
 */
public class Product implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String totalsResults;
	private String name;
	private String updated; 
	private String next;
	private String prev;
	private ArrayList<ProductEntry>entries=new ArrayList<ProductEntry>();
	public Product()
	{
		super();
	}
	public String getTotalsResults() {
		return totalsResults;
	}
	public void setTotalsResults(String totalsResults) {
		this.totalsResults = totalsResults;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public ArrayList<ProductEntry> getEntries() {
		return entries;
	}
	public void setEntries(ArrayList<ProductEntry> entries) {
		this.entries = entries;
	}
	public String getPrev() {
		// TODO Auto-generated method stub
		return prev;
	}
	public void setPrev(String prev)
	{
		this.prev=prev;
	}
	public String getNext() 
	{
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	@Override
	public String toString() 
	{
		return "Feed [totalsResults=" + totalsResults + ", name=" + name
				+ ", updated=" + updated + ", entries=" + entries + "]";
	}
}

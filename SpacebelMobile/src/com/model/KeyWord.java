package com.model;

import java.io.Serializable;
/**
 * 
 * @author mpo
 * @version 1.0
 */
public class KeyWord implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String keyword;
	public KeyWord()
	{
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}

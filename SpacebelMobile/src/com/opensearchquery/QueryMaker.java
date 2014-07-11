package com.opensearchquery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryMaker
{
	private String mAtomProductUrl;
	private String mAtomCollectionUrl;
	private String query="";
	private String url;
	public QueryMaker()
	{
	
	}
	public QueryMaker(String name, String value) 
	{
		enCodeQuery(name, value);
	}
	public void addFormat(String name, String value)
	{
		enCodeQuery(name, value);
	}
	public void add(String name, String value) {
		query += "&";
		enCodeQuery(name, value);
	}
	private void enCodeQuery(String name, String value) 
	{
		try {
			query +=URLEncoder.encode(name,"UTF-8");
			query += "=";
			query += URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Encoding error");
		}
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	public String getQuery() 
	{
		return query;
	}
	public String toString() 
	{
		return getQuery();
	}
	public String parseString(String Url,String id,String subject,String searchTerms,String count,String startIndex,String startDate,String endDate,
			String dctype,String title,String publisher,String bbox,String name,String lat,String lon,String raduis,String uid,String organisationName){
		String s=url.replace("{eo:parentIdentifier?}", id);
		s.replace("{dc:subject?}", subject);
		s.replace("{searchTerms?", searchTerms);
		s.replace("{startIndex?}", "");
		s.replace("{count?}","");
		s.replace("{time:start?}", startDate);
		s.replace("{time:end?}", endDate);
		s.replace("{dc:type?}", "");
		s.replace("{dc:title?}", "");
		s.replace("{dc:publisher?}", "");
		s.replace("{geo:box?}", bbox);
		s.replace("{geo:name?}", name);
		s.replace("{geo:lat?}", lat);
		s.replace("{geo:lon?}", lon);
		s.replace("{geo:radius?}", "");
		s.replace("{geo:uid?}", "");
		s.replace("{eo:organizationName?}", organisationName);
		s.replace("{eo:productType?}","");
		s.replace("{semantic:classifiedAs?}","");
		s.replace("{sru:recordSchema?}","server-choice");
		return s;
	}
	public void Remove() {
		// TODO Auto-generated method stub
		query="";
	}
	/**
	 * 
	 * @return
	 */
	public String getmAtomProductUrl() {
		return mAtomProductUrl;
	}
	/**
	 * 
	 * @param mAtomProductUrl
	 */
	public void setmAtomProductUrl(String mAtomProductUrl) {
		this.mAtomProductUrl = mAtomProductUrl;
	}
	/**
	 * 
	 * @return 
	 */
	public String getmAtomCollectionUrl() {
		return mAtomCollectionUrl;
	}
	/**
	 * 
	 * @param mAtomCollectionUrl
	 */
	public void setmAtomCollectionUrl(String mAtomCollectionUrl) {
		this.mAtomCollectionUrl = mAtomCollectionUrl;
	}
}
package com.parsedata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.model.CollectionFeed;
import com.model.CollectionEntry;

public class Parser {
	private InputStream xmlInputStream;
	public Parser(InputStream xmlInputStream){
		this.xmlInputStream = xmlInputStream;
	}
	public CollectionFeed xmlParse() throws IOException 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		CollectionFeed collections=null;
		try 
		{      
			SAXParser parser = factory.newSAXParser();
			CollectionHandler handler = new CollectionHandler();         
			parser.parse(this.xmlInputStream,handler);
			return handler.getCollections();        
		} catch (ParserConfigurationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collections;  
	}
}

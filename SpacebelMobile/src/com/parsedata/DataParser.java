package com.parsedata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.model.CollectionFeed;
import com.model.CollectionEntry;
import com.model.Product;

public class DataParser 
{
	public static String Surl ="http://smaad.spacebel.be/opensearch/request/?";
	public static CollectionFeed parseCollections(String url) 
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		CollectionFeed collections = null;
		URL Url=null;
		try
		{
			saxParser=saxParserFactory.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		try
		{
			Url=new URL(url);
		}
		catch(MalformedURLException e)
		{
			System.out.println("link error");
		}
		try
		{
			CollectionHandler handlerCollections = new CollectionHandler();
			InputStream input=Url.openStream();
			Reader reader=new InputStreamReader(input);
			InputSource is=new InputSource(reader);
			if (input==null)
			{
				System.out.println("erreur android,url null");
			}
			else
			{
				try {
					saxParser.parse(is, handlerCollections);
					collections = ((CollectionHandler)handlerCollections).getCollections();
				} 
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return collections;
	}
	public static Product getEntries(String request) {
		URL url=null;
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		Product feed=new Product();
		InputStream input = null;
		try
		{
			saxParser=saxParserFactory.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		try
		{ 
			String s=request.replaceAll("%2C", ",");
			url=new URL(s);
		}
		catch(MalformedURLException e)
		{
			System.out.println("probleme avec le lien");
		}
		try
		{
			ProductHandler handler = new ProductHandler();
			input=url.openStream();
			Reader reader=new InputStreamReader(input);
			InputSource is=new InputSource(reader);
			if (input==null)
			{
				System.out.println("erreur android,url null");
			}
			else
			{
				saxParser.parse(is, handler);
				feed=((ProductHandler)handler).getFeed();
			}
		}
		catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("pas trouver lien");
		}
		return feed;
	}
	public static String ParseTemplate(String request)
	{
		URL url=null;
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		String feed=null;
		InputStream input = null;
		try
		{
			saxParser=saxParserFactory.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		try
		{ 
			url=new URL(request);
		}
		catch(MalformedURLException e)
		{
			System.out.println("link error");
		}
		try
		{
			AtomProductCollectionUrlHandler handler = new AtomProductCollectionUrlHandler();
			input=url.openStream();
			Reader reader=new InputStreamReader(input);
			InputSource is=new InputSource(reader);
			if (input==null)
			{
				System.out.println("erreur android,url null");
			}
			else
			{
				saxParser.parse(is, handler);
			}
		}
		catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("pas trouver lien");
		}
		return feed;
	}
	public static Product parse(String is) 
	{
        Product product = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            // create a handler 
            ProductHandler handler = new ProductHandler();
            // apply handler to the XMLReader
            xmlReader.setContentHandler(handler);
            // the process starts
            xmlReader.parse(new InputSource(new URL(is).openStream()));
            // get the target `Product`
            product = handler.getFeed();
        } catch(Exception ex) 
        {
            Log.d("XML", "Parser: parse() failed");
        }
        // return the product we found
        return product;
    }
}

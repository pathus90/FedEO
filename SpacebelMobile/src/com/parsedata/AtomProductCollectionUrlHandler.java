package com.parsedata;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.opensearchquery.QueryMaker;

public class AtomProductCollectionUrlHandler extends DefaultHandler
{
	private StringBuffer buffer;
	private QueryMaker query;
	public AtomProductCollectionUrlHandler() 
	{
		super();
		query=new QueryMaker();
	}
	/**
	 * 
	 * @return CollectionUrl
	 */
	public String getAtomCollectionUrl()
	{
		return this.query.getmAtomCollectionUrl();
	}
	/**
	 * 
	 * @return ProductUrl
	 */
	public String getAtomProductUrl()
	{
		return this.query.getmAtomProductUrl();
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		super.processingInstruction(target, data);
	}

	public void startDocument() throws SAXException
	{
		super.startDocument();
	}

	public void endDocument()throws SAXException
	{
		super.endDocument();
	}
	
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
	{
		if (qName.equalsIgnoreCase("Url"))
		{
			String type=attributes.getValue("type");
			if (type.equalsIgnoreCase("application/atom+xml"))
			{
				if (attributes.getValue("rel")!=null)
					this.query.setmAtomCollectionUrl(attributes.getValue("template"));
				else
					this.query.setmAtomProductUrl(attributes.getValue("template"));
			}
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		
	}
	
	public void characters(char[] ch, int start, int length)throws SAXException  
	{
		String line =new String(ch,start,length);
		if (this.buffer!=null)
		{
			this.buffer.append(line);
		}
	}
}

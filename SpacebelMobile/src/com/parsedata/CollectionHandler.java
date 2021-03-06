package com.parsedata;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.model.BoundingBox;
import com.model.Category;
import com.model.CollectionFeed;
import com.model.CollectionEntry;
import com.model.PointOfContact;

public class CollectionHandler extends DefaultHandler
{
	//collection
	private CollectionFeed collection;
	//list of collections
	private ArrayList<CollectionEntry> mCollectionEntries;
	//one collection
	private CollectionEntry mCollectionEntry;
	//contact
	private PointOfContact mContact;
	private boolean isContact;
	//Bbox
	private BoundingBox boundingBox;
	private boolean isBoundingBox=false;

	private Category category;
	private ArrayList<Category> keywords;
	private boolean isKeyWord=false;

	private StringBuffer buffer=null;
	private boolean isEntry=false;
	private boolean isfeed=false;
	private boolean isAbstract;
	private boolean isDateStamp=false;
	private boolean isMetastandardName=false;
	private boolean isMetaDataStandardVersion;

	/**
	 * 
	 */
	public CollectionHandler()
	{
		super();
	}
	/**
	 * 
	 * @return collection 
	 */
	public CollectionFeed getCollections()
	{
		return collection;
	}
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		super.processingInstruction(target, data);
	}
	/**
	 * 
	 */
	public void startDocument() throws SAXException
	{
		super.startDocument();
	}
	/**
	 * 
	 */
	public void endDocument()throws SAXException
	{
		super.endDocument();
	}
	/**
	 * 
	 */
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
	{
		buffer=new StringBuffer();
		//Starting node
		if (qName.equalsIgnoreCase("feed"))
		{
			collection=new CollectionFeed();
			this.mCollectionEntries=new ArrayList<CollectionEntry>();
			keywords=new ArrayList<Category>();
			this.isfeed=true;
		}
		else if(localName.equalsIgnoreCase("Link") && attributes.getValue("rel").equalsIgnoreCase("next"))
		{
			collection.setNext(attributes.getValue("href"));
		}
		else if(localName.equalsIgnoreCase("Link") && attributes.getValue("rel").equalsIgnoreCase("previous"))
		{ 
			collection.setPrev(attributes.getValue("href"));
			//Log.i("precedent", collection.getPrevious());
		}
		// Entry
		else if (qName.equalsIgnoreCase("entry"))
		{
			this.mCollectionEntry=new CollectionEntry();
			this.isEntry=true;
		}
		// Contact
		else if (localName.equalsIgnoreCase("pointOfContact"))
		{
			mContact=new PointOfContact();
			isContact=true;
		}
		else if (qName.equalsIgnoreCase("category"))
		{
			category=new Category();
			category.setCategory(attributes.getValue("label"));	
			keywords.add(category);
		}
		else if (localName.equalsIgnoreCase("EX_GeographicBoundingBox"))
		{
			boundingBox=new BoundingBox();
		}
		else if(localName.equalsIgnoreCase("individualName"))
		{
			PointOfContact.isIndividualName=true;
		}
		else if(localName.equalsIgnoreCase("organisationName"))
		{
			PointOfContact.isOrganisation=true;
		}
		else if(localName.equalsIgnoreCase("positionName"))
		{
			PointOfContact.isPositionName=true;
		}
		if( localName.equalsIgnoreCase("voice"))
		{
			PointOfContact.isPhone=true;
		}
		if( localName.equalsIgnoreCase("facsimile"))
		{
			PointOfContact.isFax=true;
		}
		if( localName.equalsIgnoreCase("deliveryPoint"))
		{
			PointOfContact.isDelivery=true;
		}
		if (localName.equalsIgnoreCase("city"))
		{
			PointOfContact.isCity=true;
		}
		if (localName.equalsIgnoreCase("postalCode"))
		{
			PointOfContact.isCP=true;
		}
		if (localName.equalsIgnoreCase("country"))
		{
			PointOfContact.isCountry=true;
		}
		if( localName.equalsIgnoreCase("electronicMailAddress"))
		{
			PointOfContact.isEmail=true;
		}
		if( localName.equalsIgnoreCase("CI_RoleCode "))
		{
			PointOfContact.isRole=true;
		}
		if( localName.equalsIgnoreCase("abstract"))
		{
			isAbstract=true;
		}
		if (localName.equals("dateStamp"))
		{
			isDateStamp=true;
		}
		if  (localName.equalsIgnoreCase("metadataStandardName"))
		{
			isMetastandardName=true;
		}
		if  (localName.equalsIgnoreCase("metadataStandardVersion"))
		{
			isMetaDataStandardVersion=true;
		}
		if (localName.equalsIgnoreCase("summary"))
		{
			//this.mCollectionEntry.setCategories(keywords);
		}
	}
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		//we finish parsing the metaData
		if (qName.equalsIgnoreCase("feed"))
		{
			if (this.isfeed)
			{
				this.collection.setCollectionEntries(mCollectionEntries);
				this.isfeed=false;
			}
		}
		//getting an entry
		if (qName.equalsIgnoreCase("entry"))
		{
			if (isEntry)
			{
				this.mCollectionEntries.add(mCollectionEntry);
				this.mCollectionEntry=null;
				this.isEntry=false;	
			}
		}
		//getting the title
		if (qName.equalsIgnoreCase("title"))
		{
			if (isEntry)
			{
				this.mCollectionEntry.setTitle(this.buffer.toString());
				this.buffer=null;
			}
		}
		//getting the date
		if (qName.equalsIgnoreCase("dc:date"))
		{
			if (isEntry)
			{
				this.mCollectionEntry.setDate(this.buffer.toString());
				this.buffer=null;
			}
		}
		if (qName.equalsIgnoreCase("dc:identifier"))
		{
			if (isEntry)
			{
				this.mCollectionEntry.setIdentifier(this.buffer.toString());
				this.buffer=null;
			}
		}
		if (qName.equalsIgnoreCase("category"))
		{
			if (isEntry)
			{
				this.keywords.add(category);
			}
		}
		if (localName.equalsIgnoreCase("CharacterString"))
		{
			//getting the abstractName for the collection
			if (isAbstract)
			{
				this.mCollectionEntry.setDescription(this.buffer.toString());
				this.buffer=null;
				this.isAbstract=false;	
			}
			else if (isContact)
			{
				if(PointOfContact.isIndividualName)
				{
					this.mContact.setIndividualName(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isIndividualName=false;	
				}
				else if(PointOfContact.isOrganisation)
				{
					this.mContact.setOrganisation(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isOrganisation=false;	
				}
				else if(PointOfContact.isPositionName)
				{
					this.mContact.setPositionName(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isPositionName=false;	
				}
				else if(PointOfContact.isPhone)
				{
					this.mContact.setPhone(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isPhone=false;	
				}
				else if(PointOfContact.isFax)
				{  
					this.mContact.setFacsimile(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isFax=false;	
				}
				else if(PointOfContact.isDelivery)
				{
					this.mContact.setDelivery(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isDelivery=false;	
				}
				else if(PointOfContact.isCity)
				{
					this.mContact.setCity(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isCity=false;	
				}
				else if(PointOfContact.isCP)
				{
					this.mContact.setCodePostal(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isCP=false;	
				}
				else if(PointOfContact.isCountry)
				{
					this.mContact.setCountry(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isCountry=false;	
				}
				else if(PointOfContact.isEmail)
				{
					this.mContact.setEmail(this.buffer.toString());
					this.buffer=null;
					PointOfContact.isEmail=false;	
				}
			}
			else if (isMetastandardName)
			{
				this.mCollectionEntry.setMetadataStandardName(buffer.toString());
				isMetastandardName=false;
			}
			else if (isMetaDataStandardVersion)
			{
				this.mCollectionEntry.setMetadataStandardVersion(buffer.toString());
				isMetaDataStandardVersion=false;
			}
		}
		if (localName.equalsIgnoreCase("pointOfContact"))
		{
			this.mCollectionEntry.setContact(mContact);
			isContact=false;
		}
		else if (localName.equalsIgnoreCase("EX_GeographicBoundingBox"))
		{
			this.mCollectionEntry.setBoundingBox(boundingBox);
			isBoundingBox=false;
		}
		if (qName.equalsIgnoreCase("gco:Date"))
		{
			if (isDateStamp)
			{
				this.mCollectionEntry.setDateStamp(buffer.toString());
				isDateStamp=false;
			}
		}
		if (qName.equalsIgnoreCase("gmd:LanguageCode"))
		{
			this.mCollectionEntry.setLanguage(buffer.toString());
		}


	}
	/**
	 * 
	 */
	public void characters(char[] ch, int start, int length)throws SAXException  
	{
		String line =new String(ch,start,length);
		if (this.buffer!=null)
		{
			this.buffer.append(line);
		}
	}	
} 
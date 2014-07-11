package com.parsedata;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.model.ProductEntry;
import com.model.Pos;
import com.model.Product;
import com.spacebelmobile.R;

/**
 * 
 * @author mpo
 *
 */
public class ProductHandler extends DefaultHandler
{
	private Product feed;
	//one entry
	private ProductEntry entry;	
	//Array of entry
	private ArrayList<ProductEntry>entries;
	private StringBuffer tempVal=null;
	private boolean isEntry=false;
	//entry link
	private String link;
	//platform,instrument,sensor
	private boolean isPlatform=false;
	private boolean isInstrument=false;
	private boolean isSensor=false;
	private boolean isAcquisition=false;
	//quicklook and thumbnail parsing
	private boolean isMediaGroup;
	private String url;
	//polygon georss;
	private boolean isLinearing=false;
	private Pos pos;
	private ArrayList<Pos>Linear;
	private boolean isPos=false;
	private boolean isCenterOf;

	public ProductHandler() {
		super();
	}
	public Product getFeed()
	{
		return feed;
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
		tempVal=new StringBuffer();
		if (localName.equalsIgnoreCase("feed"))
		{
			feed=new Product();
			this.entries=new ArrayList<ProductEntry>();
		}
		if (qName.equalsIgnoreCase("entry"))
		{
			this.entry=new ProductEntry();
			this.isEntry=true;
		}
		// next products
		if(qName.equalsIgnoreCase("Link") && attributes.getValue("rel").equalsIgnoreCase("next"))
		{
			link=attributes.getValue("href");
			this.feed.setNext(link);
			Log.i("next", link);
		}
		// previous products
		if(qName.equalsIgnoreCase("Link") && attributes.getValue("rel").equalsIgnoreCase("previous"))
		{
			this.feed.setNext(attributes.getValue("href"));
			Log.i("previous", attributes.getValue("href"));
		}
		// platform node starting
		if (qName.equalsIgnoreCase("eop:Platform"))
		{
			this.isPlatform=true;
		}
		// instrument node starting
		if (qName.equalsIgnoreCase("eop:Instrument"))
		{
			this.isInstrument=true;
		}
		//sensor node starting
		if (qName.equalsIgnoreCase("eop:Sensor"))
		{
			this.isSensor=true;
		}
		if (qName.equalsIgnoreCase("sar:Acquisition"))
		{
			this.isAcquisition=true;
		}
		if (qName.equalsIgnoreCase("media:group"))
		{
			this.isMediaGroup=true;
		}
		//thumbnail image 
		if (qName.equalsIgnoreCase("media:content"))
		{
			if (this.isEntry && this.isMediaGroup)
			{
				url=attributes.getValue("url");
			}
		}
		//LinearRing node Starting
		if (qName.equalsIgnoreCase("LinearRing"))
		{
			Linear=new ArrayList<Pos>();
			this.isLinearing=true;

		}
		if (qName.equalsIgnoreCase("pos"))
		{
			pos=new Pos();
			this.isPos=true;	
		}
		//polygon
		if (qName.equalsIgnoreCase("eop:centerOf"))
		{
			this.isCenterOf=true;	
		}
	}
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		//Entry field
		if (qName.equalsIgnoreCase("id"))
		{
			if (this.isEntry)
			{
				this.entry.setId(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("published"))
		{
			if (this.isEntry)
			{
				this.entry.setPublished(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("title"))
		{
			if (this.isEntry)
			{
				this.entry.setTitle(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("updated"))
		{
			if (this.isEntry)
			{
				this.entry.setUpdated(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		//platform information for entry
		if (qName.equalsIgnoreCase("eop:shortName"))
		{
			if (this.isEntry && this.isPlatform)
			{
				this.entry.setShortName(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:serialIdentifier"))
		{
			if (this.isEntry && this.isPlatform)
			{
				this.entry.setSerialIdentifier(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:orbitType"))
		{
			if (this.isEntry && this.isPlatform)
			{
				this.entry.setOrbiteType(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:orbitNumber"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setOrbitNumber(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:lastOrbitNumber"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setLastOrbitreNumber(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:orbitDirection"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setOrbitDirection(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("sar:polarisationMode"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setPolarisationMode(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("sar:polarisationChannels"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setPolarisationChannels(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("sar:antennaLookDirection"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setAntenaLookDirection(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("sar:minimumIncidenceAngle"))
		{
			if (this.isEntry && this.isAcquisition)
			{
				this.entry.setMinimumIncidentAngle(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		//Intrument 
		if (qName.equalsIgnoreCase("eop:Instrument"))
		{
			if (this.isEntry && this.isInstrument)
			{
				this.entry.setInstrumentShortName(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		//Sensor
		if (qName.equalsIgnoreCase("eop:sensorType"))
		{
			if (this.isEntry && this.isSensor)
			{
				this.entry.setSensorType(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:operationalMode"))
		{
			if (this.isEntry && this.isSensor)
			{
				this.entry.setSensorOperationalMode(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:resolution"))
		{
			if (this.isEntry && this.isSensor)
			{
				this.entry.setSensorResolution(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("eop:swathIdentifier"))
		{
			if (this.isEntry && this.isSensor)
			{
				this.entry.setSwathIdentifier(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		//StartDate and endDate
		if (qName.equalsIgnoreCase("gml:beginPosition"))
		{
			if (this.isEntry)
			{
				this.entry.setStartDate(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("gml:endPosition"))
		{
			if (this.isEntry)
			{
				this.entry.setEndDate(this.tempVal.toString());
				this.tempVal=null;
			}
		}
		//pos node 
		if (qName.equalsIgnoreCase("pos")||qName.equalsIgnoreCase("coordinates"))
		{
			//we have the coordinante of the center of the polygon
			if (this.isEntry && isCenterOf)
			{
				Pos pos=new Pos();
				String [] latlong=this.tempVal.toString().split(" ");
				pos.setLatitude(latlong[0]);
				pos.setLongitude(latlong[1]);
				this.entry.setCenterOf(pos);
				this.isCenterOf=false;
				this.isPos=false;
				this.tempVal=null;
			}
		}
		if (qName.equalsIgnoreCase("georss:polygon") ||qName.equalsIgnoreCase("gml:posList"))
		{
			if (this.isEntry)
			{
				String [] polygone=this.tempVal.toString().split(" ");
				ArrayList<Pos>mListPos=new ArrayList<Pos>();
				for (int k = 0; k < polygone.length ; k = k + 2) 
				{
					Pos pos=new Pos();
					pos.setLatitude(polygone[k]);
					pos.setLongitude(polygone[k+1]);
					mListPos.add(pos);
				}
				this.entry.setPos(mListPos);
				this.tempVal=null;
			}
		}
		//End of feed
		if (qName.equalsIgnoreCase("feed"))
		{
			this.feed.setEntries(entries);
		}
		//totals results
		if (qName.equalsIgnoreCase("os:totalResults"))
		{
			this.feed.setTotalsResults(this.tempVal.toString());
		}
		//End of an Entry 
		if (qName.equalsIgnoreCase("Entry"))
		{
			this.entries.add(entry);
			this.isEntry=false;
			this.entry=null;
		}
		//End of an eop:platform 
		if (qName.equalsIgnoreCase("eop:Platform"))
		{
			this.isPlatform=false;
		}
		//End of an eop:Instrument
		if (qName.equalsIgnoreCase("eop:Instrument"))
		{
			this.isInstrument=false;
		}
		//End of an eop:Sensor
		if (qName.equalsIgnoreCase("eop:Sensor"))
		{
			this.isSensor=false;
		}
		//End of an eop:Sensor
		if (qName.equalsIgnoreCase("sar:Acquisition"))
		{
			this.isAcquisition=false;
		}
		//image thumbnail and Quicklook
		if (qName.equalsIgnoreCase("media:group"))
		{
			this.isMediaGroup=false;
		}
		// thumbnail
		if (qName.equalsIgnoreCase("media:category"))
		{
			if  (tempVal.toString().equalsIgnoreCase("THUMBNAIL"))
			{
				this.entry.setThumbnail(url);
			}
			else // Quicklook image
			{
				this.entry.setQuicklook(url);
			}
		}
		//end of Linearing polygon
		if (qName.equalsIgnoreCase("LinearRing"))
		{
			this.isLinearing=false;
			this.entry.setPos(Linear);
			this.Linear=null;
		}
	}
	public void characters(char[] ch, int start, int length)throws SAXException  
	{
		String line =new String(ch,start,length);
		if (this.tempVal!=null)
		{
			this.tempVal.append(line);
		}
	}
}

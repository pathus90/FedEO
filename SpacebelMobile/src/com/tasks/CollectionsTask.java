package com.tasks;

import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.model.CollectionFeed;
import com.parsedata.CollectionHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class CollectionsTask extends AsyncTask<String, Void, CollectionFeed>
{
	private onTaskCollectionComplete mListener;
	private ProgressDialog mDialog;
	private CollectionFeed mCollections;

	public interface onTaskCollectionComplete
	{
		public void OnServerError();
		public void onTaskCollectionCompleted(CollectionFeed collections);
	}
	public CollectionsTask(CollectionFeed colections,Activity activity) 
	{
		// TODO Auto-generated constructor stub
		this.mCollections=colections;
	}
	public CollectionsTask(onTaskCollectionComplete listener) 
	{
		// TODO Auto-generated constructor stub
		this.mListener=listener;
		mDialog=new ProgressDialog((Context) listener);
	}
	@Override
	protected void onPreExecute() 
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog.setTitle("Loading results...");
		mDialog.setCancelable(false);
		mDialog.setMessage("Please wait...");
		mDialog.show();
	}
	@Override
	protected void onPostExecute(CollectionFeed result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}
		this.mListener.onTaskCollectionCompleted(result);
	}
	@Override
	protected CollectionFeed doInBackground(String... urls) 
	{
		// TODO Auto-generated method stub
		//Loading all collections
		try {
			// create a XMLReader from SAXParser
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			// create a handler 
			CollectionHandler handler = new CollectionHandler();
			// apply handler to the XMLReader
			xmlReader.setContentHandler(handler);
			// the process starts
			xmlReader.parse(new InputSource(new URL(urls[0]).openStream()));
			// get the target `Product`
			mCollections = handler.getCollections();
			return this.mCollections;
		} 
		catch(Exception ex) 
		{
			Log.d("XML", "Parser: parse() failed");
		}
		return null;
	}
}

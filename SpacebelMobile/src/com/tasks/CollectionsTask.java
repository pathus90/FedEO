package com.tasks;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.model.CollectionFeed;
import com.parsedata.CollectionHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
/**
 * CollectionTask is a class representing for loading
 * 
 * @author mpo
 *
 */
public class CollectionsTask extends AsyncTask<String, Void, CollectionFeed>
{	
	private onTaskCollectionComplete mListener;//Listener
	private ProgressDialog mDialog;//progressDialog
	private CollectionFeed mCollection;//collection
	public interface onTaskCollectionComplete
	{
		public void OnServerError();
		public void onTaskCollectionCompleted(CollectionFeed collections);
	}
	public CollectionsTask(CollectionFeed collection,Activity activity) 
	{
		// TODO Auto-generated constructor stub
		this.mCollection=collection;
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
		//TODO Auto-generated method stub
		super.onPreExecute();
		mDialog.setTitle("Loading results...");
		mDialog.setCancelable(false);
		mDialog.setMessage("Please wait...");
		mDialog.show();
	}
	@Override
	protected void onPostExecute(CollectionFeed result) 
	{
		//TODO Auto-generated method stub
		super.onPostExecute(result);
		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}
		this.mListener.onTaskCollectionCompleted(result);
	}
	@Override
	protected CollectionFeed doInBackground(String...urls) 
	{
		//TODO Auto-generated method stub
		//Loading all collections
		
		HttpGet uri = new HttpGet(urls[0]);    
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			response = client.execute(uri);
		} catch (ClientProtocolException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() != 200) {
			Log.d("tag", "HTTP error, invalid server status code: " + response.getStatusLine());  
		}
		else
		{
			try {
				// create a XMLReader from SAXParser
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				// create a handler 
				CollectionHandler handler = new CollectionHandler();
				// apply handler to the XMLReader
				xmlReader.setContentHandler(handler);
				// the process starts
				xmlReader.parse(new InputSource(new URL(urls[0]).openStream()));
				// get the target `Collection`
				mCollection = handler.getCollections();
				return this.mCollection;
			} 
			catch(Exception ex) 
			{
				Log.d("XML", "Parser: parse() failed");
			}
		}
		return null;
	}
}

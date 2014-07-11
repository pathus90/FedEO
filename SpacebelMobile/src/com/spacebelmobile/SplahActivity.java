package com.spacebelmobile;

import com.parsedata.DataParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
public class SplahActivity extends Activity 
{
	ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splah);
		if (!isOnline())
		{
			Toast.makeText(this, "No Internet connection found to run the app.", Toast.LENGTH_LONG).show();
			finish();
		}
		else
		{
			new task().execute("http://smaad.spacebel.be/opensearch/description.xml");
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	/**
	 * 
	 * @return True  if internet connection is avalaible
	 */
	public boolean isOnline() 
	{
		ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	private class task extends AsyncTask<String, Void, String>
	{
		@Override
		protected void onPreExecute() {
		//TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String result) {
			//TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent i=new Intent(getBaseContext(),MainActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("url", result);
			//Log.i("url", result);
			i.putExtras(bundle);
			startActivity(i);
			//Remove activity
			finish();
		}
		@Override
		protected String doInBackground(String... urls) {
			return DataParser.ParseTemplate(urls[0]);
		}
	}
}

package com.spacebelmobile;
import com.model.CollectionEntry;
import com.model.KeyWord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

public class MetaDataCollectionActivity extends Activity 
{
	private CollectionEntry mCollection=null;
	TextView mTitle;
	WebView mAbstract;
	TextView mIdentifier;
	TextView mDate;
	Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailscollection);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		// get data from the precedent activity to have more information about the collection
		mBundle=getIntent().getExtras();
		mCollection=(CollectionEntry)mBundle.getSerializable("collection");
		// getting the view widget
		mTitle=(TextView)findViewById(R.id.titleMetaData);
		mAbstract=(WebView)findViewById(R.id.absCollection);
		mIdentifier=(TextView)findViewById(R.id.identifierMetaDATA);
		mDate=(TextView)findViewById(R.id.datemetaData);
		// formatting abstract ttext (justifiy)
		String text;
		text = "<html><body><p align='justify'>";
		text+= ""+mCollection.getDescription();
		text+= "</p></body></html>";
		// setting Data
		mIdentifier.setText(mCollection.getIdentifier());
		mDate.setText(mCollection.getDate());
		mTitle.setText(mCollection.getTitle());
		mAbstract.setBackgroundColor(0x00000000);
		mAbstract.loadData(text, "text/html", "utf-8");
		mAbstract.getSettings();
		// contacts 
		TextView individualName=(TextView)findViewById(R.id.IndividualName);
		TextView organisationName=(TextView)findViewById(R.id.Organisation_Name);
		TextView phone=(TextView)findViewById(R.id.Phone);
		TextView facsimile=(TextView)findViewById(R.id.facsimile);
		TextView deliveryPoint=(TextView)findViewById(R.id.deliverypoint);
		TextView city=(TextView)findViewById(R.id.City);
		TextView postalCode=(TextView)findViewById(R.id.PostalCode);
		TextView country=(TextView)findViewById(R.id.Country);
		TextView email=(TextView)findViewById(R.id.Email);
		TextView positionName=(TextView)findViewById(R.id.PositionName);
		TextView timeStamp=(TextView)findViewById(R.id.timeStamp);
		TextView metadataStandard=(TextView)findViewById(R.id.MSN);
		TextView metadataStandardVersion=(TextView)findViewById(R.id.MSV);
		TextView languageTextView=(TextView)findViewById(R.id.lng);
        //Responsable contact
		individualName.setText(mCollection.getContact().getIndividualName());
		organisationName.setText(mCollection.getContact().getOrganisation());
		phone.setText(mCollection.getContact().getPhone().replaceAll("\\s", ""));
		facsimile.setText(mCollection.getContact().getFacsimile().replaceAll("\\s", ""));
		deliveryPoint.setText(mCollection.getContact().getDelivery());
		city.setText(mCollection.getContact().getCity());
		postalCode.setText(mCollection.getContact().getCodePostal());
		country.setText(mCollection.getContact().getCountry());
		email.setText(mCollection.getContact().getEmail());
		positionName.setText(mCollection.getContact().getPositionName());
		//General Information
		timeStamp.setText(mCollection.getDateStamp());
		metadataStandard.setText(mCollection.getMetadataStandardName());
		metadataStandardVersion.setText(mCollection.getMetadataStandardVersion());
		languageTextView.setText(mCollection.getLanguage());
		// general information
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.application_main, menu);
		return true;
	}
}
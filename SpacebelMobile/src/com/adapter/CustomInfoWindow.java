package com.adapter;

import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.model.ProductEntry;
import com.spacebelmobile.R;
import com.utils.Utils;
/**
 * this class is used for implementing custom InfoWindow
 * @author mpo
 *
 */
public class CustomInfoWindow implements InfoWindowAdapter
{  
	//
	private Map<Marker,ProductEntry> mMap;
	private LayoutInflater mInflater;
	public CustomInfoWindow(LayoutInflater inflater,Map<Marker, ProductEntry> map) 
	{
		this.mInflater=inflater;
		this.mMap=map;
	}
	@Override
	public View getInfoWindow(Marker marker)
	{
		return null;
	}
	@Override
	public View getInfoContents(Marker marker) 
	{
		ProductEntry entry=mMap.get(marker);
		View view=this.mInflater.inflate(R.layout.mapwindowadapter, null);
		//getting widgets from the ui
		ImageView imageView=(ImageView)view.findViewById(R.id.badge);
		TextView title=(TextView)view.findViewById(R.id.title);
		TextView shortname=(TextView)view.findViewById(R.id.snippet);
		TextView serialNumber=(TextView)view.findViewById(R.id.serialIdentifier);
		
		TextView orbitType=(TextView)view.findViewById(R.id.orbiteType);
		TextView orbitDirection=(TextView)view.findViewById(R.id.worbitDirection);
		TextView orbitNumber=(TextView)view.findViewById(R.id.wOrbitNumber);
		TextView lastorbitNumber=(TextView)view.findViewById(R.id.wlastOrbitNumber);
		
		TextView sensorType=(TextView)view.findViewById(R.id.sensorType);
		TextView sensorMode=(TextView)view.findViewById(R.id.wsensorOperationalMode);
		TextView sensorResolution=(TextView)view.findViewById(R.id.sensorResolution);
		TextView startDate=(TextView)view.findViewById(R.id.wstartDate);
		TextView endDate=(TextView)view.findViewById(R.id.wendDate);
		TextView instrumentShortName=(TextView)view.findViewById(R.id.winstrumentShortName);
		
		//set data in widgets
		imageView.setImageBitmap(entry.getBitmapThumbnail());
		title.setText(Utils.ParseCollectionIdentifier(entry.getTitle()));
		shortname.setText(entry.getShortName());
		
		serialNumber.setText(entry.getSerialIdentifier());
		instrumentShortName.setText(entry.getInstrumentShortName());
		sensorResolution.setText(entry.getSensorResolution());
		sensorType.setText(entry.getSensorType());
		sensorMode.setText(entry.getSensorOperationalMode());
		
		orbitDirection.setText(entry.getOrbitDirection());
		lastorbitNumber.setText(entry.getLastOrbitreNumber());
		orbitNumber.setText(entry.getOrbitNumber());
		orbitType.setText(entry.getOrbitType());
		
		startDate.setText(entry.getStartDate().replaceAll("\\s", ""));
		endDate.setText(entry.getEndDate().replaceAll("\\s", ""));
		
		return view;
	}
}
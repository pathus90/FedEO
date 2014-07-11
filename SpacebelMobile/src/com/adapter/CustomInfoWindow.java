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
		TextView orbityDirection=(TextView)view.findViewById(R.id.orbitDirection);
		//set data in widgets
		imageView.setImageBitmap(entry.getBitmapThumbnail());
		title.setText(entry.getTitle());
		shortname.setText(entry.getShortName());
		serialNumber.setText(entry.getSerialIdentifier());
		orbitType.setText(entry.getOrbiteType());
		orbityDirection.setText(entry.getOrbitDirection());
		return view;
	}
}
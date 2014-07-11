package com.fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adapter.CustomInfoWindow;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.model.ProductEntry;
import com.model.Pos;
import com.model.Product;
import com.spacebelmobile.*;
public class GoogleMapFragment extends SupportMapFragment
{
	private SupportMapFragment sf;
	private GoogleMap googleMap;
	private Map<Marker, ProductEntry>MarkerEntry=new HashMap<Marker, ProductEntry>();
	Presenter presenter;
	//getting information from the mainActivity
	public interface Presenter
	{
		void setMarKers();
		Product getEntry();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.mapfragment, container, false);
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			presenter = (Presenter) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement Presenter");
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		googleMap.setInfoWindowAdapter(new CustomInfoWindow(getActivity().getLayoutInflater(),MarkerEntry));
	}
	private void initilizeMap(View view) 
	{
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity().getBaseContext());
		// Showing status
		if(status!=ConnectionResult.SUCCESS)// Google Play Services are not available
		{ 
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this.getActivity(), requestCode);
			dialog.show();
		}
		else 
		{ // Google Play Services are available
			if (googleMap == null) 
			{
				sf= ((SupportMapFragment)getFragmentManager().findFragmentById(R.id.map));
				googleMap=sf.getMap();
				// check if map is created successfully or not
				if (googleMap == null) 
				{
					Toast.makeText(this.getActivity().getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if ( googleMap!= null) {
			getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
			getActivity().getSupportFragmentManager()
			.beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map)).commit();
			googleMap = null;
		}
	}
	// These methods are used for the map
	private void drawFootPrints()
	{
		// If locations are already saved
		Product entries=presenter.getEntry();
		if(entries.getEntries().size()!=0)
		{
			String lat = "";
			String lng = "";
			// Iterating through all the locations stored in the entry
			for(int i=0;i<entries.getEntries().size();i++)
			{
				// Getting the latitude
				ProductEntry entry=entries.getEntries().get(i);
				lat = entry.getCenterOf().getLatitude();
				Log.i("lat", lat);
				// Getting the longitude 
				lng = entry.getCenterOf().getLongitude();
				//drawing the polygon
				PolygonOptions polygonOptions = new PolygonOptions();
				polygonOptions.addAll(PolygonPos(entry));
				polygonOptions.strokeColor(Color.BLUE);
				polygonOptions.strokeWidth(3);
				polygonOptions.fillColor(0x7F00FF00);
				googleMap.addPolygon(polygonOptions);
				// Drawing marker on the map
				Marker m=drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
				MarkerEntry.put(m, entry);
				Log.i("map",""+MarkerEntry.size());
			}
			// Moving CameraPosition to last clicked position
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
			// Setting the zoom level in the map on last position  is clicked
			//googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
		}
	}
	//get the polygon of each entry
	public ArrayList<LatLng>PolygonPos(ProductEntry entry)
	{
		ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
		for (int i=0;i<entry.getPos().size();i++)
		{
			Pos pos=entry.getPos().get(i);
			LatLng latLng=new LatLng(Double.parseDouble(pos.getLatitude()), Double.parseDouble(pos.getLongitude()));
			arrayPoints.add(latLng);
		}
		return arrayPoints;
	}
	//draw Marker on the map
	private Marker drawMarker(LatLng point){
		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();
		// Setting latitude and longitude for the marker
		markerOptions.position(point);
		markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		// Adding marker on the Google Map
		Marker m=googleMap.addMarker(markerOptions);
		return m;
	}
}




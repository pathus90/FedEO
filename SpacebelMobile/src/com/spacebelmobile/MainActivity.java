package com.spacebelmobile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.adapter.CustomInfoWindow;
import com.adapter.CollectionListViewAdapter;
import com.adapter.ProductAdapter;
import com.databaseManagement.DatabaseHandler;
import com.fragment.CityRaduisDialogFragment;
import com.fragment.DatePickerFragment;
import com.fragment.RaduisDialogFragment;
import com.fragment.DatePickerFragment.OnCalendarChangedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.interfaces.Localisation;
import com.model.CollectionEntry;
import com.model.ProductEntry;
import com.model.Pos;
import com.model.Product;
import com.opensearchquery.QueryMaker;
import com.parsedata.DataParser;
import com.services.GPSTracker;
import com.spacebelmobile.R;
import com.tasks.ImageDownloader;
import com.utils.Constant;
import com.utils.Utils;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author mpo
 *
 */
public class MainActivity extends FragmentActivity implements OnMapClickListener,
LocationListener, 
OnMapLongClickListener, 
OnMarkerClickListener,
OnInfoWindowClickListener,
OnMarkerDragListener,OnCalendarChangedListener,
OnDragListener
{
	// Google Map
	private GoogleMap googleMap=null;
	private Polygon rect;
	private String bound;
	private int top,left,right,bottom;
	private Marker m1, m2, m3, m4;
	//database
	private  DatabaseHandler handler;
	//ui 
	/**
	 * List of collections
	 */
	private ListView mListViewCollection;//list of collections;
	/**
	 * List of products
	 */
	private ListView mListViewProduct;//list of products
	private LinearLayout mMapLayout;//map layout
	private ArrayList<CollectionEntry>mListCollections=null;
	//Adapter
	private CollectionListViewAdapter mCollectionAdapter;
	private ProductAdapter mProductAdapter;
	private TextView mStartdate,mEnddate;
	private Button mSearch;
	private boolean check=false;
	private Button prev,next;
	private Product products=new Product();

	//date start and end date
	ImageView startdateImgb,enddateImgb;
	//boundingbox,location,city
	RadioGroup buttonBySearch;
	//queryMaker
	QueryMaker queryMaker=new QueryMaker();
	Map<Marker, ProductEntry>MarkerEntry=new HashMap<Marker, ProductEntry>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		//url
		Bundle bundle=getIntent().getExtras();
		bundle.getSerializable("url");
		//list of collections
		handler=new DatabaseHandler(this);
		mListCollections=(ArrayList<CollectionEntry>) handler.getAllCollections();
		mListViewCollection=(ListView)findViewById(R.id.productsId);
		mListViewProduct=(ListView)findViewById(R.id.listView1);
		mMapLayout=(LinearLayout)findViewById(R.id.hidegoogleMap);

		mCollectionAdapter=new CollectionListViewAdapter(this, mListCollections);
		mListViewCollection.setAdapter(mCollectionAdapter);
		mListViewCollection.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListViewCollection.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
				dialog.setTitle("Collection choice");
				dialog.setMessage("Remove collection from your favourite list?");
				dialog.setIcon(R.drawable.satellite);
				dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						CollectionEntry item = (CollectionEntry) mListViewCollection.getItemAtPosition(position);
						handler.deleteCollection(item);
						mListCollections=(ArrayList<CollectionEntry>) handler.getAllCollections();
						mCollectionAdapter.clear();
						mCollectionAdapter.addAll(mListCollections);
						mCollectionAdapter.notifyDataSetChanged();
					}
				});
				dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				dialog.show();
				return true;
			}
		});
		mListViewCollection.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				CollectionEntry collection = (CollectionEntry) mListViewCollection.getItemAtPosition(position);
				Intent intent =new Intent(MainActivity.this,MetaDataCollectionActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("collection", collection);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		//search choice
		buttonBySearch=(RadioGroup)findViewById(R.id.radioSearch);
		buttonBySearch.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//find the radiobutton by returned id
			//box bounding 
			//adress
			//location
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) 
				{
				case R.id.radioButton1:
					visibleArea();
					break;
				case R.id.radioButton2:
					final GPSTracker gps = new GPSTracker(MainActivity.this);
					RaduisDialogFragment raduisDialogFragment=new RaduisDialogFragment();
					raduisDialogFragment.show(getSupportFragmentManager(), "Raduis");
					raduisDialogFragment.setListener(new Localisation() 
					{
						@Override
						public void sendRaduis(String raduis) 
						{
							// TODO Auto-generated method stub
							// check if GPS enabled     
							if(gps.canGetLocation())
							{
								LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
								// Creating a criteria object to retrieve provider
								Criteria criteria = new Criteria();
								// Getting the name of the best provider
								String provider = locationManager.getBestProvider(criteria, true);
								// Getting Current Location
								Location location = locationManager.getLastKnownLocation(provider);
								if(location!=null){
									onLocationChanged(location);
								}
								locationManager.requestLocationUpdates(provider, 20000, 0, MainActivity.this);
								//display the pop when we touch the location
								double latitude = gps.getLatitude();
								double longitude = gps.getLongitude();
								bound =Utils.createBBoxFromPointAndDistance(longitude, latitude, Double.parseDouble(raduis));
								DrawCircle(new LatLng(latitude, longitude), raduis);
							}
						}
						@Override
						public void sendCityAndRaduis(String city, String raduis) {
							// TODO Auto-generated method stub
						}
					});
					break;
				case R.id.radioButton3:
					CityRaduisDialogFragment adressDialogFragment=new CityRaduisDialogFragment();
					adressDialogFragment.show(getSupportFragmentManager(),"CityRaduis");
					adressDialogFragment.setListener(new Localisation() {
						@Override
						public void sendCityAndRaduis(String city, String raduis) 
						{
							//get the latlng by cityname giving
							LatLng cityLtln=Utils.geocodeAddress(city,MainActivity.this);
							bound =Utils.createBBoxFromPointAndDistance(cityLtln.longitude, cityLtln.latitude, Double.parseDouble(raduis));
							Toast.makeText(getApplicationContext(),bound, Toast.LENGTH_SHORT).show();
							DrawCircle(cityLtln, raduis);
						}
						@Override
						public void sendRaduis(String raduis) {};
					});
					break;
				default:
					break;
				}
			}
		});
		//get next and previous button
		next=(Button)findViewById(R.id.next);
		prev=(Button)findViewById(R.id.prev);
		prev.setVisibility(View.GONE);
		next.setVisibility(View.GONE);
		//start date
		mStartdate=(TextView)findViewById(R.id.st);
		startdateImgb=(ImageView)findViewById(R.id.imageView1);
		startdateImgb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=true;
				displayCalendar(v);
			}
		});
		//end date
		mEnddate=(TextView)findViewById(R.id.ed);
		enddateImgb=(ImageView)findViewById(R.id.imageView2);
		enddateImgb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=false;
				displayCalendar(v);
			}
		});
		//when we handle the search button
		mSearch=(Button)findViewById(R.id.button1);
		mSearch.setOnClickListener(new OnClickListener()
		{


			@SuppressLint("SimpleDateFormat") @Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				try 
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = sdf.parse(mStartdate.getText().toString());
					Date endDate = sdf.parse(mEnddate.getText().toString());
					if(startDate.compareTo(endDate)>0)
					{
						mEnddate.setError("start date");
					}
					else
					{
						mEnddate.setError(null);
						queryMaker.add(Constant.HTTP_ACCEPT_PARAM,Constant.ATOM_MIME_TYPE);
						queryMaker.add(Constant.PARENT_IDENTIFIER, "EOP:SPOT:ALL");
						queryMaker.add(Constant.START_DATE,mStartdate.getText().toString());
						queryMaker.add(Constant.END_DATE,mEnddate.getText().toString());
						queryMaker.add(Constant.BBOX_PARAM,bound);
						queryMaker.add(Constant.RECORD_SCHEMA_PARAM,Constant.OM_RECORD_SCHEMA);
						System.out.println(Constant.ENTRY_URL+queryMaker.getQuery().toString().trim());
						new EntryTask(MainActivity.this).execute(Constant.ENTRY_URL+queryMaker.getQuery().toString());
						queryMaker.Remove();
					}
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//next products to be display
		next.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EntryTask(MainActivity.this).execute(products.getNext());
			}
		});
		prev.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EntryTask(MainActivity.this).execute(products.getPrev());
			}
		});
		// Loading map
		initilizeMap();
		googleMap.setOnCameraChangeListener(new OnCameraChangeListener() 
		{
			@Override
			public void onCameraChange(CameraPosition position) {
				// TODO Auto-generated method stub
				//visibleArea();      
			}
		});
	}
	/**
	 * 
	 * @return
	 */
	public String visibleArea() 
	{
		// Get boundaries of the screen from the projection
		VisibleRegion bounds = googleMap.getProjection().getVisibleRegion();
		//getting the smallest visible map area.....
		LatLng sw =  bounds.latLngBounds.southwest;
		LatLng ne=bounds.latLngBounds.northeast;
		this.bound=sw.longitude+","+sw.latitude+","+ ne.longitude+","+ne.latitude;
		drawRect(bounds.farLeft, bounds.farRight, bounds.nearRight, bounds.nearLeft);
		//googleMap.animateCamera(CameraUpdateFactory.zoomBy(ZOOM_OUT));
		Toast.makeText(getApplicationContext(), bound, Toast.LENGTH_LONG).show();
		return bound;
	}
	//initialize the calendar dialog for choosen data
	protected void displayCalendar(View v) 
	{
		// TODO Auto-generated method stub
		DatePickerFragment calendarDateDialog=new DatePickerFragment();
		calendarDateDialog.show(getSupportFragmentManager(), "Datepicker");
	}
	//validating a date
	@Override
	public void setDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		String date=year+"-"+month+"-"+day;
		Log.i("date", date);
		if (check)
			mStartdate.setText(date.toString());
		else
		{
			mEnddate.setText(date.toString());
		}
	}
	/**
	 * function to load map. If map is not created it will be created
	 * */
	private void initilizeMap() 
	{
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		// Showing status
		if(status!=ConnectionResult.SUCCESS)// Google Play Services are not available
		{ 
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
			dialog.show();
		}
		else 
		{ // Google Play Services are available
			if (googleMap == null) 
			{
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.googleMap)).getMap();
				// check if map is created successfully or not
				if (googleMap == null) 
				{
					Log.i("map", "null");
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
				else
				{
					// Changing map type
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					googleMap.setMyLocationEnabled(true);
					// Enable / Disable zooming controls
					googleMap.getUiSettings().setZoomControlsEnabled(true);
					// Enable / Disable my location button
					googleMap.getUiSettings().setMyLocationButtonEnabled(true);
					// Enable / Disable Rotate gesture
					googleMap.getUiSettings().setRotateGesturesEnabled(true);
					// Enable / Disable zooming functionality
					googleMap.getUiSettings().setZoomGesturesEnabled(true);
					googleMap.setOnMapLongClickListener(this);
					googleMap.setOnMarkerClickListener(this);
					googleMap.setOnMapClickListener(this);
					googleMap.setOnInfoWindowClickListener(this);
					googleMap.setOnMarkerDragListener(this);
					googleMap.setInfoWindowAdapter(new CustomInfoWindow(getLayoutInflater(), MarkerEntry));
				}
			}
		}
	}
	@Override
	protected void onResume() 
	{
		super.onResume();
		initilizeMap();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// TODO Auto-generated method stub
		switch (item.getItemId()) 
		{
		case R.id.action_preferences:
			mListCollections=(ArrayList<CollectionEntry>) handler.getAllCollections();
			mCollectionAdapter.clear();
			mCollectionAdapter.addAll(mListCollections);
			mCollectionAdapter.notifyDataSetChanged();
			Intent Intent=new Intent(MainActivity.this,PreferencesActivity.class);
			startActivity(Intent);
			break;
		case R.id.action_collectionSearch:
			Intent i=new Intent(MainActivity.this,CollectionSearchActivity.class);
			startActivity(i);
		case R.id.action_location_map:
			mMapLayout.setVisibility(View.VISIBLE);
			mListViewProduct.setVisibility(View.GONE);
			break;
		case R.id.action_list_view:
			mMapLayout.setVisibility(View.GONE);
			mListViewProduct.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		return true;
	}
	@Override
	public boolean onMarkerClick(Marker arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onMapLongClick(LatLng point) 
	{
		// TODO Auto-generated method stub
		//clear the marker on the map
		mProductAdapter.clear();
		mProductAdapter.notifyDataSetChanged();
		googleMap.clear();
		next.setVisibility(View.GONE);
	}
	//draw Marker on the map
	private Marker drawMarker(LatLng point)
	{
		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();
		// Setting latitude and longitude for the marker
		markerOptions.position(point);
		//setting marker icon bitmap
		markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		// Adding marker on the Google Map
		Marker m=googleMap.addMarker(markerOptions);
		return m;
	}
	//these methods are used for the map
	private void drawFootPrints(Product feed)
	{
		// If we have entry already saved
		if(feed.getEntries().size()!=0)
		{
			String lat = "";
			String lng = "";
			LatLngBounds bounds=googleMap.getProjection().getVisibleRegion().latLngBounds;
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			// Iterating through all the locations stored in the entry
			for(int i=0;i<feed.getEntries().size();i++)
			{

				ProductEntry entry=feed.getEntries().get(i);
				// Getting the latitude of the polygon center
				lat = entry.getCenterOf().getLatitude();
				// Getting the longitude of the polygon cente²r
				lng = entry.getCenterOf().getLongitude();
				LatLng latLng=new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
				//if (bounds.contains(latLng))
				//{
				//drawing the polygon for each entry
				PolygonOptions polygonOptions = new PolygonOptions();
				//we get for each entry the polygon points
				polygonOptions.addAll(getPolygon(entry));
				polygonOptions.strokeColor(Color.RED);
				polygonOptions.strokeWidth(3);
				polygonOptions.fillColor(Color.TRANSPARENT);//0x7F00FF00
				//adding polygon to the map
				googleMap.addPolygon(polygonOptions);
				// Drawing marker on the map and get the marker
				Marker m=drawMarker(latLng);
				MarkerEntry.put(m, entry);
				builder.include(m.getPosition());
				Log.i("map",""+MarkerEntry.size());
				//}
			
			}
			//Then obtain a movement description object by using the factory: CameraUpdateFactory:
			int padding = 0; // offset from edges of the map in pixels
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
			//Finally move the map:
			googleMap.moveCamera(cu);
			// Moving CameraPosition to last clicked position
			//	googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
			// Setting the zoom level in the map on last position  is clicked
			//googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
		}
	}
	//getting the polygon of each entry
	public ArrayList<LatLng>getPolygon(ProductEntry entry)
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
	//handled when we clicked on a marker on the map
	@Override
	public void onInfoWindowClick(Marker marker) 
	{
		// TODO Auto-generated method stub
		//getting the cliquable entry
		ProductEntry entry=MarkerEntry.get(marker);
		Bundle bundle = new Bundle();  
		bundle.putSerializable("entry", entry);
		Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
		startActivity(intent);
	}
	@Override
	public void onMapClick(LatLng point)
	{
		// TODO Auto-generated method stub
		//visibleArea();

	}
	public void DrawCircle(LatLng point,String radius)
	{
		CircleOptions circleOptions = new CircleOptions()
		.center(point)   //set cenzoomter
		.radius(Double.parseDouble(radius))   //set radius in km
		.fillColor(Color.TRANSPARENT)  //default
		.strokeColor(Color.RED)
		.strokeWidth(5);
		googleMap.addCircle(circleOptions);
		//move the camera to location
		//drawMarker(point);
		// Move the camera instantly to location with a zoom of 15.
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 7));
		// Zoom in, animating the camera.
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null);
	}
	//Drawing bounding box rectangle on the map
	private void drawRect(LatLng c1, LatLng c2, LatLng c3, LatLng c4) 
	{
		//Remove previously drawn rect
		if (rect != null) rect.remove();
		//Add rectangle to the map
		rect = googleMap.addPolygon(new PolygonOptions()
		.add(c1,c2,c3,c4) // 4 corners, ccw
		.strokeWidth(5)
		.strokeColor(Color.GREEN)
		.fillColor(0x6600ff00));
		left = (int)c1.latitude;
		top = (int) c1.longitude;
		right = (int)c3.latitude;
		bottom = (int) c3.longitude;
		bound=""+left+","+top+","+right+","+bottom;
		Toast.makeText(getApplicationContext(), bound, Toast.LENGTH_LONG).show();
		//Top left marker
		if (m1 != null) m1.remove();
		m1 = googleMap.addMarker(new MarkerOptions()
		.position(c1)
		.draggable(true)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		//Top right marker
		if (m2 != null) m2.remove();
		m2 = googleMap.addMarker(new MarkerOptions()
		.position(c2)
		.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));		
		//Bottom right marker
		if (m3 != null) m3.remove();
		m3 = googleMap.addMarker(new MarkerOptions()
		.position(c3)
		.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		//Bottom right marker
		if (m4 != null) m4.remove();
		m4 = googleMap.addMarker(new MarkerOptions()
		.position(c4)
		.draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	}
	@Override
	//updating the bouding box visible area
	public void onMarkerDragEnd(Marker marker) 
	{
		LatLng p1 = marker.getPosition();
		LatLng p2 = null;
		if (marker.equals(m1)) {
			p2 = m3.getPosition();
		} else if (marker.equals(m2)) {
			p2 = m4.getPosition();
		} else if (marker.equals(m3)) {
			p2 = m1.getPosition();
		} else if (marker.equals(m4)) {
			p2 = m2.getPosition();
		}
		// Make sure the top left corner is the maximum latitude and minimum longitude, etc
		drawRect(new LatLng(Math.max(p1.latitude, p2.latitude), Math.min(p1.longitude, p2.longitude)), 
				new LatLng(Math.max(p1.latitude, p2.latitude), Math.max(p1.longitude, p2.longitude)), 
				new LatLng(Math.min(p1.latitude, p2.latitude), Math.max(p1.longitude, p2.longitude)), 
				new LatLng(Math.min(p1.latitude, p2.latitude), Math.min(p1.longitude, p2.longitude)));
	}
	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onDrag(View v, DragEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	//class for loading the query result
	private class EntryTask extends AsyncTask<String, Void, Product>
	{
		private Product feed=new Product();
		private ProgressDialog dialog;//while loading data
		public EntryTask (MainActivity activity)
		{
			dialog=new ProgressDialog(activity);
		}
		@Override
		protected void onPreExecute()
		{	
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setTitle("Loading results...");
			dialog.setCancelable(true);
			dialog.setMessage("Please wait...");
			dialog.show();
		}
		@Override
		protected void onPostExecute(Product result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//all data have been loaded ,we make the dialog disappear
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			products=result;
			if (products.getEntries().size()==0)
			{
				next.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "products are not found in this area", Toast.LENGTH_LONG).show();;
			}
			else
			{
				//next products
				if (products.getNext()==null)
				{
					next.setVisibility(View.GONE);
				}
				else
				{
					next.setVisibility(View.VISIBLE);
				}
				//previous products
				if (products.getPrev()==null)
				{
					prev.setVisibility(View.GONE);
				}
				else
				{
					prev.setVisibility(View.VISIBLE);
				}
				queryMaker.Remove();
				googleMap.clear();
				drawFootPrints(products); //show products on the map
				//Add products in listview
				mProductAdapter=new ProductAdapter(getApplicationContext(), products.getEntries());
				mListViewProduct.setAdapter(mProductAdapter);
				mProductAdapter.notifyDataSetChanged();
				//products.setEntries(null);
			}
		}
		@Override
		protected Product doInBackground(String... urls) 
		{
			// TODO Auto-generated method stub
			//Loading all entries
			feed=DataParser.parse(urls[0]);
			//converting url to bitmap
			for (ProductEntry entry : feed.getEntries()) {
				String imageURLThumbnail = entry.getThumbnail();
				String imageURLQuickLook=entry.getQuicklook();
				Bitmap bitmapTN = ImageDownloader.downloadAndShowImage(imageURLThumbnail);
				Bitmap bitmapQL = ImageDownloader.downloadAndShowImage(imageURLQuickLook);
				/*BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inSampleSize = 1;
				try {
					bitmapTN = BitmapFactory.decodeStream(new URL(imageURLThumbnail).openStream(),
							null, bmOptions);
					bitmapQL = BitmapFactory.decodeStream(new URL(imageURLQuickLook).openStream(),
							null, bmOptions);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				//passing bitmap to the 
				entry.setBitmapThumbnail(bitmapTN);
				entry.setBitmapQuicklook(bitmapQL);
			}
			return feed;
		}
	}
	@Override
	public void onLocationChanged(Location location) 
	{
		// TODO Auto-generated method stub
		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		// Showing the current location in Google Map
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}
}

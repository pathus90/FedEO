package com.spacebelmobile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adapter.CustomInfoWindow;
import com.adapter.CollectionListViewAdapter;
import com.adapter.ProductAdapter;
import com.databaseManagement.DatabaseHandler;
import com.fragment.CityRadiusDialogFragment;
import com.fragment.DatePickerFragment;
import com.fragment.RadiusDialogFragment;
import com.fragment.DatePickerFragment.OnCalendarChangedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.internal.QueryRequest;
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
import com.interfaces.OnLocalisation;
import com.model.CollectionEntry;
import com.model.PolygonCenter;
import com.model.ProductEntry;
import com.model.Pos;
import com.model.Product;
import com.opensearchquery.QueryMaker;
import com.parsedata.DataParser;
import com.services.GPSTracker;
import com.spacebelmobile.BoundingView.OnLatLongBounds;
import com.spacebelmobile.R;
import com.tasks.ImageDownloader;
import com.utils.Constant;
import com.utils.Utils;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
	private ArrayList<CollectionEntry>mListCollections=null;//List of collections
	//Adapter
	private CollectionListViewAdapter mCollectionAdapter;
	private ProductAdapter mProductAdapter;
	private TextView mStartdate,mEnddate,mTotal;
	private Button mSearch;
	private boolean check=false;
	private ImageButton prev,next;
	private Product product=new Product();
	//date start and end date
	private ImageView startdateImgb,enddateImgb;
	//boundingbox,location,city
	private RadioGroup buttonBySearch;
	//queryMaker
	QueryMaker queryMaker;
	Map<Marker, ProductEntry>MarkerEntry=new HashMap<Marker, ProductEntry>();
	String collection;
	private String[] Cmd = {"Delete","Show Metadata"};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		//Query
		queryMaker=new QueryMaker();
		//list of collections
		handler=new DatabaseHandler(this);
		mListCollections=(ArrayList<CollectionEntry>) handler.getAllCollections();

		mListViewCollection=(ListView)findViewById(R.id.CollectionId);
		registerForContextMenu(mListViewCollection);
		mListViewProduct=(ListView)findViewById(R.id.listView1);
		mMapLayout=(LinearLayout)findViewById(R.id.hidegoogleMap);

		mTotal=(TextView)findViewById(R.id.countProduct);
		//ArrayList<String>listCollection=new ArrayList<String>();
		/*for (CollectionEntry collectionEntry : mListCollections) {
			listCollection.add(collectionEntry.getIdentifier());
		}*/
		CollectionListViewAdapter adapter=new CollectionListViewAdapter(getApplicationContext(), mListCollections);//<String>(this, android.R.layout.simple_list_item_, listCollection);
		mListViewCollection.setAdapter(adapter);
		//mListViewCollection.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
						String item = (String) mListViewCollection.getItemAtPosition(position);
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
				collection = (String) mListViewCollection.getItemAtPosition(position);
				Intent intent =new Intent(MainActivity.this,MetaDataCollectionActivity.class);
				/*Bundle bundle=new Bundle();
				bundle.putSerializable("collection", collection);
				intent.putExtras(bundle);
				startActivity(intent);*/
			} 
		});
		//search choice
		buttonBySearch=(RadioGroup)findViewById(R.id.radioSearch);
		buttonBySearch.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//find the radiobutton by returned id
			//Bounding Box  
			//adress
			//location
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) 
				{
				case R.id.radioButton1:
					visibleArea();
					/*ImageView drawable= (ImageView) findViewById(R.id.bboxImage);
					drawable.setVisibility(View.VISIBLE);
					BoundingView boundingView=new BoundingView(getApplicationContext(), googleMap);
					drawable.setOnTouchListener(boundingView);
					boundingView.setOnLatLongBounds(new OnLatLongBounds() {
						@Override
						public void setLatLngBegin(LatLng latLng) {
							// TODO Auto-generated method stub
							System.out.println(latLng);
						}
						@Override
						public void setLatLngEnd(LatLng latLng) {
							// TODO Auto-generated method stub
							System.out.println("EndLatLong"+latLng);
						}
					});
					drawable.bringToFront();*/
					break;
				case R.id.radioButton2:
					final GPSTracker gps = new GPSTracker(MainActivity.this);
					RadiusDialogFragment raduisDialogFragment=new RadiusDialogFragment();
					raduisDialogFragment.show(getSupportFragmentManager(),"Radius");
					raduisDialogFragment.setListener(new OnLocalisation() 
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
								LatLng latLng=new LatLng(latitude, longitude);
								DrawCircle(latLng, raduis);
							}
						}
						@Override
						public void sendCityAndRaduis(String city, String raduis) {
							// TODO Auto-generated method stub
						}
					});
					break;
				case R.id.radioButton3:
					CityRadiusDialogFragment adressDialogFragment=new CityRadiusDialogFragment();
					adressDialogFragment.show(getSupportFragmentManager(),"CityRaduis");
					adressDialogFragment.setListener(new OnLocalisation() {
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
		//next and previous button
		next=(ImageButton)findViewById(R.id.next);
		prev=(ImageButton)findViewById(R.id.prev);
		next.setVisibility(View.GONE);
		prev.setVisibility(View.GONE);
		//Start date
		mStartdate=(TextView)findViewById(R.id.st);
		startdateImgb=(ImageView)findViewById(R.id.imageView1);
		startdateImgb.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=true;
				displayCalendar(v);
			}
		});
		//End date
		mEnddate=(TextView)findViewById(R.id.ed);
		enddateImgb=(ImageView)findViewById(R.id.imageView2);
		enddateImgb.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=false;
				displayCalendar(v);
			}
		});
		//when we handle the search button
		mSearch=(Button)findViewById(R.id.button1);
		mSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				try 
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = sdf.parse(mStartdate.getText().toString());
					Date endDate = sdf.parse(  mEnddate.getText().toString());
					if(startDate.compareTo(endDate)>0)
					{
						mEnddate.setError("End Date must be greater than Start Date");
					}
					else
					{
						queryMaker.add(Constant.HTTP_ACCEPT_PARAM,Constant.ATOM_MIME_TYPE);
						//queryMaker.add(Constant.PARENT_IDENTIFIER,collection);
						queryMaker.add(Constant.START_DATE,mStartdate.getText().toString()+"T00:00:00Z");
						queryMaker.add(Constant.END_DATE,mEnddate.getText().toString()+"T00:00:00Z");
						queryMaker.add(Constant.BBOX_PARAM,bound);
						queryMaker.add(Constant.RECORD_SCHEMA_PARAM,Constant.OM_RECORD_SCHEMA);

						new EntryTask(MainActivity.this).execute("http://geo.spacebel.be/opensearch/request/?httpAccept=application/atom%2Bxml&parentIdentifier=urn:eop:DLR:EOWEB:IRS-P6.LISS-IV.P-MONO&startDate=2000-08-01T00:00:00Z&endDate=2013-08-12T00:00:00Z&recordSchema=om");
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
		mListViewProduct.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ProductEntry product = (ProductEntry) mListViewProduct.getItemAtPosition(position);
				Intent intent =new Intent(MainActivity.this,ProductMetaDataActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("entry", product);
				intent.putExtras(bundle);
				startActivity(intent);	
			}
		});
		//next products to be display
		next.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EntryTask(MainActivity.this).execute(product.getNext());
			}
		});
		prev.setOnClickListener(new OnClickListener()
		{ 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EntryTask(MainActivity.this).execute(product.getPrev());
			}
		});
		// Loading map
		initilizeMap();
		googleMap.setOnCameraChangeListener(new OnCameraChangeListener() 
		{
			@Override
			public void onCameraChange(CameraPosition position) {
				// TODO Auto-generated method stub
				// visibleArea();      
			}
		});
	}
	/**
	 * 
	 * @return
	 **/
	public String visibleArea() 
	{
		// Get boundaries of the screen from the projection
		VisibleRegion bounds = googleMap.getProjection().getVisibleRegion();
		//getting the smallest visible map area.....
		double north = bounds.latLngBounds.northeast.latitude;
		double east = bounds.latLngBounds.northeast.longitude;
		double south = bounds.latLngBounds.southwest.latitude;
		double west = bounds.latLngBounds.southwest.longitude;
		this.bound=west+","+south+","+ east+","+north;
		drawRect(bounds.farLeft, bounds.farRight, bounds.nearRight, bounds.nearLeft);
		//googleMap.animateCamera(CameraUpdateFactory.zoomBy(ZOOM_OUT));
		Toast.makeText(getApplicationContext(), bound, Toast.LENGTH_LONG).show();
		return bound;
	}
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select The Action");  
		menu.add(0, v.getId(), 0, "Show MetaData");  
		menu.add(0, v.getId(), 0, "Delete Collection"); 
	}

	@Override  
	public boolean onContextItemSelected(MenuItem item)
	{  
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		String number;
		try
		{
		     //number=new CollectionListViewAdapter(this).numberList.get(info.position);
			if(item.getTitle()=="Show MetaData")
			{
			}  
			else if(item.getTitle()=="Delete Collection")
			{
			}  
			else 
			{return false;}  
			return true;  
		}
		catch(Exception e)
		{
			return true;
		}
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
	public void setDate(int year, int month, int day) 
	{
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
			Intent preferenceIntent=new Intent(MainActivity.this,PreferencesActivity.class);
			startActivity(preferenceIntent);
			break;
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
		//clear the marker on the map and clear the list
		if (mProductAdapter!=null)
		{
			mProductAdapter.clear();
			mProductAdapter.notifyDataSetChanged();
		}
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
		// setting marker icon bitmap
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
			PolygonCenter center=new PolygonCenter();
			LatLngBounds bounds=googleMap.getProjection().getVisibleRegion().latLngBounds;
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			// Iterating through all the locations stored in the entry
			for(int i=0;i<feed.getEntries().size();i++)
			{

				ProductEntry entry=feed.getEntries().get(i);
				//getting the center of the polygon
				LatLng latLng=center.getPolygonCenter(entry);

				//drawing the polygon for each entry
				PolygonOptions polygonOptions = new PolygonOptions();
				//we get for each entry the polygon points
				polygonOptions.addAll(center.getPolygon(entry));
				polygonOptions.strokeColor(Color.RED);
				polygonOptions.strokeWidth(3);
				polygonOptions.fillColor(Color.TRANSPARENT);//0x7F00FF00
				//adding polygon to the map
				googleMap.addPolygon(polygonOptions);
				// Drawing marker on the map and get the marker
				Marker m=drawMarker(latLng);
				MarkerEntry.put(m, entry);
				builder.include(m.getPosition());
				System.out.println("drawing");
				Log.i("map",""+MarkerEntry.size());

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
	// fired when we clicked a marker on the map
	@Override
	public void onInfoWindowClick(Marker marker) 
	{
		// TODO Auto-generated method stub
		// getting the cliquable entry
		ProductEntry entry=MarkerEntry.get(marker);
		Intent intent = new Intent(MainActivity.this, ProductMetaDataActivity.class);
		Bundle bundle = new Bundle();  
		// bundle.putSerializable("entry", entry);
		// intent.putExtras(bundle);
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
		googleMap.clear();
		CircleOptions circleOptions = new CircleOptions()
		.center(point)   //set cenzoomter
		.radius(Double.parseDouble(radius)*Constant.KM_TO_M)   //set radius in km
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
		left = 	(int)c1.latitude;
		top = 	(int)c1.longitude;
		right = (int)c3.latitude;
		bottom =(int)c3.longitude;
		bound=""+bottom+","+right+","+top+","+left;
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
		//Bottom left marker
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
	/**
	 * 
	 * @author mpo
	 * this class implements
	 */
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
		protected void onPostExecute(Product result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//all data have been loaded ,we make the dialog disappear
			if (dialog.isShowing()) 
			{
				dialog.dismiss();
			}
			product=result;
			if (product.getEntries().size()==0)
			{
				next.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "products are not found in this area", Toast.LENGTH_LONG).show();;
			}
			else
			{
				//next products
				if (product.getNext()==null)
				{
					next.setVisibility(View.GONE);
				}
				else
				{
					next.setVisibility(View.VISIBLE);
				}
				//previous products
				if (product.getPrev()==null)
				{
					prev.setVisibility(View.GONE);
				}
				else
				{
					prev.setVisibility(View.VISIBLE);
				}
				queryMaker.Remove();
				googleMap.clear();
				drawFootPrints(result); //show products on the map
				mTotal.setText("totals :"+result.getTotalsResults());
				//Add products in listview
				mProductAdapter=new ProductAdapter(getApplicationContext(), product.getEntries());
				mListViewProduct.setAdapter(mProductAdapter);
				mProductAdapter.notifyDataSetChanged();
			}
		}
		@Override
		protected Product doInBackground(String...urls) 
		{
			// TODO Auto-generated method stub
			//Loading all entries
			feed=DataParser.parse(urls[0]);
			//converting url to bitmap
			for (ProductEntry entry : feed.getEntries()) 
			{
				//Parsing url-->Bitmap
				String imageURLThumbnail = entry.getThumbnail();
				//String imageURLQuickLook=entry.getQuicklook();
				Bitmap bitmapTN = ImageDownloader.downloadAndShowImage(imageURLThumbnail);
				//Bitmap bitmapQL = ImageDownloader.downloadAndShowImage(imageURLQuickLook);
				//passing bitmap to the product
				entry.setBitmapThumbnail(bitmapTN);
				//entry.setBitmapQuicklook(bitmapQL);
			}
			return feed;
		}
	}
	@Override
	public void onLocationChanged(Location location) 
	{
		// TODO Auto-generated method stub
		// Creating a LatLng object for the current location
		// LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		// Showing the current location in Google Map
		// googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// Zoom in the Google Map
		// googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("Enable");
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("Disable");
	}
}

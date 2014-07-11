package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Utils {
	/**
	 * 
	 * @param placesName
	 * @return
	 */
	public static JSONObject getLocationFormGoogle(String placesName) 
	{
		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" +placesName+"&ka&sensor=false");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 
	 * @param jsonObject
	 * @return Latlng 
	 */
	public static LatLng getLatLng(JSONObject jsonObject) 
	{

		Double lon = new Double(0);
		Double lat = new Double(0);
		try {
			lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");
			lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new LatLng(lat,lon);
	}
	/**
	 * this method convert a cityName to a Latlng
	 * @param addressInput locationName
	 * @param activity application main activity
	 * @return the city Latitude and Longitude
	 */
	public static  LatLng geocodeAddress(String addressInput,Activity activity) {
		Geocoder geocoder = new Geocoder(activity.getApplicationContext());
		double lat = 0,lng = 0;
		List<Address> addresses;
		addresses = null;
		try {
			addresses = geocoder.getFromLocationName(addressInput,1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			lat = address.getLatitude();
			lng = address.getLongitude();
		}
		return new LatLng(lat, lng);
	}	
	/**
	 * 
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @return
	 */
	public static String createBBoxFromPointAndDistance(double longitude, double latitude, double distance) 
	{
		String bbox = null;		
		double lat_min = latitude - (distance)/Constant.DISTANCE_BETWEEN_TWO_LAT;
		double lat_max = latitude + (distance)/Constant.DISTANCE_BETWEEN_TWO_LAT;
		double scale = Constant.EARTH_RADIUS * 2 * Math.PI/360 * Math.cos(latitude * Math.PI/180);
		double lon_min;
		double lon_max;
		if (scale !=0) {
			lon_min = longitude - (distance)/scale;
			lon_max  = longitude + (distance)/scale;
		} else {
			lon_min = longitude;
			lon_max = longitude;
		}
		DecimalFormat df = new DecimalFormat("#.######");
		bbox = df.format(lon_min) + ","  + df.format(lat_min) + "," + df.format(lon_max) + ","  + df.format(lat_max);
		return bbox;
	}
	/**
	 * 
	 * @param url
	 * @return bitmap
	 */
	public static Bitmap downloadBitmap(String url) {
		final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			getRequest.abort();
			Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public static String ParseCollectionIdentifier(String identifier)
	{
		return identifier.substring(12);
	}
	/**
	 * 
	 * @param center
	 * @param halfWidth
	 * @return
	 */
	public static List<LatLng> createRectangle(LatLng center, double halfWidth) {
	    return Arrays.asList(new LatLng(center.latitude - halfWidth, center.longitude - halfWidth),
	            new LatLng(center.latitude - halfWidth, center.longitude + halfWidth),
	            new LatLng(center.latitude + halfWidth, center.longitude + halfWidth),
	            new LatLng(center.latitude + halfWidth, center.longitude - halfWidth),
	            new LatLng(center.latitude - halfWidth, center.longitude - halfWidth));
	}
}

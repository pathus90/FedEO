
package com.opensearchquery;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.util.Log;


public class SearchRequest {

	private float mEast  = 0;
	private float mSouth = 0;
	private float mWest = 0;
	private float mNorth = 0;

	private float mCenterLat = 0;
	private float mCenterLon = 0;

	private float mRadius = 0;

	private final String TAG = "SearchRequest.java";

	private boolean  isDirtyBox = true; // keep track of last change
	private boolean  isDirtyRadius = true;
	private boolean  isDirtyCenter = true;


	public SearchRequest() {
		// constructor
	}


	public void setBox(String south, String west, String north, String east )
	{
		// 39, 10,  40, 20

		Log.d(TAG, "setBox: " + south + "," + west + "," + north + "," + east);
		try 
		{
			mSouth = Float.parseFloat(south);
			mWest = Float.parseFloat(west);
			mNorth = Float.parseFloat(north);
			mEast = Float.parseFloat(east);
			//  Log.d(TAG, "setBox = " + Float.valueOf(mSouth).toString());
			// recompute RADIUS ... ?
		}
		finally 
		{
			//
		}// try
		isDirtyBox = false;
		setDirtyRadius(true);
		setDirtyCenter(false);
		mCenterLon = (float) ((mEast + mWest) / 2.0);
		mCenterLat = (float) ((mNorth + mSouth) / 2.0);

	} // setArea
	public void setCenter(float lon, float lat) {
		mCenterLon = lon;
		mCenterLat = lat;
		/*
		 mSouth = lat - mRadius;
		 mNorth = lat + mRadius;
		 mWest  = lon - mRadius;
		 mEast  = lon + mRadius;
		 */
		setDirtyCenter(false);
		isDirtyBox = true;
		setDirtyRadius(false);
		Log.d(TAG, "setCenter: " + lon + "," + lat);
	} // setCenter

	public void setRadius(float radius) {
		mRadius = radius;
		setDirtyRadius(false);
		setDirtyCenter(false);
		isDirtyBox    = true;
		Log.d(TAG, "setRadius: " + radius);
		// recompute box around center of bbox 
		// TO DO
	}  // setRadius

	public float getSouth() {
		if (isDirtyBox) {
			mSouth = mCenterLat - mRadius;
			mNorth = mCenterLat + mRadius;
			mWest = mCenterLon - mRadius;
			mEast = mCenterLon + mRadius;
			isDirtyBox = false;
		} // if
		Log.d(TAG, "getSouth: " + mSouth);
		return mSouth;
	}
	public float getWest() {
		if (isDirtyBox) {
			mSouth = mCenterLat - mRadius;
			mNorth = mCenterLat + mRadius;
			mWest = mCenterLon - mRadius;
			mEast = mCenterLon + mRadius;
			isDirtyBox = false;
		} // if
		Log.d(TAG, "getWest: " + mWest);
		return mWest;
	}
	public float getNorth() {
		if (isDirtyBox) {
			mSouth = mCenterLat - mRadius;
			mNorth = mCenterLat + mRadius;
			mWest = mCenterLon - mRadius;
			mEast = mCenterLon + mRadius;
			isDirtyBox = false;
		} // if
		Log.d(TAG, "getNorth: " + mNorth);
		return mNorth;
	}
	public float getEast() {
		if (isDirtyBox) {
			mSouth = mCenterLat - mRadius;
			mNorth = mCenterLat + mRadius;
			mWest = mCenterLon - mRadius;
			mEast = mCenterLon + mRadius;
			isDirtyBox = false;
		} // if
		Log.d(TAG, "getEast: " + mEast);
		return mEast;
	}
	public float getRadius() {
		Log.d(TAG, "getRadius: " + mRadius);
		return mRadius;
	}
	public float getLongitude() {
		Log.d(TAG, "getLongitude: " + mCenterLon);
		return mCenterLon;
	}

	public float getLatitude() {
		Log.d(TAG, "getLatitude: " + mCenterLat);
		return mCenterLat;
	}

	public boolean isDirtyRadius() {
		return isDirtyRadius;
	}

	public void setDirtyRadius(boolean isDirtyRadius) {
		this.isDirtyRadius = isDirtyRadius;
	}

	public boolean isDirtyCenter() {
		return isDirtyCenter;
	}

	public void setDirtyCenter(boolean isDirtyCenter) {
		this.isDirtyCenter = isDirtyCenter;
	}

	public boolean isDirtyBox() {
		return isDirtyBox;
	}

	public void setDirtyBox(boolean isDirtyBox) {
		this.isDirtyBox = isDirtyBox;
	}
	public LatLngBounds  toBounds(LatLng j,LatLng k) {

		double latMin, latMax, lngMin, lngMax;
		LatLng sw, ne;

		latMin = Math.min(j.latitude, k.latitude);
		latMax = Math.max(j.latitude, k.latitude);

		lngMin = Math.min(j.longitude, k.longitude);
		lngMax = Math.max(j.longitude, k.longitude);

		sw = new LatLng(latMin, lngMin);
		ne =new LatLng(latMax, lngMax);
		LatLngBounds bounds=new LatLngBounds(sw, ne);
		return bounds;
	}
} // SearchRequest
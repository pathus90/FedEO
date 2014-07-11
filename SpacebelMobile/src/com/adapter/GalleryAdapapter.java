package com.adapter;

import com.spacebelmobile.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class GalleryAdapapter extends BaseAdapter {

	private Context ctx;
	int imageBackground;
	 Integer [] pics;

	public GalleryAdapapter(Context c,Integer[] _pics) {
		ctx = c;
		pics=_pics;
		TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
		imageBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
		a.recycle();
	}

	@Override
	public int getCount() {

		return pics.length;
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ImageView iv = new ImageView(ctx);
		iv.setImageResource(pics[arg0]);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setLayoutParams(new Gallery.LayoutParams(150,120));
		iv.setBackgroundResource(imageBackground);
		return iv;
	}
}

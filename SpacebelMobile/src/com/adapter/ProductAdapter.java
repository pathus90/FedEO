package com.adapter;
import java.util.ArrayList;

import com.model.ProductEntry;
import com.spacebelmobile.R;
import com.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<ProductEntry> {
	private LayoutInflater  inflater;
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<ProductEntry> products;

	public ProductAdapter ( Context ctx,ArrayList<ProductEntry> objects) 
	{
		super( ctx,R.layout.productlistview, objects );
		inflater = LayoutInflater.from( ctx );
		context=ctx;
		products=objects;
	}
	@Override
	public int getPosition(ProductEntry item) {
		// TODO Auto-generated method stub
		return getPosition(item);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return products.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		Holder holder;
		if (view == null) 
		{
			view = inflater.inflate(R.layout.productlistview, null, false);
			holder = new Holder(view);
			view.setTag(holder);
		}
		else
		{
			holder=(Holder) view.getTag();
		}
		holder.setEntry(getItem(position));
		return view;
	}
	private  class Holder
	{
		TextView title;
		TextView orbittype;
		TextView serialNumber;
		TextView orbitNumber;	
		TextView serialIdentifier;
		TextView orbitDirection;
		TextView instrumentShortName;
		TextView sensorOperationMode;
		TextView LastOrbitNumber;
		ImageView productImage;
		public Holder(View view)
		{
			title=(TextView)view.findViewById(R.id.legendName);
			productImage=(ImageView)view.findViewById(R.id.image);
			
			orbittype=(TextView)view.findViewById(R.id.textView1);
			serialNumber=(TextView)view.findViewById(R.id.textView2);
			orbitNumber=(TextView)view.findViewById(R.id.textView3);
			serialIdentifier=(TextView)view.findViewById(R.id.textView4);
			instrumentShortName=(TextView)view.findViewById(R.id.TextView05);
			sensorOperationMode=(TextView)view.findViewById(R.id.TextView06);
			LastOrbitNumber=(TextView)view.findViewById(R.id.TextView07);
		}
		public void setEntry(ProductEntry product)
		{
			title.setText(Utils.ParseCollectionIdentifier(product.getTitle()));
			orbittype.setText(product.getOrbiteType());
			serialNumber.setText(product.getSensorType());
			orbitNumber.setText(product.getOrbitNumber());
			serialIdentifier.setText(product.getSerialIdentifier());
			//orbitDirection.setText(product.getOrbitDirection());
			instrumentShortName.setText(product.getInstrumentShortName());
			sensorOperationMode.setText(product.getSensorOperationalMode());
			//	LastOrbitNumber.setText(product.getLastOrbitreNumber());
			productImage.setImageBitmap(product.getBitmapThumbnail());
		}
	}
}

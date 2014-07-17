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
		TextView title,platformShortName,SensorResolutiion,startDate,endDate;
		TextView orbittype;
		TextView sensortype;
		TextView orbitNumber;	
		TextView serialIdentifier;
		TextView orbitDirection;
		TextView instrumentShortName;
		TextView sensorOperationMode;
		TextView LastOrbitNumber;
		ImageView productImage;
		public Holder(View view)
		{
			title=(TextView)view.findViewById(R.id.ltitle);
			productImage=(ImageView)view.findViewById(R.id.lbadge);
			platformShortName=(TextView)view.findViewById(R.id.lsnippet);
			orbittype=(TextView)view.findViewById(R.id.lorbiteType);
			serialIdentifier=(TextView)view.findViewById(R.id.lserialIdentifier);
			orbitNumber=(TextView)view.findViewById(R.id.lOrbitNumber);
			sensortype=(TextView)view.findViewById(R.id.lsensorType);
			SensorResolutiion=(TextView)view.findViewById(R.id.lsensorResolution);
			instrumentShortName=(TextView)view.findViewById(R.id.linstrumentShortName);
			sensorOperationMode=(TextView)view.findViewById(R.id.lsensorOperationalMode);
			LastOrbitNumber=(TextView)view.findViewById(R.id.llastOrbitNumber);
			orbitDirection=(TextView)view.findViewById(R.id.lorbitDirection);
			startDate=(TextView)view.findViewById(R.id.lstartDate);
			endDate=(TextView)view.findViewById(R.id.lendDate);
		}
		public void setEntry(ProductEntry product)
		{
			productImage.setImageBitmap(product.getBitmapThumbnail());
			title.setText(Utils.ParseCollectionIdentifier(product.getTitle()));
			
			orbittype.setText(product.getOrbiteType());
			sensortype.setText(product.getSensorType());
			orbitNumber.setText(product.getOrbitNumber());
			serialIdentifier.setText(product.getSerialIdentifier());
			
			instrumentShortName.setText(product.getInstrumentShortName());
		    sensorOperationMode.setText(product.getSensorOperationalMode());
		    orbitDirection.setText(product.getOrbitDirection());
		    LastOrbitNumber.setText(product.getLastOrbitreNumber());
		    platformShortName.setText(product.getShortName());
		    SensorResolutiion.setText(product.getSensorResolution());
		    startDate.setText(product.getStartDate().replaceAll("\\s", ""));
		    endDate.setText(product.getEndDate().replaceAll("\\s", ""));
			
		}
	}
}

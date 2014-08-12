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
/**
 * class
 * @author mpo
 * @version 1.0
 */
public class ProductAdapter extends ArrayAdapter<ProductEntry> {
	private LayoutInflater  inflater;
	private Context context;
	private ArrayList<ProductEntry> products;
	public ProductAdapter ( Context ctx,ArrayList<ProductEntry> objects) 
	{
		super( ctx,0, objects );
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
		TextView platformShortName;
		TextView sensorResolution;
		TextView startDate;
		TextView endDate;
		TextView orbitType;
		TextView sensorType;
		TextView orbitNumber;	
		TextView serialIdentifier;
		TextView orbitDirection;
		TextView instrumentShortName;
		TextView sensorOperationMode;
		TextView lastOrbitNumber;
		ImageView productImage;
		public Holder(View view)
		{
			title=(TextView)view.findViewById(R.id.ltitle);
			productImage=(ImageView)view.findViewById(R.id.lbadge);
			platformShortName=(TextView)view.findViewById(R.id.lsnippet);
			orbitType=(TextView)view.findViewById(R.id.lorbiteType);
			serialIdentifier=(TextView)view.findViewById(R.id.lserialIdentifier);
			orbitNumber=(TextView)view.findViewById(R.id.lOrbitNumber);
			sensorType=(TextView)view.findViewById(R.id.lsensorType);
			sensorResolution=(TextView)view.findViewById(R.id.lsensorResolution);
			instrumentShortName=(TextView)view.findViewById(R.id.linstrumentShortName);
			sensorOperationMode=(TextView)view.findViewById(R.id.lsensorOperationalMode);
			lastOrbitNumber=(TextView)view.findViewById(R.id.llastOrbitNumber);
			orbitDirection=(TextView)view.findViewById(R.id.lorbitDirection);
			startDate=(TextView)view.findViewById(R.id.lstartDate);
			endDate=(TextView)view.findViewById(R.id.lendDate);
		}
		public void setEntry(ProductEntry product)
		{
			productImage.setImageBitmap(product.getBitmapThumbnail());
			title.setText(Utils.ParseCollectionIdentifier(product.getTitle()));
			orbitType.setText(product.getOrbitType());
			sensorType.setText(product.getSensorType());
			orbitNumber.setText(product.getOrbitNumber());
			serialIdentifier.setText(product.getSerialIdentifier());
			instrumentShortName.setText(product.getInstrumentShortName());
		    sensorOperationMode.setText(product.getSensorOperationalMode());
		    orbitDirection.setText(product.getOrbitDirection());
		    lastOrbitNumber.setText(product.getLastOrbitreNumber());
		    platformShortName.setText(product.getShortName());
		    sensorResolution.setText(product.getSensorResolution());
		  
		    startDate.setText(product.getStartDate().replaceAll("\\s", ""));
		    endDate.setText(product.getEndDate().replaceAll("\\s", ""));
		}
	}
}

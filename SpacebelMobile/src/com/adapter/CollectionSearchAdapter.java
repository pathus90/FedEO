package com.adapter;
import java.util.ArrayList;

import com.model.CollectionEntry;
import com.spacebelmobile.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author mpo
 *
 */
public class CollectionSearchAdapter extends ArrayAdapter<CollectionEntry> 
{

	OnChooseCollection listener;
	public interface OnChooseCollection
	{
		public void addCollection(CollectionEntry collection);
		public void showMetaData(CollectionEntry collection);
	}
	/**
	 * Contains the list of objects that represent the data of the collections.
	 * The content of this list is referred to as "the array" in the documentation.
	 */
	private ArrayList<CollectionEntry> mCollections;

	public CollectionSearchAdapter(Context context, ArrayList<CollectionEntry>collections) 
	{
		super(context, R.layout.collectioncustomview,collections);
		this.mCollections=collections;
	}
	@Override
	public int getPosition(CollectionEntry item) {
		// TODO Auto-generated method stub
		return getPosition(item);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCollections.size();
	}
	/**
	 * 
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Holder holder;
		if (view == null) 
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.collectioncustomview, parent, false);
			holder = new Holder(view);
			holder.mIdentifier=(TextView)view.findViewById(R.id.identifier);
			holder.mCollectionTitle = (TextView) view.findViewById(R.id.title);
			holder.mAbstractDescription=(TextView)view.findViewById(R.id.abstractCollection);
			holder.mButtonAddListCollection=(Button)view.findViewById(R.id.ButtonAdd);
			holder.mButtonShowMetaData=(Button)view.findViewById(R.id.ButtonShowMetaData);
			holder.imageOrganisationName=(ImageView)view.findViewById(R.id.iconorganisation);
			view.setTag(holder);
		}
		else
		{
			holder=(Holder) view.getTag();
		}
		holder.setEntry(getItem(position));
		holder.getButtonAddListCollection().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CollectionEntry collection=getItem(position);
				listener.addCollection(collection);
			}
		});
		holder.getButtonShowMetaData().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.showMetaData(getItem(position));
			}
		});
		return view;
	}
	public void setOnAddClickedListener(final OnChooseCollection listener) {
		this.listener = listener;
	}
	/**
	 * 
	 * @author mpo
	 *
	 */
	static class Holder
	{
		/**
		 *
		 */
		TextView  mCollectionTitle;
		TextView  mAbstractDescription;
		TextView  mIdentifier;
		Button 	  mButtonAddListCollection;
		Button    mButtonShowMetaData;
		ImageView imageOrganisationName;
		public Holder(View view) 
		{
			mIdentifier=(TextView)view.findViewById(R.id.identifier);
			mCollectionTitle = (TextView) view.findViewById(R.id.title);
			mAbstractDescription=(TextView)view.findViewById(R.id.abstractCollection);
			mButtonAddListCollection=(Button)view.findViewById(R.id.ButtonAdd);
			mButtonShowMetaData=(Button)view.findViewById(R.id.ButtonShowMetaData);
			imageOrganisationName=(ImageView)view.findViewById(R.id.iconorganisation);
		}
		public void setEntry(CollectionEntry collection)
		{
			mCollectionTitle.setText(collection.getTitle());
			mIdentifier.setText(collection.getIdentifier());
			mAbstractDescription.setText(collection.getDescription());
			if (collection.getIdentifier().contains("ESA"))
			{
				imageOrganisationName.setImageResource(R.drawable.esa);
			}
			else if (collection.getIdentifier().contains("VITO"))
			{
				imageOrganisationName.setImageResource(R.drawable.vito);
			}
			else if(collection.getIdentifier().contains("SPOT"))
			{
				imageOrganisationName.setImageResource(R.drawable.spotimage);
			}
			else if (collection.getIdentifier().contains("EUM")||(collection.getIdentifier().contains("EUMETSAT")))
			{
				imageOrganisationName.setImageResource(R.drawable.eumetsat);
			}
			else
			{
				//imageOrganisationName.setImageResource(Color.parseColor("#80000000"));
			}
		}
		public TextView getButtonAddListCollection()
		{
			return mButtonAddListCollection;
		}
		public TextView getButtonShowMetaData()
		{
			return mButtonShowMetaData;
		}
	}
}

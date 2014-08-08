package com.adapter;

import java.util.ArrayList;
import com.model.CollectionEntry;
import com.spacebelmobile.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class CollectionListViewAdapter extends ArrayAdapter<CollectionEntry>
{
	private ArrayList<CollectionEntry> entries;
	private Context context;
	public CollectionListViewAdapter(Context context,ArrayList<CollectionEntry>entries) 
	{
		// TODO Auto-generated constructor stub
		super(context,0,entries);
		this.entries=entries;
		this.context=context;	
	}
	@Override
	public int getPosition(CollectionEntry item) 
	{
		// TODO Auto-generated method stub
		return getPosition(item);
	}
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return entries.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		View view = convertView;
		Holder holder;
		if (view == null) 
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			view = inflater.inflate(R.layout.listviewadapter, null, false);
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
	public class Holder
	{ 
		public RadioButton mRadioBtn;
		public TextView mTitle;
		public Holder(View view)
		{
			mTitle=(TextView)view.findViewById(R.id.title);
			mRadioBtn=(RadioButton)view.findViewById(R.id.radioButton1);
		}
		public void setEntry(CollectionEntry entry)
		{
			mTitle.setText( entry.getIdentifier());
		}
	}
}

package com.spacebelmobile;

import java.util.ArrayList;
import java.util.List;

import com.adapter.CollectionSearchAdapter;
import com.adapter.CollectionSearchAdapter.OnChooseCollection;
import com.databaseManagement.DatabaseHandler;
import com.model.CollectionFeed;
import com.model.CollectionEntry;
import com.tasks.CollectionsTask;
import com.tasks.CollectionsTask.onTaskCollectionComplete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchCollectionsResultsActivity extends Activity implements onTaskCollectionComplete
{
	private ListView listViewCollection;
	private CollectionFeed collections=null;
	CollectionSearchAdapter adapter;
	Button prev,next;
	DatabaseHandler handler;
	TextView count;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_collections_results);
		listViewCollection=(ListView)findViewById(R.id.listView1);
		count=(TextView)findViewById(R.id.count);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		//setting the database to save the chosen collection
		handler=new  DatabaseHandler(this);
		//get data from the query 
		Bundle bundle=getIntent().getExtras();
		collections=(CollectionFeed)bundle.getSerializable("collections");
		//passing data to the listview
		adapter=new CollectionSearchAdapter(getApplicationContext(), collections.getCollectionEntries());
		listViewCollection.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		adapter.setNotifyOnChange(true);
		//counting the displayed data
		count.setText("("+collections.getCollectionEntries().size()+")"  );
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		//buttons prev and nex
		next=(Button)findViewById(R.id.next);
		prev=(Button)findViewById(R.id.prev);
		if (collections.getNext()==null)
		{
			next.setVisibility(View.GONE);
		}
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("previous", "previous");
				if (collections.getPrevious()!=null)
					new CollectionsTask(SearchCollectionsResultsActivity.this).execute(collections.getPrevious());
			}
		});
		next.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CollectionsTask(SearchCollectionsResultsActivity.this).execute(collections.getNext());
			}
		});

		this.adapter.setOnAddClickedListener(new OnChooseCollection() 
		{
			@Override
			public void addCollection(final CollectionEntry collection) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog=new AlertDialog.Builder(SearchCollectionsResultsActivity.this);
				dialog.setTitle("Collection choice");
				dialog.setMessage("Add this collection in your favourite list?");
				dialog.setIcon(R.drawable.satellite);
				dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//check if a collection is already added to the preferences Collection List
						boolean check=handler.containsCollection(collection);
						if (check)
						{
							Log.i("added", "added");
						}
						else
						{
							handler.addCollection(collection);
						}
					}
				});
				dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
			@Override
			public void showMetaData(CollectionEntry collection) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SearchCollectionsResultsActivity.this,MetaDataCollectionActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("collection", collection);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_collections_results, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				// This activity is NOT part of this app's task, so create a new task
				// when navigating up, with a synthesized back stack.
				TaskStackBuilder.create(this)
				// Add all of this activity's parents to the back stack
				.addNextIntentWithParentStack(upIntent)
				// Navigate up to the closest parent
				.startActivities();
			} else {
				// This activity is part of this app's task, so simply
				// navigate up to the logical parent activity.
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//laoding next datas
	@Override
	public void onTaskCollectionCompleted(CollectionFeed collections)
	{
		// TODO Auto-generated method stub
		adapter.clear();
		adapter.addAll(collections.getCollectionEntries());
		adapter.notifyDataSetChanged();
		this.collections=collections;
		//Log.i("previous", collections.getPrevious());
	}
	@Override
	public void OnServerError() {
		// TODO Auto-generated method stub
		
	}
}

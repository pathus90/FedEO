package com.spacebelmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.CollectionListViewAdapter;
import com.adapter.CollectionSearchAdapter;
import com.adapter.CollectionSearchAdapter.OnChooseCollection;
import com.databaseManagement.DatabaseHandler;
import com.fragment.DatePickerFragment;
import com.fragment.DatePickerFragment.OnCalendarChangedListener;
import com.model.CollectionEntry;
import com.model.CollectionFeed;
import com.opensearchquery.QueryMaker;
import com.tasks.CollectionsTask;
import com.tasks.CollectionsTask.onTaskCollectionComplete;
import com.utils.Constant;

public class CollectionSearchActivity extends FragmentActivity implements onTaskCollectionComplete,
OnCalendarChangedListener
{
	private EditText keyword,organisationName,title,subject,startDate,endDate,eoplatform,instrument;
	private LinearLayout optionLayout;
	private Button searchButton;
	private ImageButton next,prev;
	private QueryMaker queryMaker;
	private CheckBox displayOption;
	private ListView listViewCollection;
	private boolean check=false;
	private DatabaseHandler handler;
	private String[] Cmd = {"Delete","Show Metadata"};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		setContentView(R.layout.activity_collection_search);

		keyword=(EditText)findViewById(R.id.searchTerms);
		organisationName=(EditText)findViewById(R.id.organisationName);
		title=(EditText)findViewById(R.id.title);
		subject=(EditText)findViewById(R.id.subject);
		eoplatform=(EditText)findViewById(R.id.eoplatform);
		instrument=(EditText)findViewById(R.id.eoinstrument);
		optionLayout=(LinearLayout)findViewById(R.id.option);
		displayOption=(CheckBox)findViewById(R.id.displayOption);
		startDate=(EditText)findViewById(R.id.startDate);
		endDate=(EditText)findViewById(R.id.endDate);
		searchButton=(Button)findViewById(R.id.find);
		/*ListView*/
		listViewCollection = (ListView)findViewById(R.id.CollectionListView);

		//Database
		handler=new  DatabaseHandler(this);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		//setting the database to save the chosen collection

		//passing data to the listview

		displayOption.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (displayOption.isChecked())
				{
					optionLayout.setVisibility(View.VISIBLE);
				}
				else
				{
					optionLayout.setVisibility(View.GONE);
				}
			}
		});
		startDate.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=true;
				displayCalendar(v);
			}
		});
		endDate.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check=false;
				displayCalendar(v);
			}
		});
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				queryMaker=new QueryMaker();
				queryMaker.add(Constant.HTTP_ACCEPT_PARAM,Constant.ATOM_MIME_TYPE);
				queryMaker.add(Constant.QUERY,keyword.getText().toString());
				queryMaker.add(Constant.TITLE,title.getText().toString());
				queryMaker.add(Constant.SUBJECT_PARAM,subject.getText().toString());
				queryMaker.add(Constant.ORGANIZATION_NAME,organisationName.getText().toString());
				queryMaker.add(Constant.PLATFORM,eoplatform.getText().toString());
				queryMaker.add(Constant.INSTRUMENT,instrument.getText().toString());
				queryMaker.add(Constant.START_DATE,startDate.getText().toString());
				queryMaker.add(Constant.END_DATE,endDate.getText().toString());
				queryMaker.add(Constant.PARENT_IDENTIFIER,"EOP:ESA:FEDEO:COLLECTIONS");
				queryMaker.add(Constant.RECORD_SCHEMA_PARAM,Constant.SERVER_CHOICE_RECORD_SCHEMA);
				System.out.println(Constant.ENTRY_URL+queryMaker.getQuery());
				new CollectionsTask(CollectionSearchActivity.this).execute(Constant.ENTRY_URL+queryMaker.getQuery().toString());
			}
		});
	}
	
	/**
	 * Loading all collection
	 * 
	 * @param collections
	 *
	 */
	@Override
	public void onTaskCollectionCompleted(CollectionFeed collections) 
	{
		// TODO Auto-generated method stub
		if (collections.getCollectionEntries().size()==0)
		{            
			Toast.makeText(getApplicationContext(),"no collection found ", Toast.LENGTH_LONG).show();
			queryMaker.Remove();
		}
		else
		{
			//Show metada details
			CollectionSearchAdapter collectionListViewAdapter=new CollectionSearchAdapter(getBaseContext(), collections.getCollectionEntries());
			collectionListViewAdapter.notifyDataSetChanged();
			collectionListViewAdapter.setNotifyOnChange(true);
			listViewCollection.setAdapter(collectionListViewAdapter);
			collectionListViewAdapter.setOnAddClickedListener(new OnChooseCollection() 
			{
				@Override
				public void addCollection(final CollectionEntry collection) {
					// TODO Auto-generated method stub
					AlertDialog.Builder dialog=new AlertDialog.Builder(CollectionSearchActivity.this);
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
					Intent intent=new Intent(CollectionSearchActivity.this,MetaDataCollectionActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("collection", collection);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}
		//queryMaker.Remove();
	}

	public void OnServerError() 
	{
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_LONG);
	}
	@Override
	public void setDate(int year, int month, int day) 
	{
		// TODO Auto-generated method stub
		String date=year+"-"+month+"-"+day;
		Log.i("date", date);
		if (check)
			startDate.setText(date.toString());
		else
		{
			endDate.setText(date.toString());
		}
	}
	// initialize the calendar dialog for choosen data
	protected void displayCalendar(View v) 
	{
		// TODO Auto-generated method stub
		DatePickerFragment calendarDateDialog=new DatePickerFragment();
		calendarDateDialog.show(getSupportFragmentManager(),"Datepicker");
	}	
}

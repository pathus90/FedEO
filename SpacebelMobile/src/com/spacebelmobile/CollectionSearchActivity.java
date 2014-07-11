package com.spacebelmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.fragment.DatePickerFragment;
import com.fragment.DatePickerFragment.OnCalendarChangedListener;
import com.model.CollectionFeed;
import com.opensearchquery.QueryMaker;
import com.tasks.CollectionsTask;
import com.tasks.CollectionsTask.onTaskCollectionComplete;
import com.utils.Constant;

public class CollectionSearchActivity extends FragmentActivity implements onTaskCollectionComplete,
OnCalendarChangedListener
{
	private EditText keyword,organisationName,title,subject,startDate,endDate;
	private LinearLayout optionLayout;
	private Button searchButton;
	private QueryMaker queryMaker;
	CheckBox displayOption;
	private boolean check=false;
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
		optionLayout=(LinearLayout)findViewById(R.id.option);
		displayOption=(CheckBox)findViewById(R.id.displayOption);
		startDate=(EditText)findViewById(R.id.startDate);
		endDate=(EditText)findViewById(R.id.endDATE);
		searchButton=(Button)findViewById(R.id.find);
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
		queryMaker=new QueryMaker();
		queryMaker.add("httpAccept",Constant.ATOM_MIME_TYPE);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				queryMaker.add(Constant.QUERY,keyword.getText().toString());
				queryMaker.add(Constant.TITLE,title.getText().toString());
				queryMaker.add(Constant.ORGANIZATION_NAME,organisationName.getText().toString());
				queryMaker.add(Constant.START_DATE,startDate.getText().toString());
				queryMaker.add(Constant.END_DATE,endDate.getText().toString());
				queryMaker.add(Constant.PARENT_IDENTIFIER,"EOP:ESA:FEDEO:COLLECTIONS");
				queryMaker.add(Constant.RECORD_SCHEMA_PARAM,Constant.SERVER_CHOICE_RECORD_SCHEMA);
				new CollectionsTask(CollectionSearchActivity.this).execute(Constant.ENTRY_URL+queryMaker.getQuery());
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
			Intent intent=new Intent(CollectionSearchActivity.this,SearchCollectionsResultsActivity.class);
			Bundle bundle=new Bundle();
			//pass Data 
			bundle.putSerializable("collections", collections);
			intent.putExtras(bundle);
			startActivity(intent);
			queryMaker.Remove();
		}
	}
	@SuppressLint("ShowToast") @Override
	public void OnServerError() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_LONG);
	}
	@Override
	public void setDate(int year, int month, int day) {
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
		calendarDateDialog.show(getSupportFragmentManager(), "Datepicker");
	}
}

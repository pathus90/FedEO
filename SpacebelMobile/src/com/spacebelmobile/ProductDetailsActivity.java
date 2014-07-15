package com.spacebelmobile;

import com.interfaces.SendProduct;
import com.model.CollectionEntry;
import com.model.ProductEntry;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ProductDetailsActivity extends Activity implements SendProduct{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		setContentView(R.layout.activity_product_details);
		//Bundle bundle=getIntent().getExtras();
		//ProductEntry entry=(ProductEntry)bundle.getSerializable("entry");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void Send(ProductEntry entry) {
		// TODO Auto-generated method stub
		Log.i("entry",""+entry.getShortName());
	}
}

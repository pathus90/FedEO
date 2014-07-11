package com.spacebelmobile;

import com.spacebelmobile.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class PreferencesActivity extends PreferenceActivity {

	static final int DIALOG_ABOUT_ID = 0;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_seeting);
		// Get the custom preference
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#598e96")));
		Preference customPref = (Preference) findPreference("aboutPref");
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://smaad.spacebel.be/opensearch/description.xml"))); 
				return true;
			}
		});
		Preference customPref2 = (Preference) findPreference("aboutSpacebelPref");
		customPref2.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.spacebel.be"))); 
				return true;
			}
		});
		Preference customPref3 = (Preference) findPreference("aboutEsaPref");
		customPref3.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.esa.int/About_Us/Welcome_to_ESA"))); 
				return true;
			}
		});
		Preference customPref4 = (Preference) findPreference("aboutHmaPref");
		customPref4.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.esa.int/About_Us/ESA_Publications/ESA_TM-21_Heterogeneous_Missions_Accessibility"))); 
				return true;
			}
		});

	} // onCreate
	protected Dialog onCreateDialog(int id) {    
		Dialog dialog = null;  
		AlertDialog.Builder builder;
		switch(id) {    
		case DIALOG_ABOUT_ID:        
			// Create About Dialog  
			builder = new AlertDialog.Builder(this);
			builder.setMessage(
					getResources().getText(R.string.app_name) +
					"\nVersion " +

            			"\n(c) 2013 "
					)      
					.setCancelable(true) ;      
			dialog = builder.create();		
			break;
		default:        
			dialog = null; 
		} // switch   
		return dialog;
	} // onCreateDialog   
} // class

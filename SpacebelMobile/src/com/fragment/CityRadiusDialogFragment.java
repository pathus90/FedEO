package com.fragment;
import com.interfaces.OnLocalisation;
import com.spacebelmobile.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
public class CityRadiusDialogFragment extends DialogFragment
{  
	private Button mButtonOk,mButtonCancel;  
	private EditText mEditTextAdress,mEditTextRaduis;  
	OnLocalisation mListener;  
	String text = "";  
	@Override  
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{  
		final Dialog dialog = new Dialog(getActivity());  
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		dialog.setContentView(R.layout.adressdialog);  
		//dialog.show();  
		mButtonOk =(Button) dialog.findViewById(R.id.ok);  
		mButtonCancel =(Button) dialog.findViewById(R.id.cancel); 
		mEditTextAdress =(EditText) dialog.findViewById(R.id.city);    
		mEditTextRaduis =(EditText) dialog.findViewById(R.id.raduis); 
		
		mButtonOk.setOnClickListener(new OnClickListener() 
		{  
			@Override  
			public void onClick(View v) 
			{  
				mListener.sendCityAndRaduis(mEditTextAdress.getText().toString(),mEditTextRaduis.getText().toString());  
				dismiss();  
			}  
		}); 
		mButtonCancel.setOnClickListener(new OnClickListener() 
		{  
			@Override  
			public void onClick(View v) 
			{  
				dismiss();  
			}  
		}); 
		return dialog;  
	}  
	public void setListener(OnLocalisation listener)
	{
		this.mListener=listener;
	}
}  

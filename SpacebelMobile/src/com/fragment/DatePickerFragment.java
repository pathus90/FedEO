package com.fragment;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
/**
 * 
 * @author mpo
 *
 */
public class DatePickerFragment extends DialogFragment implements 
DatePickerDialog.OnDateSetListener 
{ 
	protected OnCalendarChangedListener mListener;
	public interface OnCalendarChangedListener {
		public void setDate(int year,int month,int day);
	}
	/**
	 * Attach a listener.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnCalendarChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnCalendarChangedListener");
		}
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		 //Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		mListener.setDate(year, monthOfYear+1, dayOfMonth);
	}
} 
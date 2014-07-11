package com.spacebelmobile;

import com.google.android.gms.maps.GoogleMap;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
class BoundingView extends ImageView implements OnClickListener
{ 
	private boolean isDrawing=false;
	Point begin,end;
	GoogleMap map;
	Paint paint=new Paint();
	public BoundingView(Context context)
	{
		super(context);
	}
	public BoundingView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	public boolean onTouch(View v, MotionEvent event) 
	{
		switch(event.getAction()) 
		{
			case MotionEvent.ACTION_DOWN:
				isDrawing = true; 
				begin.x = (int) event.getX();
				begin.y = (int) event.getY();
				end.x = (int) event.getX();
				end.y = (int) event.getY();
				invalidate(); 
				break;
		case MotionEvent.ACTION_MOVE:
				end.x = (int) event.getX();
				end.y = (int) event.getY();
				invalidate(); 
				break;
		case MotionEvent.ACTION_UP:
				isDrawing = false; 
				invalidate(); 
				Log.d("dd", "msg");	
				break;
		}
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		paint.setColor(Color.RED);
		paint.setStrokeWidth(3);
		if(isDrawing) {
			canvas.drawRect(begin.x, begin.y, end.x, end.y, paint);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
}
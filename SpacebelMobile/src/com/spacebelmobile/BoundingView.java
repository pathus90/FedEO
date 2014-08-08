package com.spacebelmobile;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
class BoundingView extends ImageView implements OnTouchListener 
{ 
	private boolean isDrawing=false;
	Point begin=new Point();
	Point end=new Point();
	GoogleMap map;
	Paint paint=new Paint();
	LatLng latLngE,latLngB;
	OnLatLongBounds latLongBounds;
	public BoundingView(Context context,GoogleMap map )
	{
		super(context);
		this.map=map;
		setWillNotDraw(false);
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
			setDrawing(true); 
			begin.x = (int) event.getX();
			begin.y = (int) event.getY();
			end.x = (int) event.getX();
			end.y = (int) event.getY();
			latLongBounds.setLatLngBegin(map.getProjection().fromScreenLocation(begin));
			invalidate(); 
			break;
		case MotionEvent.ACTION_MOVE:
			setDrawing(true); 
			end.x = (int) event.getX();
			end.y = (int) event.getY();
			latLongBounds.setLatLngEnd(map.getProjection().fromScreenLocation(end));
			invalidate(); 
			break;
		case MotionEvent.ACTION_UP:
			setDrawing(false); 
			invalidate(); 
			break;
		}
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		Paint p = new Paint();
		// smooths
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		p.setStyle(Paint.Style.STROKE); 
		p.setStrokeWidth(4.5f);
		// opacity
		p.setAlpha(0x80); 
		canvas.drawRect(200, 30, 500, 500, p);
	}
	
	public void setOnLatLongBounds(OnLatLongBounds latLongBounds)
	{
		this.latLongBounds=latLongBounds;
	}
    public boolean isDrawing() {
		return isDrawing;
	}
	public void setDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}
	/*|******************************|
      |********INTERFACE BBOX********|
	  |******************************|*/
	 public interface OnLatLongBounds
	 {
		 void setLatLngBegin(LatLng latLng);
		 void setLatLngEnd(LatLng latLng);
	 }
}
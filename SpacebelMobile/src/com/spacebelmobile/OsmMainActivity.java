package com.spacebelmobile;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import android.app.Activity;
import android.os.Bundle;

public class OsmMainActivity extends Activity {
    /** Called when the activity is first created. */
    private MapController mMapController;
    private MapView mMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osm_main);
        mMapView = (MapView) findViewById(R.id.mapviewos);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setBuiltInZoomControls(true);
        mMapController = mMapView.getController();
        mMapController.setZoom(13);
        GeoPoint gPt = new GeoPoint(51500000, -150000);
        //Centre map near to Hyde Park Corner, London
        mMapController.setCenter(gPt);
    }
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
}   
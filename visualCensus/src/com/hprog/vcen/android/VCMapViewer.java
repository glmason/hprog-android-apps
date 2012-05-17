package com.hprog.vcen.android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.hprog.vcen.android.session.SessionCommon;
import com.hprog.vcen.android.view.CVOverlayBuilder;

public class VCMapViewer extends MapActivity {
    private MapView map=null; 
    private MapController mapController=null; 
   	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
     
		VCController.register(this);
		Log.d(this.getClass().getName(), "Starting VCMapViewer()");
		setMap();	    
	    setMapController();
	    if (VCController.boundaryWaitingFlag) {
	        VCController.drawTractBoundary(SessionCommon.getVaribles()
	        		                       .getCurrentTractBean()
	        		                       .getPnt(), 
	        		                       false);	
	    }
	}
	
	private void setMap() {
		map = (MapView)findViewById(R.id.map2);
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);
		
	}
	
	private void setMapController() {
		mapController = map.getController();
		mapController.setZoom(16);			
	}
	
	void clearOverlays() {
		List overlays = map.getOverlays();
		overlays.clear();	
	}
	
	public void drawTractOverlay(boolean addOverlay) {
		if (!addOverlay) {
			clearOverlays();
		}
		
		CVOverlayBuilder ob = new CVOverlayBuilder(getVertices());
		map.getOverlays().add(ob);
	}
	
	public void setCenter(GeoPoint gpt) {
		mapController.setCenter(gpt);
	}
	
	
	private ArrayList<GeoPoint> getVertices() {		
		ArrayList<GeoPoint> v = new ArrayList<GeoPoint>();		
		v = (ArrayList<GeoPoint>) SessionCommon.getVaribles().getCurrentTractBean().
		getBoundryVertices();
		return v;
	}
}

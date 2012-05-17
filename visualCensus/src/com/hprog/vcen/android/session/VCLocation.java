package com.hprog.vcen.android.session;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.hprog.vcen.android.VCController;
import com.hprog.vcen.android.helpers.Coord;

public class VCLocation {
	private LocationManager lm; 
    private LocationListener ll; 
	private Criteria criteria;
	private Location lastLocation;
	
	public VCLocation(LocationManager lm) {
		Log.d(this.getClass().getName(), "Starting VCLocation()");
		this.lm = lm;
//		setCriteria();
//		this.getManager().getProviders(criteria, true);
		this.addListener();
		lm.requestLocationUpdates(
	            LocationManager.GPS_PROVIDER,0,0,ll);
	}
	
	LocationManager getManager(){
		return lm;
	}
	
	void addListener() {	
		ll = new VCLocationListener(); 
	}
	
	private void setCriteria() {
		if (criteria == null) {
			criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setPowerRequirement(Criteria.POWER_HIGH);
		}
	}
		
	public Coord getCurrentLatLng() {
		return new Coord(lastLocation.getLatitude(), lastLocation.getLongitude());
	}
	
	private void setLastLocation(Location loc) {
		if (lastLocation == null) {
			VCController.GPSUpdatingLocation(true);
		}
		if (loc == null) {
			VCController.GPSUpdatingLocation(false);
		}
		lastLocation = loc;
		

	}
	
	private Location getLastLocation() {
		return lastLocation;
	}
		
	class VCLocationListener implements LocationListener { 
        public void onLocationChanged(Location loc) {
        	setLastLocation(loc);
        	//Log.d(this.getClass().getName(), "Lat: "+loc.getLatitude()+" long: "+loc.getLongitude());
        } 

        public void onProviderDisabled(String provider) {
        	setLastLocation(null);
        } 

        public void onProviderEnabled(String provider) { 
        	//VCController.GPSUpdatingLocation(true);
         } 

        public void onStatusChanged(String provider, int status, Bundle extras) { 
         }
   } 
}

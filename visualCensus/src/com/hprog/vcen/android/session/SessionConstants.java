package com.hprog.vcen.android.session;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

public class SessionConstants {
	static SessionConstants sv;
	static VCLocation slm;
	static Criteria criteria;
	static Activity mainActivity;

	private SessionConstants() {
	}
	
	public static SessionConstants getSessionConstants() {
		if (sv == null) {
			sv = new SessionConstants();
		}		
		return sv;
	}

	public void setMainActivity(Activity act) {
		mainActivity = act;
	}
	
	static public VCLocation getVCLocation() {
		getSessionLocMgr();
		return slm;

	}

	static public LocationManager getSessionLocMgr() {
		if (slm == null) {
			slm = new VCLocation((LocationManager) mainActivity
					.getSystemService(Context.LOCATION_SERVICE));
		}
		return slm.getManager();
	}
}

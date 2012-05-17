package com.hprog.vcen.android.session;

import android.app.Activity;

import com.hprog.vcen.android.vcml.VCTractBean;

public class SessionCommon {
	static SessionCommon sv;
	private VCTractBean currentTractLocation;
	static private VCLocation location;
	
	private SessionCommon() {
	}
	
	private static void init() {
		if (sv == null) {
			sv = new SessionCommon();
		}
	}
	
	static public SessionCommon getVaribles() {
		init();
		return sv;
	}
				
	public void setCurrentTractBean(VCTractBean tb) {
		currentTractLocation = tb;
	}
	
	public VCTractBean getCurrentTractBean() {
		return currentTractLocation;
	}
  
}


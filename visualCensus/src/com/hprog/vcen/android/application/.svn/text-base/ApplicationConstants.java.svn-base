package com.hprog.vcen.android.application;

/* Application and developer constants will go here and not be user definable" 
 * 
 * User definable properties and preferences will go in Session constants, or variables
 */


public class ApplicationConstants {
	static final String THE_URL = "http://mizarllc.com/vcenti/VCServlet";
	//static final String THE_URL = "http://192.168.1.102/vcenti/VCServlet";
	static private ApplicationConstants app;
	static String vcUrlString; 
	
	private ApplicationConstants() {
		vcUrlString = "http://mizarllc.com/vcenti/VCServlet";
	}
	
	static public String getURLString() {
		if (app == null) {
			app = new ApplicationConstants();
		} 
		return vcUrlString;
	}
}

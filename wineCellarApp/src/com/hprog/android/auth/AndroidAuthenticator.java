package com.hprog.android.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.pras.auth.Authenticator;

public class AndroidAuthenticator implements Authenticator {
	private final String TAG = "HPROG";
	Activity activity;
	AccountManager manager;
	private String mService = "wise";
	private String auth_token = "";
	
	public AndroidAuthenticator(Context context){
		//this.activity = activity;
		manager = AccountManager.get(context);
	}
	
	public String getAuthToken(String service) 
	{
		if(service == null){
			throw new IllegalAccessError("No Service name defined, Can't create Auth Token...");
		}
		
		if(mService != null && !mService.equals(service)){
			// Reset previous Token
			manager.invalidateAuthToken("com.google", null);
		}
			
		Account[] acs = manager.getAccountsByType("com.google");
		Log.i(TAG, "Num of Matching account: "+ acs.length);
		
		for(int i=0; i<acs.length; i++){
			
			if(acs[i].type.equals("com.google") && (acs[i].name.startsWith("glm")||acs[i].name.startsWith("lei"))) 
			{
				// The first Gmail Account will be selected
				Log.i(TAG, "Selected Google Account "+ acs[i].name);
				AccountManagerFuture result = (AccountManagerFuture)(manager.getAuthToken(acs[i], service, null, activity, null, null));
				
				try{
					Bundle b = (Bundle)result.getResult();
					auth_token = b.getString(AccountManager.KEY_AUTHTOKEN);
					Log.i(TAG, "Auth_Token: "+ auth_token);
					//return auth_token;
					
				}catch(Exception ex){
					Log.i(TAG, "Error: "+ ex.toString());
				}
			}
		}
		if (auth_token == null || auth_token.isEmpty()) {
//		if (auth_token == null || auth_token.equalsIgnoreCase("")) {
		  Log.i(TAG, "Problem in getting Auth Token...");
		}
		return auth_token;
	}
	
	public void resetAuthToken(String at) {
		manager.invalidateAuthToken("com.google", at);	
	}
}

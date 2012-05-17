package com.hprog.android.wine;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.hprog.android.auth.AndroidAuthenticator;
import com.pras.SpreadSheetFactory;

public class WineCellarTabLayout extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.main);
                
        TabHost tabHost = getTabHost();
        Resources res = getResources();

        tabHost.addTab(tabHost.newTabSpec("cellar")
        		.setIndicator("The Cellar",res.getDrawable(R.drawable.tabwinelisticon))
        		.setContent(new Intent(this, WineListActivity.class)));
		        //.setContent(R.id.lineItem));       
        tabHost.addTab(tabHost.newTabSpec("note")
        		.setIndicator("Of Note",res.getDrawable(R.drawable.tabnotesicon))
        		.setContent(new Intent(this, NotesActivity.class)));
                //.setContent(R.id.noteView));
        tabHost.addTab(tabHost.newTabSpec("save")
        		.setIndicator("To Try",res.getDrawable(R.drawable.tablookoutlisticon))
        		.setContent(new Intent(this, WineOfInterestActivity.class)));
        
        setDefaultTab(0);            
    	}
}

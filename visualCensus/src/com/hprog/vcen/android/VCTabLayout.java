package com.hprog.vcen.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class VCTabLayout extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        VCController.setController();
        VCController.register(this);

        setDefaultTab(0);
        
//        this.getTabWidget().setMinimumHeight(60);
//        BitmapDrawable tabInd = (BitmapDrawable)getResources().getDrawable(R.drawable.tab_indicator);
//        this.getTabWidget().setDrawable(tabInd);
        
        TabHost tabHost = getTabHost();
        Resources res = getResources();
        //BitmapDrawable tabInd = (BitmapDrawable)getResources().getDrawable(R.drawable.tab_indicator);
        tabHost.addTab(tabHost.newTabSpec("tab1")
        		.setIndicator("Home",res.getDrawable(R.drawable.hometabicon))
        		.setContent(new Intent(this, LocateCurrentTract.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
        		.setIndicator("Tabular",res.getDrawable(R.drawable.tabtabicon))
        		.setContent(new Intent(this, DrawableStats.class)));       		
        tabHost.addTab(tabHost.newTabSpec("tab3")
        		.setIndicator("Visual",res.getDrawable(R.drawable.charttabicon))
        		.setContent(new Intent(this, DrawableStats.class)));       		
        tabHost.addTab(tabHost.newTabSpec("tab4")
        		.setIndicator("Map",res.getDrawable(R.drawable.maptabicon))
        		/*.setContent(new Intent(this, VCMapViewer.class)));*/ 
        		.setContent(new Intent(this, VCMapViewer.class))); 
        
//        this.startActivity(new Intent(this, CVMapViewer.class));
    }
}
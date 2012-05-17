package com.hprog.android.wine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WineOfInterestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the tab where wines of interest can be kept until found in a store");
        setContentView(textview);
        }
}

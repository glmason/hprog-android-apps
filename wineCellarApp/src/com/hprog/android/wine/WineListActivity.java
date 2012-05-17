package com.hprog.android.wine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hpog.android.utils.SortWineUtils;
import com.hpog.android.utils.SortWineUtils.RC;
import com.pras.table.Record;

public class WineListActivity extends ListActivity {
	    private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    private EfficientAdapter adap;
	    private static int[] dataIndx;
	    private static String[] brandString;
	    private static String[] grapeString;	       		    
	    private static Integer[] bottleCount;           
	    private static ArrayList<Record> data;
	    private static List<String> brands;
	    private static List<String> grapes;
	    
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.i("HPROG", "+) Started app " );
	        //TextView tv = new TextView(this.getApplicationContext());
	        ListView lv = new ListView(this.getApplicationContext());
			lv.setTextFilterEnabled(true);			
			new MyTask().execute(null);
	    }
	    
	    public boolean onContextItemSelected(MenuItem item) {
	            Log.i("HPROG", "a) selected item..." + item.toString() + 
	            	  " : " + brandString[adap.getCurrentPosition()]);
	            
	            int pos = WCController.setCurrentRecordByBrandName(WineListActivity.this, brandString[adap.getCurrentPosition()]);
	            Record rec = WCController.getCurrentRecord();
	            Log.i("HPROG", "b) selected item..." + item.toString() + 
		            	  " : " + rec.getData().toString() + " at row " + pos);
	            
	            switch (item.getItemId()) {
		            case R.id.make_note:
		            	WCController.setCurrentRecord(rec);
		            	TabHost tabHost =  (TabHost) getParent().findViewById(android.R.id.tabhost);	                
		                tabHost.setCurrentTab(1);
		                break;
		            case R.id.add_additional:  
		                rec.addData(getResources().getString(R.string.col_DateAcquired), df.format(new Date()));
		                rec.addData(getResources().getString(R.string.col_DateConsumed), "");
		                rec.addData(getResources().getString(R.string.col_Notes), "");
		                WCController.addRow(rec.getData());
		                refreshData(null);
		                adap.notifyDataSetChanged();
		            	break;
		            case R.id.remove_consumed:		            	
		                rec.addData(getResources().getString(R.string.col_DateConsumed), df.format(new Date()));
		                WCController.updateRow(pos, rec.getData());
		                refreshData(null);
		                adap.notifyDataSetChanged();
		            	break;
		            default:
		            	break;
		            }
	            return true;
	    }
	    
	    
	    	    
		protected class MyTask extends AsyncTask {
			ProgressDialog dialog;
			String message;
			private Runnable changeMessage = new Runnable() {
			    @Override
			    public void run() {
			        dialog.setMessage(message);
			    }
			};
			
			protected void updateMessage(String mess) {
				message = mess;
				runOnUiThread(changeMessage);    	
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();				
				dialog = ProgressDialog.show(WineListActivity.this, "", 
                        "Featching wine list from your Google account...", true);
			}
			
			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				if(dialog.isShowing())
					dialog.cancel();								
					adap = new EfficientAdapter(WineListActivity.this);
					
				    setListAdapter(adap);				    
			}

			@Override
			protected Object doInBackground(Object... params) {
	    	    refreshData(this);
				return null; 
			}
		}
				
		private void refreshData(MyTask task) {
            data = WCController.getGSSData(this, task, getResources().getString(R.string.wks_Wines));
            brands = new ArrayList<String>();
            grapes = new ArrayList<String>();

            ArrayList<Record> dataSorted = (ArrayList<Record>) data.clone();
            Collections.sort(dataSorted, SortWineUtils.accending(SortWineUtils.getComparator(RC.CATEGORY_SORT, RC.GRAPE_SORT, RC.NAME_SORT )));
			for(Record r : dataSorted){			
				if (r.getData().get(getResources().getString(R.string.col_DateConsumed)) == null ||
					r.getData().get(getResources().getString(R.string.col_DateConsumed)) == ""	) {
				  brands.add(r.getData().get(getResources().getString(R.string.col_Name)));
				  grapes.add(r.getData().get(getResources().getString(R.string.col_Grape)));
				}
			}			
			summarize(brands, grapes);
		}
		
		private static void summarize(List<String> brands, List<String> grapes) {	    
    	    brandString = new String[0];
    	    grapeString = new String[0];

			List<String> tmpB = new ArrayList<String>();	
			List<String> tmpG = new ArrayList<String>();
			List<Integer> count = new ArrayList<Integer>();
		    int j = 0;
		    int bottles = brands.size();		    
		    for (int i = 0; i < bottles ; i++) {
		      if (!tmpB.contains(brands.get(i))) {		    	 
		        tmpB.add(brands.get(i));
		        tmpG.add(grapes.get(i));
		        count.add(new Integer(1));
		        j++;
		      } else {
		    	int tmp = tmpB.indexOf(brands.get(i));
		    	count.set(tmp, count.get(tmp)+1);		    
		      }
		    }
		    dataIndx = new int[j];
		    for (int i = 0; i < j; i++) {
		      dataIndx[i] = i;	
		    }
		    brandString = tmpB.toArray(new String[j]);
		    grapeString = tmpG.toArray(new String[j]);	
            bottleCount = count.toArray(new Integer[j]);			
		}
		
	    public static class EfficientAdapter extends BaseAdapter  {
	        private LayoutInflater mInflater;
	        private Context context;
	        private int currentPos;

	        public EfficientAdapter(Context context) {
	          mInflater = LayoutInflater.from(context);
	          this.context = context;
	        }

	        /**
	         * Make a view to hold each row.
	         * 
	         * @see android.widget.ListAdapter#getView(int, android.view.View,
	         *      android.view.ViewGroup)
	         */
	        public View getView(final int position, View convertView, ViewGroup parent) {
	          ViewHolder holder;
	          mInflater = LayoutInflater.from(context);
	          convertView = mInflater.inflate(R.layout.list_item, null);
	            
	            // Creates a ViewHolder and store references to the children
	            // views we want to bind data to.
	            holder = new ViewHolder();
	            holder.textLine = (TextView) convertView.findViewById(R.id.numBot);
	            holder.textLine3 = (TextView) convertView.findViewById(R.id.stock);
	            holder.textLine1 = (TextView) convertView.findViewById(R.id.topLine);
	            holder.textLine2 = (TextView) convertView.findViewById(R.id.secondLine);
	            
	            convertView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	            	private int pos = position;
					@Override
				    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				    	if (v.getId() == R.id.lineItem) {	    		
				    		((Activity) context).getMenuInflater().inflate(R.menu.wine_context, menu);
				    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
				    		menu.setHeaderIcon(R.drawable.tabnotesicon).setHeaderTitle(brandString[pos]);
				    	}
					}		            
	            });
	            	            	            
	            convertView.setOnClickListener(new OnClickListener() {
	              private int pos = position;

	              @Override
	              public void onClick(View v) {
	                Toast.makeText(context, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();	                
	              }
	            });
	            
	          convertView.setTag(holder);

	          holder.textLine.setText(bottleCount[position].toString());
	          holder.textLine1.setText(brandString[position]);
	          holder.textLine2.setText(grapeString[position]);
	          holder.textLine3.setText("In Stock");
	          
	          return convertView;
	        }
	        
	        public int getCurrentPosition() {
	        	return currentPos;
	        }

	        @Override
	        public long getItemId(int position) {
	          currentPos = position;
	          return position;
	        }

	        @Override
	        public int getCount() {
	          return dataIndx.length;
	        }

	        @Override
	        public Object getItem(int position) {
	          return dataIndx[position];
	        }
	        
	        static class ViewHolder {
		          TextView textLine;
		          TextView textLine1;
		          TextView textLine2;
		          TextView textLine3;
		     }
	    }
}

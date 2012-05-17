package com.hprog.android.wine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.hprog.android.wine.WineListActivity.EfficientAdapter;
import com.hprog.android.wine.WineListActivity.EfficientAdapter.ViewHolder;
import com.pras.table.Record;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotesActivity  extends Activity {
    private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static String[] noteDates;
    private static String[] noteNotes;
    private static int[] dataIndx;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        ListView lv = (ListView)findViewById(R.id.noteList);
		NoteListAdapter nlad = new NoteListAdapter(this);
		setData(); 
		lv.setAdapter(nlad);		
        }
    
    public void onResume() {
    	super.onResume();
        setData(); 	
    }
    
    private void setData() {
    	ArrayList<String> dates = new ArrayList<String>();
    	ArrayList<String> notes = new ArrayList<String>(); 
    	
    	EditText noteEdTv = (EditText)findViewById(R.id.note_editText);
    	noteEdTv.clearComposingText();
    	    	   	
    	TextView nameTV = (TextView)findViewById(R.id.note_name);
    	TextView grapeTV = (TextView)findViewById(R.id.note_grape);
    	TextView notesTV = (TextView)findViewById(R.id.note_notes);
    	if  (WCController.getCurrentRecord() != null) {
	        Record record = WCController.getCurrentRecord();
	        nameTV.setText(record.getData().get(getResources().getString(R.string.col_Name)));
			grapeTV.setText(record.getData().get(getResources().getString(R.string.col_Grape)));			
			//TODO get note from records
			ArrayList<Record> data = WCController.getGSSData(this, null, getResources().getString(R.string.wks_Notes));
			int j = 0;
			for (Record r : data) {
				if (r.getData().get(getResources().getString(R.string.col_Name)).equalsIgnoreCase(record.getData().get(getResources().getString(R.string.col_Name)))) {
				  dates.add(r.getData().get(getResources().getString(R.string.col_DateOfNote)));
				  notes.add(r.getData().get(getResources().getString(R.string.col_Notes)));
				  j++;
				}
			}
		    dataIndx = new int[j];
		    for (int i = 0; i < j; i++) {
		      dataIndx[i] = i;	
		    }
			if (dataIndx.length > 0) {
				notesTV.setText("Notes:");
				Log.i("HPROG", "+) got notes..." );
			} else {
				notesTV.setText("");
	        	noteEdTv.setText("Add note here...");
			}
			noteDates = dates.toArray(new String[j]);
			noteNotes = notes.toArray(new String[j]);
    	}   	
    }
    
    public static class NoteListAdapter extends BaseAdapter  {
        private LayoutInflater mInflater;
        private Context context;
        private int currentPos;
        

        public NoteListAdapter(Context context) {
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
          convertView = mInflater.inflate(R.layout.note_list_item, null);
            
            // Creates a ViewHolder and store references to the children
            // views we want to bind data to.
            holder = new ViewHolder();
            holder.textItem1 = (TextView) convertView.findViewById(R.id.dateTI);
            holder.textItem2 = (TextView) convertView.findViewById(R.id.noteTI);
            
//            convertView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
//            	private int pos = position;
//				@Override
//			    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//			    	if (v.getId() == R.id.lineItem) {	    		
//			    		((Activity) context).getMenuInflater().inflate(R.menu.wine_context, menu);
//			    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//			    		//menu.setHeaderIcon(R.drawable.tabnotesicon).setHeaderTitle(brandString[pos]);
//			    	}
//				}		            
//            });
            	            	            
            convertView.setOnClickListener(new OnClickListener() {
              private int pos = position;

              @Override
              public void onClick(View v) {
                Toast.makeText(context, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();	                
              }
            });
            
          convertView.setTag(holder);

          holder.textItem1.setText(noteDates[position]);
          holder.textItem2.setText(noteNotes[position]);
          
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
	          TextView textItem1;
	          TextView textItem2;
	     }
    }
    

}

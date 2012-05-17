package com.hpog.android.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hprog.android.wine.R;
import com.hprog.gss.WorkSheetCell;
import com.hprog.gss.WorkSheetRow;
import com.pras.table.Record;

public class RecordUtils {
	public static ArrayList<Record> currentWineRecordSet;
	public static ArrayList<Record> currentNoteRecordSet;
	//public static ArrayList<Record> currentRecordSet;
	
	public enum ListType {
	    WINES, NOTES 
	}
	
	public static ArrayList<Record> makeRecordSet(List<WorkSheetRow> list, ListType lt) {
		ArrayList<Record> records = new ArrayList<Record>();
	    HashMap<String, String> map;
	    
	    for (WorkSheetRow r : list) {
	      ArrayList<WorkSheetCell> cells = r.getCells();
	      map = new HashMap<String, String>();
	      for (WorkSheetCell c : cells) {
	        map.put(c.getName(), c.getValue());
	      }
	      Record newRecord = new Record();
	      newRecord.setData(map);	
	      records.add(newRecord);
	    }
	    
        switch (lt) {
        case WINES:
        	setCurrentWineRecordSet(records);
        	break;
        case NOTES:
        	setCurrentNoteRecordSet(records);
        	break;
         default:
        	 records = null;
        }
		return records;
	}
	
	public static void setCurrentWineRecordSet(ArrayList<Record> recordSet) {
	   currentWineRecordSet = recordSet;	
	}
	
	public static ArrayList<Record> getCurrentWineRecordSet() {
		   return currentWineRecordSet;	
		}
	
	public static void setCurrentNoteRecordSet(ArrayList<Record> recordSet) {
		   currentNoteRecordSet = recordSet;	
		}
		
	public static ArrayList<Record> getCurrentNoteRecordSet() {
		   return currentNoteRecordSet;	
		}
	
}

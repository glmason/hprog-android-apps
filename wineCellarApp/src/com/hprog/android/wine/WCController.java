package com.hprog.android.wine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.util.Log;

import com.hpog.android.utils.RecordUtils;
import com.hpog.android.utils.RecordUtils.ListType;
import com.hprog.android.auth.AndroidAuthenticator;
import com.hprog.android.wine.WineListActivity.MyTask;
import com.hprog.gss.SpreadSheet;
import com.hprog.gss.SpreadSheetFactory;
import com.hprog.gss.WorkSheet;
import com.hprog.gss.WorkSheetRow;
import com.pras.table.Record;

/**
 * @author glm
 *
 */
public class WCController {
	static WCController wcc = new WCController();
	private static AndroidAuthenticator authenticator;
	static Record curRecord = null;
	static String key;
	static WorkSheet ws;
	static ArrayList<WorkSheetRow> wsr;	

	private WCController() {		
	}
	
	private static AndroidAuthenticator getCred(Context context) {
		if (authenticator == null) {
          authenticator = new AndroidAuthenticator(context);
		}
		return authenticator;
	}

	/**
	 * @param Name
	 */
	static public int setCurrentRecordByBrandName(Context context, String Name) {
		int pos = 0;
		Record rec = null;
		List<Record> rSet = RecordUtils.getCurrentWineRecordSet();
		boolean found = false;
		int i = 0;
		for (Record r : rSet){
		  if (!found && r.getData().get(context.getResources().getString(R.string.col_Name)).equalsIgnoreCase(Name) ) {
			if (wineInStock(context, r)) {
		    	rec = r;
		    	pos = i;
		    	found = true;
		    }
		  }
		  i++;
		}
		setCurrentRecord(rec);
		return pos;
	}
	
	static public boolean wineInStock(Context context, Record r) {
		return (r.getData().get(context.getResources().getString(R.string.col_DateAcquired)) != null &&
		        (r.getData().get(context.getResources().getString(R.string.col_DateConsumed)) == null ||
		         r.getData().get(context.getResources().getString(R.string.col_DateConsumed)).length() < 8));
	}

	static public void setCurrentRecord(Record rec) {
	    curRecord = rec;
	}
		
	static public Record getCurrentRecord() {
	    return curRecord;
	}
	
	static public ArrayList<Record> getGSSData(Context context, MyTask task, String sheetName) {
	    ArrayList<SpreadSheet> sheets;
	    String message;
	    SpreadSheetFactory factory = SpreadSheetFactory.getInstance(getCred(context));
	    //sheets = factory.getAllSpreadSheets();
	    sheets = factory.getSpreadSheet(context.getResources().getString(R.string.gss_FileName), true);
	    Log.i("HPROG", "+) sheetName " +  sheets.get(0).getTitle());
	    key = sheets.get(0).getKey();
	    if (task != null) {
	      message = "Obtained " + sheets.get(0).getTitle() + "...";
	      task.updateMessage(message);
	    }
	    
	    ArrayList<WorkSheet> workSheets = sheets.get(0).getWorkSheet(sheetName, true);	    
	    ws = workSheets.get(0);
	    Log.i("HPROG", "+) workSheet " +  ws.getTitle());
	    if (task != null) {
	      message = "Checking " + ws.getTitle() + "...";
	      task.updateMessage(message);
	    }
	    
	    wsr = ws.getData(false);	    
	    Log.v("HPROG", "+) rows " +  wsr.toString());
	    int i = 0;
	    
	    ListType listType = sheetName.equalsIgnoreCase(context.getResources().getString(R.string.wks_Wines)) ? RecordUtils.ListType.WINES : RecordUtils.ListType.NOTES;
	    return RecordUtils.makeRecordSet(wsr, listType);
	}
	
	static public boolean updateRow(int rowId, HashMap<String, String> update) {
		Log.i("HPROG", "+) rowId..." + rowId); 
		WorkSheetRow row = (WorkSheetRow)wsr.get(rowId);
		WorkSheetRow rowUpdate = ws.updateListRow(key, row, update);
		return rowUpdate != null;
	}
	
	static public boolean addRow(HashMap<String, String> newRow) { 
		WorkSheetRow rowAdded = ws.addListRow(newRow);
		return rowAdded != null;
	}
}

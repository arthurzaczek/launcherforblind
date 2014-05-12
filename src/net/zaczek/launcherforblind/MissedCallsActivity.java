package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractCursorActivity;
import net.zaczek.launcherforblind.listentries.ListEntry;
import net.zaczek.launcherforblind.listentries.MissedCallListEntry;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

public class MissedCallsActivity extends AbstractCursorActivity {
	private static final String TAG = "launcherforblind";

	private static final String[] PROJECTION = new String[] {
		CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE };
	private static final String SELECTION =  CallLog.Calls.TYPE + " = " + CallLog.Calls.MISSED_TYPE;

	private boolean confirmed = false;
	private TextView txtMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.missedcalls);

		txtMain = (TextView) findViewById(R.id.txtMain);
	}

	@Override
	protected Cursor getCursor() {
		Cursor c = managedQuery(CallLog.Calls.CONTENT_URI, PROJECTION, SELECTION, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		return c;
	}

	@Override
	protected ListEntry getListEntry(Cursor c) {
		long time = c.getLong(2);
		return new MissedCallListEntry(c.getString(0), c.getString(1), 
				DateUtils.formatDateTime(this, time, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME));
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);
		confirmed = false;		
	}

	@Override
	protected void onExecute() {
		super.onExecute();
		final MissedCallListEntry current = (MissedCallListEntry)getCurrentListEntry();
		if(current == null) return;
		
		if (!confirmed) {
			Helper.confirmDial(this, current.getLabel());
			confirmed = true;
		} else {
			// actually dial
			Helper.dial(this, current.getNumber());			
		}
	}
}

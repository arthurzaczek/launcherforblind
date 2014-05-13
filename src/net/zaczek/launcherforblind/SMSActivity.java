package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractCursorActivity;
import net.zaczek.launcherforblind.listentries.ListEntry;
import net.zaczek.launcherforblind.listentries.SMSListEntry;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

public class SMSActivity extends AbstractCursorActivity {
	private static final String TAG = "launcherforblind";

	private static final String[] PROJECTION = new String[] { "address",
			"date", "body" };

	private TextView txtMain;
	private boolean confirmed = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.sms);

		txtMain = (TextView) findViewById(R.id.txtMain);
	}

	@Override
	protected Cursor getCursor() {
		Cursor c = managedQuery(Uri.parse("content://sms/inbox"), PROJECTION,
				null, null, "date DESC");
		return c;
	}

	@Override
	protected ListEntry getListEntry(Cursor c) {
		long date = c.getLong(1);
		String strDate = DateUtils.formatDateTime(this, date,
				DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
		return new SMSListEntry(c.getString(0), strDate, c.getString(2));
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);
		confirmed = false;
	}

	@Override
	protected void onExecute() {
		super.onExecute();
		final SMSListEntry current = (SMSListEntry) getCurrentListEntry();
		if (current == null)
			return;

		if (!confirmed) {
			Helper.confirmDial(this, current.getName());
			confirmed = true;
		} else {
			// actually dial
			Helper.dial(this, current.getNumber());
		}
	}
}

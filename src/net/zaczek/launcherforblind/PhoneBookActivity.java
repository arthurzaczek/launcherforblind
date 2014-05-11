package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractCursorActivity;
import net.zaczek.launcherforblind.listentries.ContactListEntry;
import net.zaczek.launcherforblind.listentries.ListEntry;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.widget.TextView;

public class PhoneBookActivity extends AbstractCursorActivity {
	private static final String TAG = "launcherforblind";

	private static final String[] PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.RAW_CONTACT_ID, Phone.NUMBER, Phone.TYPE };

	private boolean confirmed = false;
	private TextView txtMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.phonebook);

		txtMain = (TextView) findViewById(R.id.txtMain);
	}

	@Override
	protected Cursor getCursor() {
		Cursor c = managedQuery(Phone.CONTENT_URI, PROJECTION, null, null,
				Phone.DISPLAY_NAME + " ASC");
		return c;
	}

	@Override
	protected ListEntry getListEntry(Cursor c) {
		int phoneType = Integer.parseInt(c.getString(3));
		String type = "";
		if (phoneType == Phone.TYPE_HOME) {
			type = getString(R.string.home);
		} else if (phoneType == Phone.TYPE_MOBILE) {
			type = getString(R.string.cell);
		} else if (phoneType == Phone.TYPE_WORK) {
			type = getString(R.string.work);
		} else {
			type = getString(R.string.other);
		}

		return new ContactListEntry(c.getString(0), c.getInt(1), type, c.getString(2));
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);
		confirmed = false;		
	}

	@Override
	protected void onExecute() {
		super.onExecute();
		final ContactListEntry current = (ContactListEntry)getCurrentListEntry();
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

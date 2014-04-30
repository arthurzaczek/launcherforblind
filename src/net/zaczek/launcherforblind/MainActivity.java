package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractArrayActivity;
import net.zaczek.launcherforblind.listentries.ListEntry;
import net.zaczek.launcherforblind.listentries.NavigatorListEntry;
import net.zaczek.launcherforblind.listentries.StaticListEntry;
import net.zaczek.launcherforblind.listentries.TimeListEntry;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AbstractArrayActivity {
	private static final String TAG = "launcherforblind";
	
	private TextView txtMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);
		
		txtMain = (TextView)findViewById(R.id.txtMain);
	}

	@Override
	protected ListEntry[] getList() {
		return new ListEntry[] { new StaticListEntry("Start Schirm"),
				new NavigatorListEntry(getString(R.string.phonebook), this, PhoneBookActivity.class),
				new NavigatorListEntry(getString(R.string.dialer), this, DialerActivity.class),
				//new NavigatorListEntry(getString(R.string.missedcalls), this, null),
				//new NavigatorListEntry(getString(R.string.sms), this, null),
				new TimeListEntry(getString(R.string.currenttime)),
				};
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);		
	}
}

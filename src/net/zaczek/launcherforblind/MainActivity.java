package net.zaczek.launcherforblind;

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
				new NavigatorListEntry("Telefonbuch", null),
				new NavigatorListEntry("Verpasste Anrufe", null),
				new NavigatorListEntry("SMS", null),
				new TimeListEntry("Uhrzeit"),
				};
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);		
	}
}

package net.zaczek.launcherforblind;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AbstractListActivity {
	private static final String TAG = "launcherforblind";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);
	}
}

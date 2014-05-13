package net.zaczek.launcherforblind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zaczek.launcherforblind.activitysupport.AbstractArrayActivity;
import net.zaczek.launcherforblind.listentries.AppListEntry;
import net.zaczek.launcherforblind.listentries.ListEntry;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class AppsActivity extends AbstractArrayActivity {
	private static final String TAG = "launcherforblind";

	private TextView txtMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.apps);

		txtMain = (TextView) findViewById(R.id.txtMain);
	}

	@Override
	protected ListEntry[] getList() {
		Intent homeIntent = new Intent(Intent.ACTION_MAIN, null);
		homeIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		PackageManager pm = this.getPackageManager();
		List<AppListEntry> result = new ArrayList<AppListEntry>();

		for (ResolveInfo info : pm.queryIntentActivities(homeIntent, 0)) {
			final ActivityInfo appInfo = info.activityInfo;
			if (!appInfo.packageName.equals("net.zaczek.launcherforblind")) {
				String lb = info.loadLabel(pm).toString();
				if (TextUtils.isEmpty(lb)) {
					lb = info.activityInfo.name.toString();
				}
				result.add(new AppListEntry(lb, info));
			}
		}

		Collections.sort(result);
		
		return result.toArray(new ListEntry[0]);
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);
	}
}

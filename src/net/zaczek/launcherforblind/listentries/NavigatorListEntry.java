package net.zaczek.launcherforblind.listentries;

import net.zaczek.launcherforblind.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NavigatorListEntry extends AbstractListEntry {
	private static final String TAG = "launcherforblind";

	@SuppressWarnings("rawtypes")
	private Class mActivityClass;

	@SuppressWarnings("rawtypes")
	public NavigatorListEntry(String label, Class activityClass) {
		super(label);

		mActivityClass = activityClass;
	}

	@Override
	public void onSelected() {
		if (mActivityClass != null) {
			final Context ctx = MyApplication.getAppContext();
			Log.i(TAG, "Starting activity " + mActivityClass);
			Intent i = new Intent(ctx, mActivityClass);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(i);
		} else {
			Log.w(TAG, "Unable to navigate - no class provided");
		}
	}
}

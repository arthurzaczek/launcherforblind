package net.zaczek.launcherforblind.listentries;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NavigatorListEntry extends AbstractListEntry {
	private static final String TAG = "launcherforblind";

	@SuppressWarnings("rawtypes")
	private Class mActivityClass;
	private Context mCtx;

	@SuppressWarnings("rawtypes")
	public NavigatorListEntry(String label, Context ctx, Class activityClass) {
		super(label);

		mCtx = ctx;
		mActivityClass = activityClass;
	}

	@Override
	public void onSelected() {
		if (mActivityClass != null) {
			Log.i(TAG, "Starting activity " + mActivityClass);
			mCtx.startActivity(new Intent(mCtx, mActivityClass));
		} else {
			Log.w(TAG, "Unable to navigate - no class provided");
		}
	}

	@Override
	public String getTextToSay() {
		return null;
	}
}

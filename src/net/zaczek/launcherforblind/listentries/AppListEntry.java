package net.zaczek.launcherforblind.listentries;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class AppListEntry extends AbstractListEntry implements Comparable<AppListEntry> {
	private ResolveInfo mInfo;
	private Context mCtx;

	public AppListEntry(String label, Context ctx, ResolveInfo info) {
		super(label);
		mCtx = ctx;
		mInfo = info;
	}

	@Override
	public String getTextToSay() {
		return null;
	}

	@Override
	public void onSelected() {
		final Intent i = mCtx.getPackageManager().getLaunchIntentForPackage(
				mInfo.activityInfo.packageName);
		mCtx.startActivity(i);
	}

	@Override
	public int compareTo(AppListEntry another) {		
		return getLabel().compareTo(another.getLabel());
	}
}

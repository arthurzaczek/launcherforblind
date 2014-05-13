package net.zaczek.launcherforblind.listentries;

import net.zaczek.launcherforblind.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class AppListEntry extends AbstractListEntry implements
		Comparable<AppListEntry> {
	private ResolveInfo mInfo;

	public AppListEntry(String label, ResolveInfo info) {
		super(label);
		mInfo = info;
	}

	@Override
	public void onSelected() {
		final Context ctx = MyApplication.getAppContext();
		final Intent i = ctx.getPackageManager().getLaunchIntentForPackage(
				mInfo.activityInfo.packageName);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(i);
	}

	@Override
	public int compareTo(AppListEntry another) {
		return getLabel().compareTo(another.getLabel());
	}
}

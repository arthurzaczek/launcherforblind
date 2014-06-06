package net.zaczek.launcherforblind.listentries;

import net.zaczek.launcherforblind.MyApplication;
import net.zaczek.launcherforblind.R;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryListEntry extends AbstractListEntry {

	public BatteryListEntry(String label) {
		super(label);
	}
	
	@Override
	public String getLabelToSay() {
		return super.getLabel() + ". " + getTextToSay();
	}

	@Override
	public String getTextToSay() {
		final IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		final Intent batteryStatus = MyApplication.getAppContext().registerReceiver(null, ifilter);
		
		final int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		final boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
		
		final int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		final int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		final int batteryPct = (100 * level) / scale;
		
		return Integer.toString(batteryPct) + "%. " + (isCharging ? MyApplication.getAppContext().getString(R.string.charging) :"");
	}
}

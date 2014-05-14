package net.zaczek.launcherforblind;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	private static SharedPreferences sharedPref;

	public synchronized static void load() {
		if (sharedPref == null) {
			sharedPref = PreferenceManager
					.getDefaultSharedPreferences(MyApplication.getAppContext());
		}
	}

	public static boolean announceMainHelp() {
		load();
		return sharedPref.getBoolean("announceMainHelp", true);
	}

	public synchronized static void updateAnnounceMainHelp() {
		load();
		sharedPref.edit().putBoolean("announceMainHelp", false).commit();
		sharedPref = null;
	}
	
	public static boolean announceDialerHelp() {
		load();
		return sharedPref.getBoolean("announceDialerHelp", true);
	}

	public synchronized static void updateAnnounceDialerHelp() {
		load();
		sharedPref.edit().putBoolean("announceDialerHelp", false).commit();
	}
}

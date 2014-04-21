package net.zaczek.launcherforblind;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeListEntry extends AbstractListEntry {

	final Calendar cal = Calendar.getInstance();
	final SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

	public TimeListEntry(String label) {
		super(label);
	}

	@Override
	public void onSelected() {
	}

	@Override
	public String getTextToSay() {
		final int day = cal.get(Calendar.DAY_OF_MONTH);
		final String monthStr = monthFormat.format(cal.getTime());
		final int hour = cal.get(Calendar.HOUR_OF_DAY);
		final int minutes = cal.get(Calendar.MINUTE);

		return "Es ist " + Integer.toString(hour) + " Uhr "
				+ Integer.toString(minutes) + " am " + Integer.toString(day)
				+ " " + monthStr;
	}
}

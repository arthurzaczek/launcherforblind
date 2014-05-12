package net.zaczek.launcherforblind.listentries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeListEntry extends AbstractListEntry {

	final Calendar cal = Calendar.getInstance();
	final String mFormatStr;

	public TimeListEntry(String label, String format) {
		super(label);
		mFormatStr = format;
	}
	
	@Override
	public String getLabelToSay() {
		return super.getLabel() + ". " + getTextToSay();
	}

	@Override
	public String getTextToSay() {
		final SimpleDateFormat sdf = new SimpleDateFormat(mFormatStr,
				Locale.getDefault());
		return sdf.format(cal.getTime());
	}
}

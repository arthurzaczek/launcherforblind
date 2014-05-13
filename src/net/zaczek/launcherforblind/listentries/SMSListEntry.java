package net.zaczek.launcherforblind.listentries;

import net.zaczek.launcherforblind.Helper;
import net.zaczek.launcherforblind.MyApplication;

public class SMSListEntry extends AbstractListEntry {
	String mNumber;
	String mDate;
	String mMessage;

	public SMSListEntry(String number, String date, String msg) {
		super(date);
		mNumber = number;
		mDate = date;
		mMessage = msg;
	}

	@Override
	public String getLabelToSay() {
		return Helper.getContactName(MyApplication.getAppContext(), mNumber)
				+ ", " + mDate + ".\n" + mMessage;
	}

	public String getNumber() {
		return mNumber;
	}
}

package net.zaczek.launcherforblind.listentries;

import net.zaczek.launcherforblind.Helper;
import android.content.Context;

public class SMSListEntry extends AbstractListEntry {
	String mNumber;
	String mDate;
	String mMessage;
	Context mCtx;
	
	public SMSListEntry(Context ctx, String number, String date, String msg) {
		super(date);
		mCtx = ctx;
		mNumber = number;
		mDate = date;
		mMessage = msg;
	}
	
	@Override
	public String getLabelToSay() {
		return Helper.getContactName(mCtx, mNumber) + ", " + mDate + ".\n" + mMessage;
	}

	public String getNumber() {
		return mNumber;
	}
}

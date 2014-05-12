package net.zaczek.launcherforblind.listentries;

public class MissedCallListEntry extends AbstractListEntry {
	String mNumber;
	String mDate;
		
	public MissedCallListEntry(String label, String number, String date) {
		super(label);
		mNumber = number;
		mDate = date;
	}

	@Override
	public String getLabelToSay() {
		return getLabel() + ", " + mDate;
	}

	public String getNumber() {
		return mNumber;
	}
}

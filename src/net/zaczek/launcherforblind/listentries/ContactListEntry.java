package net.zaczek.launcherforblind.listentries;

public class ContactListEntry extends AbstractListEntry {
	int mContactID;
	String mNumber;
	
	public ContactListEntry(String label, int id, String type, String number) {
		super(label + " " + type);
		mContactID = id;
		mNumber = number;
	}

	@Override
	public String getTextToSay() {
		return null;
	}

	@Override
	public void onSelected() {
	}

	public String getNumber() {
		return mNumber;
	}
}

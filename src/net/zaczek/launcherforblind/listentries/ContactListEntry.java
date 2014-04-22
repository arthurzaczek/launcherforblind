package net.zaczek.launcherforblind.listentries;

public class ContactListEntry extends AbstractListEntry {
	int mContactID;
	public ContactListEntry(String label, int id, String type) {
		super(label + " " + type);
		mContactID = id;
	}

	@Override
	public String getTextToSay() {
		return null;
	}

	@Override
	public void onSelected() {
	}

}

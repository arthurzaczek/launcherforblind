package net.zaczek.launcherforblind.listentries;

public abstract class AbstractListEntry implements ListEntry {
	private final String mLabel;

	public AbstractListEntry(String label) {
		mLabel = label;
	}

	public String getLabel() {
		return mLabel;
	}

	public String getLabelToSay() {
		return mLabel;
	}

	public void onSelected() {

	}

	public String getTextToSay() {
		return null;
	}
}

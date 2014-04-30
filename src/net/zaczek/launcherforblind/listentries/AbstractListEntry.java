package net.zaczek.launcherforblind.listentries;


public abstract class AbstractListEntry implements ListEntry {
	private final String mLabel;

	public AbstractListEntry(String label) {
		mLabel = label;
	}

	@Override
	public String getLabel() {
		return mLabel;
	}
	
	public String getLabelToSay() {
		return mLabel;
	}
	
	@Override
	public abstract void onSelected();
}

package net.zaczek.launcherforblind;

public abstract class AbstractListEntry implements ListEntry {
	private final String mLabel;

	public AbstractListEntry(String label) {
		mLabel = label;
	}

	@Override
	public String getLabel() {
		return mLabel;
	}
	@Override
	public abstract void onSelected();
}

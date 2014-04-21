package net.zaczek.launcherforblind;

public interface ListEntry {
	String getLabel();
	
	void onSelected();
	String getTextToSay();
}

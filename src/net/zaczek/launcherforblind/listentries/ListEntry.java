package net.zaczek.launcherforblind.listentries;

public interface ListEntry {
	String getLabel();
	
	void onSelected();
	String getTextToSay();
}

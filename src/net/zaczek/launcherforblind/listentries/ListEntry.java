package net.zaczek.launcherforblind.listentries;

public interface ListEntry {
	String getLabel();
	String getLabelToSay();
	
	void onSelected();
	String getTextToSay();
}

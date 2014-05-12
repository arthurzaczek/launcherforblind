package net.zaczek.launcherforblind.listentries;

public interface ListEntry {
	/* 
	 * The label to display
	 */
	String getLabel();
	/*
	 * Overrides the label when announcing the label
	 */
	String getLabelToSay();
	
	/*
	 * Called when the item is selected (usually during onDoubleTap) 
	 */
	void onSelected();
	
	/*
	 * Announced during the onSelected event
	 */
	String getTextToSay();
}

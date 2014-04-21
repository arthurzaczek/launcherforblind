package net.zaczek.launcherforblind;

public class TimeListEntry extends AbstractListEntry {

	public TimeListEntry(String label) {
		super(label);
	}

	@Override
	public void onSelected() {
	}

	@Override
	public String getTextToSay() {
		return "Es ist schon spät";
	}
}

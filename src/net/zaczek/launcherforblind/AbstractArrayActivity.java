package net.zaczek.launcherforblind;

import android.util.Log;

/*
 * Abstract list activity for dealing with simple lists
 */
public abstract class AbstractArrayActivity extends AbstractActivity {
	private static final String TAG = "launcherforblind";

	private ListEntry[] mList;
	private int mIndex = -1;

	protected abstract ListEntry[] getList();

	protected abstract void giveFeedback(String label);

	@Override
	protected void onResume() {
		super.onResume();

		mList = getList();
		if (mList != null && mList.length > 0) {
			mIndex = 0;
			select();
		} else {
			mIndex = -1;
		}
	}

	private void select() {
		final ListEntry entry = mList[mIndex];
		final String label = entry.getLabel();
		Log.i(TAG, "Selecting " + label);
		giveFeedback(label);
		say(label);
	}

	private void execute() {
		final ListEntry entry = mList[mIndex];
		say(entry.getTextToSay());
		entry.onSelected();
	}

	@Override
	protected void onScrollDown() {
		super.onScrollDown();

		if (mIndex == -1)
			return;

		if (mIndex < mList.length - 1) {
			mIndex++;
		} else {
			mIndex = 0;
		}
		select();
	}

	@Override
	protected void onScrollUp() {
		super.onScrollUp();

		if (mIndex == -1)
			return;

		if (mIndex > 0) {
			mIndex--;
		} else {
			mIndex = mList.length - 1;
		}
		select();
	}

	@Override
	protected void onDoubleTap() {
		super.onDoubleTap();

		execute();
	}
}

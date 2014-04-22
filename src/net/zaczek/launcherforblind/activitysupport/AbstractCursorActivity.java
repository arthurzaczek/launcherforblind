package net.zaczek.launcherforblind.activitysupport;

import net.zaczek.launcherforblind.listentries.ListEntry;
import android.database.Cursor;
import android.util.Log;

public abstract class AbstractCursorActivity extends AbstractActivity {
	private static final String TAG = "launcherforblind";
	private Cursor mCursor;

	protected abstract Cursor getCursor();

	protected abstract ListEntry getListEntry(Cursor c);

	protected abstract void giveFeedback(String label);

	@Override
	protected void onResume() {
		super.onResume();

		mCursor = getCursor();
		if (mCursor != null && mCursor.moveToFirst()) {
			select();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void select() {
		final ListEntry entry = getListEntry(mCursor);
		final String label = entry.getLabel();
		Log.i(TAG, "Selecting " + label);
		giveFeedback(label);
		say(label);
	}

	private void execute() {
		final ListEntry entry = getListEntry(mCursor);
		say(entry.getTextToSay());
		entry.onSelected();
	}

	@Override
	protected void onScrollDown() {
		super.onScrollDown();

		if (mCursor == null)
			return;

		if (!mCursor.moveToNext() && !mCursor.moveToFirst()) {
			return;
		}
		select();
	}

	@Override
	protected void onScrollUp() {
		super.onScrollUp();

		if (mCursor == null)
			return;

		if (!mCursor.moveToPrevious() && !mCursor.moveToLast()) {
			return;
		}
		select();
	}

	@Override
	protected void onDoubleTap() {
		super.onDoubleTap();

		execute();
	}
}

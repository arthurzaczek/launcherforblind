package net.zaczek.launcherforblind;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class AbstractListActivity extends Activity {
	private static final String TAG = "lstactivity";
	private GestureDetectorCompat mDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

	protected void onSwipeDown() {
		Log.d(TAG, "onSwipeDown");
	}

	protected void onSwipeUp() {
		Log.d(TAG, "onSwipeUp");
	}

	protected void onSwipeRight() {
		Log.d(TAG, "onSwipeRight");
	}

	protected void onSwipeLeft() {
		Log.d(TAG, "onSwipeLeft");
	}

	private void onScrollUp() {
		Log.d(TAG, "onScrollUp");
	}

	private void onScrollDown() {
		Log.d(TAG, "onScrollDown");
	}

	private void onScrollLeft() {
		Log.d(TAG, "onScrollLeft");
	}

	private void onScrollRight() {
		Log.d(TAG, "onScrollRight");
	}
	
	protected void onTap() {
		Log.d(TAG, "onTap");
	}

	protected void onDoubleTap() {
		Log.d(TAG, "onDoubleTap");
	}

	protected void onLongPress() {
		Log.d(TAG, "onLongPress");
	}

	private float scrollSaveX = Float.NaN;
	private float scrollSaveY = Float.NaN;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);

		int action = event.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			scrollSaveX = event.getX();
			scrollSaveY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			computeScroll(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			computeScroll(event.getX(), event.getY());
			scrollSaveX = Float.NaN;
			scrollSaveY = Float.NaN;
			break;
		}
		return true;
	}

	private static final int SCROLL_MIN_DISTANCE = 100;

	private void computeScroll(float x, float y) {
		if (scrollSaveX == Float.NaN) {
			scrollSaveX = x;
		} else {
			if (x - scrollSaveX > SCROLL_MIN_DISTANCE) {
				onScrollRight();
				scrollSaveX = x;
			} else if (scrollSaveX - x > SCROLL_MIN_DISTANCE) {
				onScrollLeft();
				scrollSaveX = x;
			}
		}

		if (scrollSaveY == Float.NaN) {
			scrollSaveY = y;
		} else {
			if (y - scrollSaveY > SCROLL_MIN_DISTANCE) {
				onScrollDown();
				scrollSaveY = y;
			} else if (scrollSaveY - y > SCROLL_MIN_DISTANCE) {
				onScrollUp();
				scrollSaveY = y;
			}
		}
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			AbstractListActivity.this.onDoubleTap();
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			AbstractListActivity.this.onLongPress();
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			AbstractListActivity.this.onTap();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractListActivity.this.onSwipeLeft();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractListActivity.this.onSwipeRight();
				} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractListActivity.this.onSwipeUp();
				} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractListActivity.this.onSwipeDown();
				}
			} catch (Exception e) {
				// nothing
			}
			return true;
		}
	}
}

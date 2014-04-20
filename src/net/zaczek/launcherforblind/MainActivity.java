package net.zaczek.launcherforblind;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class MainActivity extends Activity {
	private static final String TAG = "launcherforblind";
	private GestureDetectorCompat mDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

	private VelocityTracker mVelocityTracker = null;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);

		// int index = event.getActionIndex();
		int action = event.getActionMasked();
		// int pointerId = event.getPointerId(index);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			} else {
				mVelocityTracker.clear();
			}
			mVelocityTracker.addMovement(event);
			break;
		case MotionEvent.ACTION_MOVE:
			mVelocityTracker.addMovement(event);
			// When you want to determine the velocity, call
			// computeCurrentVelocity(). Then call getXVelocity()
			// and getYVelocity() to retrieve the velocity for each pointer ID.
			mVelocityTracker.computeCurrentVelocity(1000);
			// Log velocity of pixels per second
			// Best practice to use VelocityTrackerCompat where possible.
			// Log.d("", "X velocity: " +
			// VelocityTrackerCompat.getXVelocity(mVelocityTracker,
			// pointerId));
			// Log.d("",
			// "Y velocity: "
			// + VelocityTrackerCompat.getYVelocity(
			// mVelocityTracker, pointerId));
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mVelocityTracker.recycle();
			mVelocityTracker = null;
			break;
		}
		return true;
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "Gestures";
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.d(DEBUG_TAG, "onDoubleTap");
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.d(DEBUG_TAG, "onLongPress");
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.d(DEBUG_TAG, "onSingleTapConfirmed");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// right to left swipe
					Log.d(DEBUG_TAG, "onFling: right to left swipe");
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// left to right flip
					Log.d(DEBUG_TAG, "onFling: left to right swipe");
				} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					// right to left swipe
					Log.d(DEBUG_TAG, "onFling: up to down swipe");
				} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					// left to right flip
					Log.d(DEBUG_TAG, "onFling: down to up swipe");
				}
			} catch (Exception e) {
				// nothing
			}
			return true;
		}
	}
}

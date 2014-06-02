package net.zaczek.launcherforblind.activitysupport;

import java.util.HashMap;

import net.zaczek.launcherforblind.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* 
 * Abstract activity for handling touch events and tts
 * */
public abstract class AbstractActivity extends Activity implements
		OnInitListener {
	private static final String TAG = "lstactivity";
	private GestureDetectorCompat mDetector;
	private TextToSpeech tts;
	private HashMap<String, String> ttsParams = new HashMap<String, String>();
	private boolean ttsInitialized = false;
	private Vibrator mVibe;
	private static final long[] PATTERN = { 0, 50 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "next");
	}

	@Override
	protected void onResume() {
		super.onResume();

		destroyTTS();
		tts = new TextToSpeech(this, this);
		tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
			@Override
			public void onUtteranceCompleted(String utteranceId) {
				onTTSCompleted();
			}
		});

		announceHelp();
	}

	@Override
	protected void onPause() {
		super.onPause();
		destroyTTS();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroyTTS();
	}

	private void destroyTTS() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
			tts = null;
		}
		ttsInitialized = false;
	}

	private CharSequence cachedSaying;

	public void say(CharSequence something) {
		if (TextUtils.isEmpty(something))
			return;

		if (ttsInitialized) {
			Log.d(TAG, "Saying: " + something);
			tts.speak(something.toString(), TextToSpeech.QUEUE_FLUSH, ttsParams);
		} else {
			cachedSaying = something;
		}
	}

	protected boolean announceHelp() {
		return false;
	}

	protected void onTTSCompleted() {

	}

	protected void vibe() {
		mVibe.vibrate(PATTERN, -1);
	}

	protected void onExecute() {

	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// tts.setLanguage(Locale.GERMAN);
			ttsInitialized = true;
			say(cachedSaying);
		} else {
			Log.e(TAG, "Initilization failed");
		}
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

	protected void onScrollUp() {
		Log.d(TAG, "onScrollUp");
	}

	protected void onScrollDown() {
		Log.d(TAG, "onScrollDown");
	}

	protected void onScrollLeft() {
		Log.d(TAG, "onScrollLeft");
	}

	protected void onScrollRight() {
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_CALL:
			onExecute();
			return true;
		case KeyEvent.KEYCODE_ENDCALL:
			startActivity(new Intent(this, MainActivity.class));
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private float scrollSaveX = Float.NaN;
	private float scrollSaveY = Float.NaN;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);

		int action = event.getAction();
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
			AbstractActivity.this.onDoubleTap();
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			AbstractActivity.this.onLongPress();
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			AbstractActivity.this.onTap();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractActivity.this.onSwipeLeft();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractActivity.this.onSwipeRight();
				} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractActivity.this.onSwipeUp();
				} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					AbstractActivity.this.onSwipeDown();
				}
			} catch (Exception e) {
				// nothing
			}
			return true;
		}
	}
}

package net.zaczek.launcherforblind;

import org.acra.*;
import org.acra.annotation.*;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

@ReportsCrashes(
		formKey = "", 
		formUri = "",
		mailTo = "launcherforblind-support@googlegroups.com",
		mode = ReportingInteractionMode.NOTIFICATION,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
        resNotifTickerText = R.string.crash_notif_ticker_text,
        resNotifTitle = R.string.crash_notif_title,
        resNotifText = R.string.crash_notif_text,
        resNotifIcon = android.R.drawable.stat_notify_error, // optional. default is a warning sign
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
        resDialogOkToast = R.string.crash_dialog_ok_toast // optional. displays a Toast message when the user accepts to send a report.
)
public class MyApplication extends Application implements OnInitListener {
	private static final String TAG = "app";
	private static Context context;
	protected int currentCallState;
	private BroadcastReceiver screenStateChangeReceiver;
	private TextToSpeech tts;
	private boolean ttsInitialized;

	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
		Log.i("launcherforblind", "Initializing ACRA");
		ACRA.init(this);

		tts = new TextToSpeech(this, this);
		ttsInitialized = false;

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				currentCallState = state;
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);

		// Receive notifications about the screen power changes
		screenStateChangeReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
					Log.e("currentCallState", currentCallState + "");
					// If the phone is ringing or the user is talking,
					// don't try do anything else.
					if (currentCallState != TelephonyManager.CALL_STATE_IDLE) {
						return;
					}
					if (ttsInitialized) {
						tts.speak(getString(R.string.please_unlock), 0, null);
					}
				}
			}
		};
		final IntentFilter screenStateChangeFilter = new IntentFilter(
				Intent.ACTION_SCREEN_ON);
		screenStateChangeFilter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(screenStateChangeReceiver, screenStateChangeFilter);
	}

	public static Context getAppContext() {
		return MyApplication.context;
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// tts.setLanguage(Locale.GERMAN);
			ttsInitialized = true;
		} else {
			Log.e(TAG, "Initilization failed");
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		if (screenStateChangeReceiver != null) {
			unregisterReceiver(screenStateChangeReceiver);
			screenStateChangeReceiver = null;
		}

		if (tts != null) {
			tts.stop();
			tts.shutdown();
			tts = null;
		}
		ttsInitialized = false;
	}
}

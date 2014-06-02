/*
 * Dialer activity
 * Used some code from the eyes-free-project/TaklingDialer
 * https://code.google.com/p/eyes-free/
 * */

package net.zaczek.launcherforblind;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import net.zaczek.launcherforblind.activitysupport.AbstractActivity;

public class DialerActivity extends AbstractActivity {
	private static final String TAG = "launcherforblind";
	private final double left = 0;

	private final double upleft = Math.PI * .25;
	private final double up = Math.PI * .5;
	private final double upright = Math.PI * .75;
	private final double downright = -Math.PI * .75;
	private final double down = -Math.PI * .5;
	private final double downleft = -Math.PI * .25;
	private final double right = Math.PI;
	private final double rightWrap = -Math.PI;

	private double downX;
	private double downY;

	private String currentValue;
	private String dialedNumber;

	private boolean confirmed = false;
	boolean screenIsBeingTouched = false;
	boolean screenVisible = true;

	private TextView txtMain;
	private TextView txtCurrentDigit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.dialer);

		say(getString(R.string.dialer));

		dialedNumber = "";

		txtMain = (TextView) findViewById(R.id.txtMain);
		txtCurrentDigit = (TextView) findViewById(R.id.txtCurrentDigit);
		txtCurrentDigit.setText(R.string.digit_help);
	}

	@Override
	protected boolean announceHelp() {
		if (Settings.announceDialerHelp()) {
			say(getString(R.string.digit_help));
			Settings.updateAnnounceDialerHelp();
			return true;
		}
		return false;
	}

	@Override
	protected void onExecute() {
		super.onExecute();
		callCurrentNumber();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		String prevVal = "";
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			screenIsBeingTouched = true;
			downX = x;
			downY = y;
			currentValue = "";
			break;
		case MotionEvent.ACTION_UP:
			screenIsBeingTouched = false;
			prevVal = currentValue;
			currentValue = evalMotion(x, y);
			// Do some correction if the user lifts up on deadspace
			if (currentValue.length() == 0) {
				currentValue = prevVal;
			}

			if ("-".equals(currentValue)) {
				say(getText(R.string.deleted));
				if (dialedNumber.length() > 0) {
					dialedNumber = dialedNumber.substring(0,
							dialedNumber.length() - 1);
				}
			} else if ("dial".equals(currentValue)) {
				callCurrentNumber();
			} else {
				confirmed = false; // new number invalidates the
				// confirmation
				say(currentValue);
				dialedNumber = dialedNumber + currentValue;
			}
			txtMain.setText(dialedNumber);
			break;
		default:
			screenIsBeingTouched = true;
			prevVal = currentValue;
			currentValue = evalMotion(x, y);
			// Do nothing since we want a deadzone here;
			// restore the state to the previous value.
			if (currentValue.length() == 0) {
				currentValue = prevVal;
				break;
			}
			if (prevVal != currentValue) {
				txtCurrentDigit.setText(currentValue);
				vibe();
			}
			break;
		}
		return true;
	}

	public String evalMotion(double x, double y) {
		float rTolerance = 25;
		double thetaTolerance = (Math.PI / 12);

		boolean movedFar = false;

		double r = Math.sqrt(((downX - x) * (downX - x))
				+ ((downY - y) * (downY - y)));

		if (r < rTolerance) {
			return "5";
		}
		if (r > 6 * rTolerance) {
			movedFar = true;
		}

		double theta = Math.atan2(downY - y, downX - x);

		if (Math.abs(theta - left) < thetaTolerance) {
			return movedFar ? "-" : "4";
		} else if (Math.abs(theta - upleft) < thetaTolerance) {
			return "1";
		} else if (Math.abs(theta - up) < thetaTolerance) {
			return movedFar ? "dial" : "2";
		} else if (Math.abs(theta - upright) < thetaTolerance) {
			return "3";
		} else if (Math.abs(theta - downright) < thetaTolerance) {
			return movedFar ? "#" : "9";
		} else if (Math.abs(theta - down) < thetaTolerance) {
			return movedFar ? "0" : "8";
		} else if (Math.abs(theta - downleft) < thetaTolerance) {
			return movedFar ? "*" : "7";
		} else if ((theta > right - thetaTolerance)
				|| (theta < rightWrap + thetaTolerance)) {
			return "6";
		}

		// Off by more than the threshold, so it doesn't count
		return "";
	}

	private void callCurrentNumber() {
		if (!confirmed) {
			if (dialedNumber.length() < 3) {
				// A number is considered invalid if less than 3 digits in
				// length.
				say(getString(R.string.invalid_number));
			} else {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < dialedNumber.length(); i++) {
					sb.append(dialedNumber.charAt(i) + " ");
				}
				Helper.confirmDial(this, sb.toString());
				confirmed = true;
			}
		} else {
			// actually dial
			Helper.dial(this, dialedNumber);
		}
	}
}

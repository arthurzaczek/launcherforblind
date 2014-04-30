package net.zaczek.launcherforblind;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Helper {
	public static boolean actuallyDial = true;
	
	public static void dial(Context ctx, String number) {
		if (actuallyDial) {
			ctx.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Uri.encode(number))));
		}
	}
}

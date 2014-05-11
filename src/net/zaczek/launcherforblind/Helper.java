package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Helper {
	public static boolean actuallyDial = true;

	public static void confirmDial(AbstractActivity ctx, String label) {
		StringBuilder sb = new StringBuilder();
		sb.append(ctx.getString(R.string.you_are_about_to_dial));
		sb.append(" " + label);
		ctx.say(sb.toString());
	}

	public static void dial(Context ctx, String number) {
		if (actuallyDial) {
			ctx.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Uri.encode(number))));
		}
	}
}

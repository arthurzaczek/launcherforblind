package net.zaczek.launcherforblind;

import net.zaczek.launcherforblind.activitysupport.AbstractActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

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

	/*
	 * Returns the contact name to a given number or returns the number if no
	 * name was found
	 */
	public static String getContactName(Context ctx, String phoneNumber) {
		final ContentResolver cr = ctx.getContentResolver();
		final Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phoneNumber));
		Cursor cursor = null;
		String contactName = phoneNumber;
		try {
			cursor = cr.query(uri, new String[] { PhoneLookup.DISPLAY_NAME },
					null, null, null);
			if (cursor == null) {
				return null;
			}

			if (cursor.moveToFirst()) {
				contactName = cursor.getString(cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			}
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		return contactName;
	}
}

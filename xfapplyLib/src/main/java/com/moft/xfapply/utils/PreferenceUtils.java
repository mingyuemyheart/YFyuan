package com.moft.xfapply.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {

	public static String datastore = "settingInfo";

	public static String getPrefString(Context context, String key,
			final String defaultValue) {
		final SharedPreferences settings = context.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	public static void setPrefString(Context context, final String key,
			final String value) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		settings.edit().putString(key, value).commit();
	}

	public static String getPrefString(Context context, String key,
									   final String defaultValue, int mode) {
		final SharedPreferences settings = context.getSharedPreferences(datastore, mode);
		return settings.getString(key, defaultValue);
	}

	public static void setPrefString(Context context, final String key,
									 final String value, int mode) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, mode);
		settings.edit().putString(key, value).commit();
	}

	public static boolean getPrefBoolean(Context context, final String key,
			final boolean defaultValue) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}

	public static void setPrefBoolean(Context context, final String key,
									  final boolean value) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		settings.edit().putBoolean(key, value).commit();
	}

	public static boolean getPrefBoolean(Context context, final String key,
										 final boolean defaultValue, int mode) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, mode);
		return settings.getBoolean(key, defaultValue);
	}

	public static void setPrefBoolean(Context context, final String key,
									  final boolean value, int mode) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, mode);
		settings.edit().putBoolean(key, value).commit();
	}

	public static boolean hasKey(Context context, final String key) {
		return context.getSharedPreferences(datastore, Context.MODE_PRIVATE)
				.contains(key);
	}

	public static void setPrefInt(Context context, final String key,
			final int value) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		settings.edit().putInt(key, value).commit();
	}

	public static int getPrefInt(Context context, final String key,
			final int defaultValue) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}

	public static void setPrefFloat(Context context, final String key,
			final float value) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		settings.edit().putFloat(key, value).commit();
	}

	public static float getPrefFloat(Context context, final String key,
			final float defaultValue) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	public static void setSettingLong(Context context, final String key,
			final long value) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		settings.edit().putLong(key, value).commit();
	}

	public static long getPrefLong(Context context, final String key,
			final long defaultValue) {
		final SharedPreferences settings = context.getApplicationContext()
				.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}

	public static void clearPreference(Context context,
			final SharedPreferences p) {
		final Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}
}
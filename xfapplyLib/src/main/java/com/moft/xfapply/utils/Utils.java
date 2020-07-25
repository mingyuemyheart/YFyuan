package com.moft.xfapply.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	// 虚拟键手机获取menu键
	public static void getMenu(Context context) {
		try {
			((Activity) context).getWindow().addFlags(
					WindowManager.LayoutParams.class.getField(
							"FLAG_NEEDS_MENU_KEY").getInt(null));
		} catch (NoSuchFieldException e) {

		} catch (IllegalAccessException e) {
			Log.w("feelyou.info",
					"Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()",
					e);
		}
	}

	public static Integer ToInteger(String sd) {
		Integer result = null;

		if (null != sd && !"".equals(sd)) {
			try {
				result = Integer.valueOf(sd);
			} catch (NumberFormatException e) {
				result = null;
			}
		}

		return result;
	}

	public static Double convertToDouble(String sd) {
		Double result = null;

		if (!StringUtil.isEmpty(sd)) {
			try {
				result = Double.valueOf(sd);
			} catch (NumberFormatException e) {
				result = null;
			}
		}

		return result;
	}

	public static String ToString(Object db) {
		String result = "";

		if (null != db) {
			result = db.toString();
		}

		return result;
	}

	public static String ToString(Double db) {
		String result = "";

		if (null != db) {
			result = db.toString();
		}

		return result;
	}

	public static String ToString(Integer db) {
		String result = "";

		if (null != db) {
			result = db.toString();
		}

		return result;
	}

	public static String ToString(Boolean db) {
		String result = "";

		if (null != db) {
			result = db.toString();
		}

		return result;
	}

	public static Boolean ToBoolean(String sd) {
		Boolean result = null;

		if (null != sd && !"".equals(sd)) {
			try {
				result = Boolean.valueOf(sd);
			} catch (NumberFormatException e) {
				result = null;
			}
		}

		return result;
	}
	// 把日期转为字符串
	@SuppressLint("SimpleDateFormat")
	public static String ConverToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return df.format(date);
	}

	// 当前日期
	@SuppressLint("SimpleDateFormat")
	public static Date CurrentDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.parse(strDate);
	}

	// 把字符串转为日期
	@SuppressLint("SimpleDateFormat")
	public static Date ConverToDate(String strDate) {
		Date result = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			result = df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static void setImageSize(Context context, ImageView img, int size) {
		ViewGroup.LayoutParams params = img.getLayoutParams();
		params.height = WindowUtil.dp2px(context, size);
		params.width = params.height;
		img.setLayoutParams(params);
	}
}

package com.it.xushuai.baseapp.util;

import android.util.Log;

/**
 * 自定义的日志类，可开关�?
 * 
 */
public class Logger {
	
	private static final String TAG = "GroupByLog";
	
	/**
	 * DEBUG 值为true 时，日志为开�?值为 false 时，日志关闭，应用发布时，应置为false.
	 */
	public static final boolean DEBUG = false;

	public static void v(String tag, String msg) {
		if (DEBUG) {
			Log.v(TAG, tag + " " + msg);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUG) {
			Log.d(TAG, tag + " " + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(TAG, tag + " " + msg);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			Log.v(TAG, tag + " " + msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.e(TAG, tag + " " + msg);
		}
	}
}

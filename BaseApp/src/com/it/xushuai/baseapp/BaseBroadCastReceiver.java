package com.it.xushuai.baseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class BaseBroadCastReceiver extends BroadcastReceiver{

	/**
	 * 网络状态变更的广播
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		 if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
	            AppContext.mNetWorkState = ((AppContext)context.getApplicationContext()).getNetworkType();
	    }
	}
	
}

package com.it.xushuai.baseapp.activity;

import java.io.Serializable;

import com.it.xushuai.baseapp.AppManager;
import com.mobclick.android.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	private AppManager appManager = AppManager.getAppManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appManager.addActivity(this);
		MobclickAgent.onError(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		appManager.finishActivity(this);
	}
	
	/**
     * Get serializable extra from activity's intent
     *
     * @param name
     * @return extra
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
            return (V)getIntent().getSerializableExtra(name);
    }

    /**
     * Get string extra from activity's intent
     *
     * @param name
     * @return extra
     */
    protected String getStringExtra(final String name) {
            return getIntent().getStringExtra(name);
    }
	
	protected abstract void initViews();
	protected abstract void initEvents();
		
}

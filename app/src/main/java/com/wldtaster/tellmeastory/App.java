package com.wldtaster.tellmeastory;

import android.content.Context;

/**
 * Created by Georges on 15/04/2016.
 */
public class App extends AnalyticsApplication //android.app.Application
{
	private static App mApp = null;

	public static Context context() {
		return mApp.getApplicationContext();
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
	}
}
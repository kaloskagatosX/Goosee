/**
 * @version 0.0.1
 * @since 12/04/2016
 */

package com.wldtaster.tellmeastory;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class LibraryActivity extends AppCompatActivity {
	private static final String TAG = LibraryActivity.class.getSimpleName();
	private static final String GoogleAnalyticsName = "LibraryActivity";
	private Tracker mTracker; // Google Analytics Tracker

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); /**Create the activity and populate the savedInstanceState if the activity existed but has been destroyed (otherwise savedInstanceState will return 'null'*/
		setContentView(R.layout.activity_library); /** Call to the XML layout library which display the activity */

		if (savedInstanceState != null) {
			// Restore value of members from saved state
		} else {
			// Probably initialize members with default values for a new instance
		}

		try {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			BooksFragment bookFragment = new BooksFragment();
			fragmentTransaction.add(R.id.library_activity_recycler_view, bookFragment);
			fragmentTransaction.commit();


			//Google Analytics - Obtain the shared Tracker instance.
			Log.d(TAG, "<<Initialising Google Analytics>>");
			AnalyticsApplication application = (AnalyticsApplication) getApplication();
			mTracker = application.getDefaultTracker();

			// Google Analytics - tracking code
			Log.d(TAG, "Setting screen name: " + GoogleAnalyticsName);
			mTracker.setScreenName("Image~" + GoogleAnalyticsName);
			mTracker.enableAdvertisingIdCollection(true);
			mTracker.send(new HitBuilders.ScreenViewBuilder().build());


		} catch (Exception e) {
			Log.d(TAG, "[ERROR] " + e.getMessage());
		}
	}
}

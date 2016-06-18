/**
 * SectionActivity manages the activity_section layout and which is displayed when a section is selected
 * TODO Need to add code to manage sections which do not have audio associated to it!
 */
package com.wldtaster.tellmeastory;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.wldtaster.tellmeastory.AudioService.AudioBinder;

import java.util.ArrayList;

public class SectionActivity extends Activity {

	private static final String TAG = "SectionActivity"; //Tag to identify the source of the errors
	static SectionActivity sectionActivity; //Store the Activity SectionActivity
	private Tracker mTracker; // Google Analytics Tracker
	private AudioService currentAudioService;
	private ServiceConnection audioConnection;
	private Intent playIntent; //This is the playIntent which is used to launch the service
	private boolean audioBound = false;

	private SectionItem selectedSectionItem; //Store the current Section
	private int SectionPos = 0; //Store the index of the current section (starting with zero for the 1st section)
	private BookItem selectedBookItem; //Store the selected Book
	private ArrayList<SectionItem> SectionItems; //Store the list of sections in the selected book

	private MediaController SectionMediaController; //Create a MediaController which will enable the User to Play, Post, Fast forward, Fast backward and move to the next / previous Sections

	public static SectionActivity getInstance() {
		return sectionActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right); //Animate the activity IN/OUT
		setContentView(R.layout.activity_section);











        /*Store the this Activity in 'sectionActivity' */
		sectionActivity = this;

		/* Retrieve the book selected in the LibraryActivity and store it in 'selectedBookItem'*/
		selectedBookItem = (BookItem) getIntent().getSerializableExtra(Constants.bookItemObject);

		/*Store all the sections of the selected Book into 'SectionItems' */
		BooksAndSections tmpBookCollection = new BooksAndSections();
		SectionItems = tmpBookCollection.getSectionItems(selectedBookItem.getBookID());

		/*Create the MediaController control and show it at the bottom of the SectionActivity*/
		CreateSectionMediaController();


		try {
			//Google Analytics - Obtain the shared Tracker instance.
			Log.d(TAG, "<<Initialising Google Analytics>>");
			AnalyticsApplication application = (AnalyticsApplication) getApplication();
			mTracker = application.getDefaultTracker();

			// Google Analytics - tracking code
			String GoogleAnalyticsName;
			GoogleAnalyticsName = "SectionActivity Book ID:" + selectedBookItem.getBookID();
			Log.d(TAG, "Setting screen name: " + GoogleAnalyticsName);
			mTracker.setScreenName("Image~" + GoogleAnalyticsName);
			mTracker.enableAdvertisingIdCollection(true);
			mTracker.send(new HitBuilders.ScreenViewBuilder().build());
		} catch (Exception e) {
			String GoogleAnalyticsName = "SectionActivity Book ID:" + selectedBookItem.getBookID();
			Log.d(TAG, ">>> Error in Setting screen name: " + GoogleAnalyticsName + " ---- " + e.getMessage());
		}




		/*Display the information of the first Section (text in EN, text in HK, miniature of the page where the section is located) */
		refreshSectionActivity(0);


	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d(TAG, "<<onConfigurationChanged...>>");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "<<onBackPressed activated...>>");
		finish();
	}

	public android.widget.MediaController getActiveMediaController() {
		return SectionMediaController;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------
	public void CreateSectionMediaController() {

		/**Create a new MediaController and store it into 'SectionMediaController' */
		SectionMediaController = new MediaController(this, false) { //The 'false' in 'MediaController(this, false)' hides the fast forward and fast backward buttons

			//Override its hide() function to prevent it to hide every x seconds. However it needs to be hidden again before the Activity is destroyed!
			@Override
			public void hide() {
			}


			//The Media Controller capture the "back button" on the Android App and therefore prevents the User from going one step back. The code below override this.
			@Override
			public boolean dispatchKeyEvent(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						return true;
					} else if (event.getAction() == KeyEvent.ACTION_UP) {
						((Activity) getContext()).onBackPressed();
						super.hide();
						return true;
					}
				}
				return super.dispatchKeyEvent(event);
			}
		};

		/**Execute code when the User click on the 'Play next' track or 'Play previous' track*/
		SectionMediaController.setPrevNextListeners(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Play Next
				refreshSectionActivity(1);
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Play Previous
				refreshSectionActivity(-1);
			}
		});

	}
	//-----------------------------------------------------------------------------------------------------------------------------------------

	public void AudioConnection() {

		//Try to kill the Service if it already exists
		try {
			AttemptToDestroyAudioService();
			Log.d(TAG, "<<'AttemptToDestroyAudioService()' completed>>");
		} catch (Exception e) {
			Log.d(TAG, "<<Was not able to destroy the Service :" + e.getMessage() + " >>");
		}
		Log.d(TAG, "<<Entering AudioConnection...>>");
		audioConnection = null;
		audioConnection = new ServiceConnection() {


			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "<<-------------------------------------------------------------------------------------------------------------------------------------------------->>");
				Log.d(TAG, "<<Connecting Service...>>");
				AudioBinder binder = (AudioBinder) service;
				currentAudioService = binder.getService();
				currentAudioService.setAudio(selectedSectionItem.getAudioResource());
				Log.d(TAG, "<<Getting Audio Resource from Section " + selectedSectionItem.getSectionNumber() + ">>");
				audioBound = true;
				Log.d(TAG, "<<Service connected...>>");


				if (currentAudioService != null) {
					Log.d(TAG, "<<currentAudioService is NOT 'null', well done!>> ");


					try {
						if (currentAudioService == null) {
							Log.d(TAG, "<<<<<<<<<<<<<<<< currentAudioService is 'nul'!!!!!!!!!!!!!!!!!! >>>>>>>>>>>>>>>>>>>>>>>");
						}

						Log.d(TAG, "<<currentAudioService.loadAudio()>>");
						currentAudioService.loadAudio();

						Log.d(TAG, "<<Setting up SectionMediaController>>");

						SectionMediaController.setMediaPlayer(currentAudioService);
						Log.d(TAG, "<<setMediaPlayer(currentAudioService)>>");
						SectionMediaController.setAnchorView(findViewById(R.id.LinearLayoutSectionId));
						Log.d(TAG, "<<setAnchorView(findViewById(R.id.LinearLayoutSectionId))>>");
						SectionMediaController.setEnabled(true);
						Log.d(TAG, "<<setEnabled(true)>>");
						SectionMediaController.show();
						Log.d(TAG, "<<SectionMediaController.show()>>");

					} catch (Exception e) {
						Log.d(TAG, "Cannot instantiate 'SectionMediaController' :(! " + e.getMessage());
					}


				} else {
					Log.d(TAG, "<<currentAudioService is 'null' !!!!!!!!!!!!!>>");
				}

				Log.d(TAG, "<<-------------------------------------------------------------------------------------------------------------------------------------------------->>");

			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "<<****************************************** onServiceDisconnected... ******************************************>>");
				audioBound = false;
			}
		};

		if (playIntent == null) {
			playIntent = new Intent(this, AudioService.class);
			boolean bindReturn;
			bindReturn = bindService(playIntent, audioConnection, Context.BIND_AUTO_CREATE);
			startService(playIntent);
			Log.d(TAG, "<<bindService() established>>");
		}

	}


	@Override
	protected void onDestroy() {

		super.onDestroy();
		AttemptToDestroyAudioService();
		if (SectionMediaController.isShowing()) {
			Log.d(TAG, "<< Unfortunately the 'SectionMediaController' is still showing (not hidden) at the end of 'onDestroy' !>>");
		}
	}

	void AttemptToDestroyAudioService() {
		Log.d(TAG, "<<Attempting to stop the service>>");
		try {


			if ((currentAudioService != null) || (playIntent != null)) {
				Log.d(TAG, "<<Attempting to stop the Audio Service>>");
				stopService(playIntent);
				currentAudioService = null;
				playIntent = null;
				Log.d(TAG, "<<Audio Service stopped>>");
			}

			if (audioConnection != null) {
				Log.d(TAG, "<<Attempting to unbindService(audioConnection)>>");
				try {
					unbindService(audioConnection);
					audioConnection = null;
				} catch (Exception e) {
					Log.d(TAG, "<<Error in attempting to unbindService(audioConnection) " + e.getMessage() + " >>");
				}
				Log.d(TAG, "<<Did unbindService(audioConnection)>>");
			}


			audioBound = false;
			//audioConnection=null;
		} catch (Exception e) {
			Log.d(TAG, "<<Error while attempting to destroy the service " + e.getMessage() + " >>");
		}
		Log.d(TAG, "<< No Exception has been raised in 'AttemptToDestroyAudioService'>>");

	}


	//-----------------------------------------------------------------------------------------------------------------------------------------
	void refreshSectionActivity(int changeSectionNumberBy) {

		int TestSectionPos = SectionPos + changeSectionNumberBy;

		if ((TestSectionPos >= 0) && (TestSectionPos < SectionItems.size())) {
			SectionPos = TestSectionPos;
		}


		selectedSectionItem = SectionItems.get(SectionPos);
		Log.d(TAG, "<< The new 'selectedSectionItem' has the index: " + selectedSectionItem.getSectionNumber() + ">>");


		TextView SectionTextViewBookName_EN = (TextView) findViewById(R.id.textViewBookName_EN);
		SectionTextViewBookName_EN.setText(selectedBookItem.getBookName_HK() + " -  Section " + selectedSectionItem.getSectionNumber() + " / " + SectionItems.size());


		TextView SectionTextViewDescription_EN = (TextView) findViewById(R.id.textViewText_EN);
		SectionTextViewDescription_EN.setText(selectedSectionItem.getText_EN());
		ImageView SectionPageImageView = (ImageView) findViewById(R.id.imageViewSectionPage);

		TextView SectionTextViewDescription_HK = (TextView) findViewById(R.id.textViewText_HK);
		SectionTextViewDescription_HK.setText(selectedSectionItem.getText_HK());
		SectionPageImageView.setImageResource(selectedSectionItem.getSectionPhotoID());

		Log.d(TAG, "<<Calling 'AudioConnection()'>>");
		AudioConnection();
		Log.d(TAG, "<<Called 'AudioConnection()'>>");


		// Google Analytics - tracking code
		try {
			String GoogleAnalyticsName;
			GoogleAnalyticsName = "SectionActivity Book ID:" + selectedBookItem.getBookID() + " Section #:" + selectedSectionItem.getSectionNumber();
			Log.d(TAG, ">>> SETTING EVENTS: " + GoogleAnalyticsName);
			mTracker.setScreenName("Image~" + GoogleAnalyticsName);
			mTracker.enableAdvertisingIdCollection(true);
			mTracker.send(new HitBuilders.ScreenViewBuilder().build());


			mTracker.send(new HitBuilders.EventBuilder()
					.setCategory("Section")
					.setAction(GoogleAnalyticsName)
					.setNonInteraction(true)
					.setLabel(GoogleAnalyticsName)
					.build());

		} catch (Exception e) {
			String GoogleAnalyticsName = "SectionActivity Book ID:" + selectedBookItem.getBookID() + " Section #:" + selectedSectionItem.getSectionNumber();
			Log.d(TAG, ">>> Error in Setting screen name: " + GoogleAnalyticsName + " ---- " + e.getMessage());
		}

		//getWindow().getAttributes().windowAnimations = R.style.Fade;
	}

}

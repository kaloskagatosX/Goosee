package com.wldtaster.tellmeastory;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.MediaController;


/**
 * AudioService is the service which will play audio files (stored in the Res->Raw folder)
 */
public class AudioService extends Service implements MediaController.MediaPlayerControl, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {

	private static final String TAG = "AudioService"; //Tag to identify the source of the errors
	private final IBinder audioBind = new AudioBinder(); //Create an IBinder object which enables the Activities to create a connection to the AudioService service
	private MediaPlayer player; //Create a MediaPlayer object 'player' which will be used to play the audio files
	private int selectedAudioResourceID; //Store the resource ID of the audio file of the selected section
	private AudioManager mAudioManager; //Create an AudioManager object which will request the focus to the phone audio channel

	private boolean mFocusGranted, mFocusChanged; //Booleans to check the focus

	/**
	 * Initiated when the service is created
	 */
	public void onCreate() {
		super.onCreate(); //Create the service
		player = new MediaPlayer(); //Create a new MediaPlayer object and store it into 'player'


		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //Create a new Audio Manager
		int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN); //Request Audio Focus and store outcomes in Result

		switch (result) {
			case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
				// Could  receive audio focus.
				mFocusGranted = true;
				Log.d(TAG, "Audio focus granted!");
				initMusicPlayer();
				break;
			case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
				// Could not get audio focus.
				mFocusGranted = false;
				Log.d(TAG, "Audio focus NOT granted!");
				break;
		}


	}

	/**
	 * Initialise the MediaPlayer at the creation of the service
	 */
	public void initMusicPlayer() {
		//Set player properties
		try {
			player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK); //Set the low-level power management behavior for the  MediaPlayer, PARTIAL_WAKE_LOCK: Ensures that the CPU is running; the screen and keyboard backlight will be allowed to go off.
			player.setAudioStreamType(AudioManager.STREAM_MUSIC); //Sets the audio stream type for this MediaPlayer. STREAM_MUSIC: The audio stream for music playback
			player.setOnPreparedListener(this);
			player.setOnCompletionListener(this);
			player.setOnErrorListener(this);
		} catch (Exception e) {
			Log.d(TAG, "Error in 'initMusicPlayer'", e);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return audioBind;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		player.stop();
		player.release();
		return false;
	}

	public void loadAudio() {
		//Launched when Audio is played

		try {
			AssetFileDescriptor afd = App.context().getResources().openRawResourceFd(selectedAudioResourceID); //Retrieve the Audio file and store it in 'afd'
			//player.reset();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			//player.setOnErrorListener(this);
			//player.setOnPreparedListener(this);
			player.prepareAsync();

			afd.close();


		} catch (Exception e) {
			Log.d(TAG, "Error in 'play audio'", e);
		}


	}

	public void setAudio(int tmpAudioResourceID) {
		selectedAudioResourceID = tmpAudioResourceID;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		//mp.start();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// do stuff here
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// do stuff here
		return true;
	}

	public void pause() {
		player.pause();
	}

	public boolean canSeekBackward() {
		return true;
	}

	public boolean canSeekForward() {
		return true;
	}

	public boolean isPlaying() {
		boolean tmpIsPlaying;
		try {
			tmpIsPlaying = player.isPlaying();
		} catch (Exception e) {
			tmpIsPlaying = false;
			Log.d(TAG, "Error in 'isPlaying': " + e.getMessage());
		}
		return tmpIsPlaying;
	}

	public void start() {
		try {
			player.start();
		} catch (Exception e) {
			Log.d(TAG, "Error in 'start()': " + e.getMessage());
		}


        /*
        Intent notIntent = new Intent(this, LibraryActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.default_book)
                .setTicker("SongTitle")
                .setOngoing(true)
                .setContentTitle("Playing")
        .setContentText("songTitle");
        Notification not = builder.build();

        startForeground(1, not);
        */
	}

	public boolean canPause() {
		return true;
	}

	public int getBufferPercentage() {
		return 0;
	}

	public int getAudioSessionId() {
		return 0;
	}

	public void seekTo(int i) {
		player.seekTo(i);
	}

	public int getDuration() {
		int tmpCurrentDuration;
		try {
			tmpCurrentDuration = player.getDuration();
		} catch (Exception e) {
			tmpCurrentDuration = 1;
			Log.d(TAG, "Error in 'getDuration': " + e.getMessage());
		}
		return tmpCurrentDuration;
	}

	public int getCurrentPosition() {
		int tmpCurrentPosition;

		try {
			tmpCurrentPosition = player.getCurrentPosition();
		} catch (Exception e) {
			tmpCurrentPosition = 1;
			Log.d(TAG, "Error in 'getCurrentPosition': " + e.getMessage());
		}
		return tmpCurrentPosition;
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		mFocusChanged = true;
		Log.d(TAG, "Focus changed");

		switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
				Log.d(TAG, "AUDIOFOCUS_GAIN");
				// resume playback
				try {
					if (player == null) initMusicPlayer();
					else if (!player.isPlaying()) player.start();
				} catch (Exception e) {
					Log.d(TAG, "AUDIOFOCUS_GAIN ERROR !", e);
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				Log.d(TAG, "AUDIOFOCUS_LOSS");
				try {
					if (player.isPlaying()) player.pause();
				} catch (Exception e) {
					mAudioManager.abandonAudioFocus(this);
					Log.d(TAG, "AUDIOFOCUS_LOSS ERROR !", e);

				}

			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				// Lost focus for a short time, but we have to stop
				// playback. We don't release the media player because playback
				// is likely to resume
				Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
				try {
					if (player.isPlaying()) player.pause();
				} catch (Exception e) {
					mAudioManager.abandonAudioFocus(this);
					Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT ERROR !", e);
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				// Lost focus for a short time, but it's ok to keep playing
				// at an attenuated level
				try {
					if (player.isPlaying()) player.pause();
				} catch (Exception e) {
					Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK !", e);
				}
				break;
		}
	}

	/**
	 * Enables the Activities to call 'getService' to create a connection to the AudioService class
	 */
	public class AudioBinder extends Binder {
		AudioService getService() {
			return AudioService.this;
		}
	}
}

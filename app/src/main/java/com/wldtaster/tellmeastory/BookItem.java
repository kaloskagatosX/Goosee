package com.wldtaster.tellmeastory;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * This class defined the Book object
 */


public class BookItem implements Comparable<BookItem>, Serializable {

	private static final String TAG = BookItem.class.getSimpleName();
	private String BookID;
	private String BookName_EN;
	private String BookName_HK;
	private String NumberPages;
	private String BookDescription;
	private String BookURL;
	private String BookPhotoID;
	private String BookLevel;
	private String ReleaseDate;
	private String SoundID;
	private String Comments;
	private String LastSectionID;
	private String Status;
	private Boolean LastReadBook;

	@Override
	public int compareTo(BookItem CompareBookItem) {
		return CompareBookItem.getReleaseDate().compareTo(this.getReleaseDate());
	}

	String getBookID() {
		return BookID;
	}

	/**
	 * Create a TAG for the Log data
	 */

	//--------------------------------------------------------
	void setBookID(String tmpBookID) {
		BookID = tmpBookID;
	}
	//--------------------------------------------------------

	String getBookName_EN() {
		return BookName_EN;
	}

	//--------------------------------------------------------
	void setBookName_EN(String tmpBookName_EN) {
		BookName_EN = tmpBookName_EN;
	}
	//--------------------------------------------------------

	String getBookName_HK() {
		return BookName_HK;
	}

	//--------------------------------------------------------
	void setBookName_HK(String tmpBookName_HK) {
		BookName_HK = tmpBookName_HK;
	}
	//--------------------------------------------------------

	String getNumberPages() {
		return NumberPages;
	}

	//--------------------------------------------------------
	void setNumberPages(String tmpNumberPages) {
		NumberPages = tmpNumberPages;
	}
	//--------------------------------------------------------

	String getBookDescription() {
		return BookDescription;
	}

	//--------------------------------------------------------
	void setBookDescription(String tmpBookDescription) {
		BookDescription = tmpBookDescription;
	}
	//--------------------------------------------------------

	String getBookURL() {
		return BookURL;
	}

	//--------------------------------------------------------
	void setBookURL(String tmpBookURL) {
		BookURL = tmpBookURL;
	}
	//--------------------------------------------------------

	//--------------------------------------------------------
	int getBookPhotoID() {
		String MiniatureResourceName = "";
		if ((BookPhotoID == null) || (BookPhotoID.length() < 1)) {
			MiniatureResourceName = "m" + getBookID();
		} else {
			MiniatureResourceName = BookPhotoID;
		}
		int resId = App.context().getResources().getIdentifier(MiniatureResourceName, "drawable", App.context().getPackageName());

		/**If the resource for the book cannot be found, used the default image*/
		if (resId == 0) {
			resId = App.context().getResources().getIdentifier("default_book", "drawable", App.context().getPackageName());
		}

		return resId;
	}

	//--------------------------------------------------------
	void setBookPhotoID(String tmpBookPhotoID) {
		BookPhotoID = tmpBookPhotoID;
	}
	//--------------------------------------------------------

	String getBookLevel() {
		return BookLevel;
	}

	//--------------------------------------------------------
	void setBookLevel(String tmpBookLevel) {
		BookLevel = tmpBookLevel;
	}
	//--------------------------------------------------------

	Date getReleaseDate() {
	        /*TODO add try/catch to capture potential errors while converting string to a date*/
		SimpleDateFormat format = new SimpleDateFormat(Constants.bookReleaseDateFormat, Locale.ENGLISH);
		try {
			Date convertedReleaseDate = format.parse(ReleaseDate);
			return convertedReleaseDate;
		} catch (ParseException e) {
			Log.d(TAG, e.getMessage());
			return null;
		}
	}

	//--------------------------------------------------------
	void setReleaseDate(String tmpReleaseDate) {
		ReleaseDate = tmpReleaseDate;
	}

	String getReleaseDateAsString() {
		SimpleDateFormat tmpformat = new SimpleDateFormat(Constants.bookReleaseDateFormat, Locale.ENGLISH);
		String tmpString = tmpformat.format(getReleaseDate());
		return tmpString;
	}
	//--------------------------------------------------------

	String getSoundID() {
		return SoundID;
	}

	//--------------------------------------------------------
	void setSoundID(String tmpSoundID) {
		SoundID = tmpSoundID;
	}
	//--------------------------------------------------------

	String getComments() {
		return Comments;
	}

	//--------------------------------------------------------
	void setComments(String tmpComments) {
		Comments = tmpComments;
	}
	//--------------------------------------------------------

	String getLastSectionID() {
		return LastSectionID;
	}

	//--------------------------------------------------------
	void setLastSectionID(String tmpLastSectionID) {
		LastSectionID = tmpLastSectionID;
	}
	//--------------------------------------------------------

	String getStatus() {
		return Status;
	}

	//--------------------------------------------------------
	void setStatus(String tmpStatus) {
		Status = tmpStatus;
	}
	//--------------------------------------------------------

	Boolean getLastReadBook() {
		return LastReadBook; //Boolean.parseBoolean()
	}

	//--------------------------------------------------------
	void setLastReadBook(Boolean tmpLastReadBook) {
		LastReadBook = tmpLastReadBook;
	}
	//--------------------------------------------------------

	//--------------------------------------------------------
	int getAudioResource() {
		String AudioResourceName = "";
		if ((SoundID == null) || (SoundID.length() < 1)) {
			AudioResourceName = "audio_" + getBookID();
		} else {
			AudioResourceName = SoundID;
		}


		int resId = App.context().getResources().getIdentifier(AudioResourceName, "raw", App.context().getPackageName());

		/**If the resource for the book cannot be found, used the default audio*/
		if (resId == 0) {
		    /*TODO The line below needs to be deleted as we do not need to play a default audio song for each book*/
			resId = App.context().getResources().getIdentifier("default_book", "raw", App.context().getPackageName());
		}

		return resId;
	}
	//--------------------------------------------------------


}

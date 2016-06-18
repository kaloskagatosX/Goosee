package com.wldtaster.tellmeastory;

import android.util.Log;

/**
 * This class defined the Section object
 */
public class SectionItem implements Comparable<SectionItem> {

	private static final String TAG = "SectionItem"; //Tag to identify the source of the errors
	private String SectionID;
	private String SectionNumber;
	private String BookID;
	private String Text_EN;
	private String Text_HK;
	private String PageNumber;
	private String PagePhotoID;
	private String SoundID;
	private String Comments;

	@Override
	public int compareTo(SectionItem CompareSectionItem) {
		Integer ThisSectionNumber = Integer.parseInt(this.getSectionNumber());
		Integer CompareSectionNumber = Integer.parseInt(CompareSectionItem.getSectionNumber());

		return ThisSectionNumber.compareTo(CompareSectionNumber);
	}

	String getSectionID() {
		return SectionID;
	}

	//--------------------------------------------------------
	void setSectionID(String tmpSectionID) {
		SectionNumber = tmpSectionID;
	}
	//--------------------------------------------------------

	String getSectionNumber() {
		return SectionNumber;
	}

	//--------------------------------------------------------
	void setSectionNumber(String tmpSectionNumber) {
		SectionNumber = tmpSectionNumber;
	}
	//--------------------------------------------------------

	String getBookID() {
		return BookID;
	}

	//--------------------------------------------------------
	void setBookID(String tmpBookID) {
		BookID = tmpBookID;
	}
	//--------------------------------------------------------

	String getText_EN() {
		return Text_EN;
	}

	//--------------------------------------------------------
	void setText_EN(String tmpText_EN) {
		Text_EN = tmpText_EN;
	}
	//--------------------------------------------------------

	String getText_HK() {
		return Text_HK;
	}

	//--------------------------------------------------------
	void setText_HK(String tmpText_HK) {
		Text_HK = tmpText_HK;
	}
	//--------------------------------------------------------

	String getPageNumber() {
		return PageNumber;
	}

	//--------------------------------------------------------
	void setPageNumber(String tmpPageNumber) {
		PageNumber = tmpPageNumber;
	}
	//--------------------------------------------------------

	String getPagePhotoID() {
		return PagePhotoID;
	}

	//--------------------------------------------------------
	void setPagePhotoID(String tmpPagePhotoID) {
		PagePhotoID = tmpPagePhotoID;
	}
	//--------------------------------------------------------

	String getSoundID() {
		return SoundID;
	}

	//--------------------------------------------------------
	void setSoundID(String tmpSoundID) {
		SoundID = tmpSoundID;
	}

	String getComments() {
		return Comments;
	}

	//--------------------------------------------------------
	//--------------------------------------------------------
	void setComments(String tmpComments) {
		Comments = tmpComments;
	}
	//--------------------------------------------------------

	//--------------------------------------------------------
	int getSectionPhotoID() {

		String MiniatureResourceName;
		MiniatureResourceName = PagePhotoID;

		int resId = App.context().getResources().getIdentifier(MiniatureResourceName, "drawable", App.context().getPackageName());

		if (resId == 0) {
			MiniatureResourceName = "m" + getBookID() + "_" + getPageNumber();
			resId = App.context().getResources().getIdentifier(MiniatureResourceName, "drawable", App.context().getPackageName());
		}


		Log.d(TAG, "<<'MiniatureResourceName'=" + MiniatureResourceName + ">>");


		/**If the resource for the book cannot be found, used the default image*/
		if (resId == 0) {
			resId = App.context().getResources().getIdentifier("default_section", "drawable", App.context().getPackageName());
		}
		return resId;
	}
	//--------------------------------------------------------


	//--------------------------------------------------------
	int getAudioResource() {
		String AudioResourceName;
		AudioResourceName = SoundID;
		int resId = App.context().getResources().getIdentifier(AudioResourceName, "raw", App.context().getPackageName());

		if (resId == 0) {
			AudioResourceName = "audio_" + getBookID() + "_" + getSectionNumber();
			resId = App.context().getResources().getIdentifier(AudioResourceName, "raw", App.context().getPackageName());
		}


		Log.d(TAG, "<<'AudioResourceName'=" + AudioResourceName + " " + getSectionNumber() + ">>");


		/**If the resource for the section cannot be found, used the default audio*/
		if (resId == 0) {
		    /*If the files cannot be found then use the default sound for the section */
			resId = App.context().getResources().getIdentifier("default_section", "raw", App.context().getPackageName());
		}

		return resId;
	}
	//--------------------------------------------------------


}

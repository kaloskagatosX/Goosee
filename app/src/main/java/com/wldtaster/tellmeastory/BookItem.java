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


public class BookItem  implements Comparable<BookItem>, Serializable {

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

    private static final String TAG = BookItem.class.getSimpleName();

    @Override
    public int compareTo(BookItem CompareBookItem) {
        return CompareBookItem.getReleaseDate().compareTo(this.getReleaseDate());
    }

    /**
     * Create a TAG for the Log data
     */

    //--------------------------------------------------------
    void setBookID(String tmpBookID) {
        BookID = tmpBookID;
    }

    String getBookID() {
        return BookID;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookName_EN(String tmpBookName_EN) {
        BookName_EN = tmpBookName_EN;
    }

    String getBookName_EN() {
        return BookName_EN;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookName_HK(String tmpBookName_HK) {
        BookName_HK = tmpBookName_HK;
    }

    String getBookName_HK() {
        return BookName_HK;
    }
    //--------------------------------------------------------


    //--------------------------------------------------------
    void setNumberPages(String tmpNumberPages) {
        NumberPages = tmpNumberPages;
    }

    String getNumberPages() {
        return NumberPages;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookDescription(String tmpBookDescription) {
        BookDescription = tmpBookDescription;
    }

    String getBookDescription() {
        return BookDescription;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookURL(String tmpBookURL) {
        BookURL = tmpBookURL;
    }

    String getBookURL() {
        return BookURL;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookPhotoID(String tmpBookPhotoID) {
        BookPhotoID = tmpBookPhotoID;
    }

    //--------------------------------------------------------
    int getBookPhotoID() {
        String MiniatureResourceName="";
        if ((BookPhotoID==null) || (BookPhotoID.length()<1)){
            MiniatureResourceName="m"+getBookID();
        }else{
            MiniatureResourceName=BookPhotoID;
        }
        int resId =  App.context().getResources().getIdentifier(MiniatureResourceName,"drawable",App.context().getPackageName());

        /**If the resource for the book cannot be found, used the default image*/
        if (resId==0){
            resId=App.context().getResources().getIdentifier("default_book","drawable",App.context().getPackageName());
        }

        return resId;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setBookLevel(String tmpBookLevel) {
        BookLevel = tmpBookLevel;
    }

    String getBookLevel() {
        return BookLevel;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setReleaseDate(String tmpReleaseDate) {
        ReleaseDate = tmpReleaseDate;
    }

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
    String getReleaseDateAsString() {
        SimpleDateFormat tmpformat = new SimpleDateFormat(Constants.bookReleaseDateFormat, Locale.ENGLISH);
        String tmpString=  tmpformat.format(getReleaseDate());
        return tmpString;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setSoundID(String tmpSoundID) {
        SoundID = tmpSoundID;
    }

    String getSoundID() {
        return SoundID;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setComments(String tmpComments) {
        Comments = tmpComments;
    }

    String getComments() {
        return Comments;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setLastSectionID(String tmpLastSectionID) {
        LastSectionID = tmpLastSectionID;
    }

    String getLastSectionID() {
        return LastSectionID;
    }
    //--------------------------------------------------------


    //--------------------------------------------------------
    void setStatus(String tmpStatus) {
        Status = tmpStatus;
    }

    String getStatus() {
        return Status;
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    void setLastReadBook(Boolean tmpLastReadBook) {
        LastReadBook = tmpLastReadBook;
    }

    Boolean getLastReadBook() {
        return LastReadBook; //Boolean.parseBoolean()
    }
    //--------------------------------------------------------

    //--------------------------------------------------------
    int getAudioResource() {
        String AudioResourceName="";
        if ((SoundID==null) || (SoundID.length()<1)){
            AudioResourceName="audio_"+getBookID();
        }else{
            AudioResourceName=SoundID;
        }


        int resId = App.context().getResources().getIdentifier(AudioResourceName,"raw",App.context().getPackageName());

        /**If the resource for the book cannot be found, used the default audio*/
        if (resId==0){
            /*TODO The line below needs to be deleted as we do not need to play a default audio song for each book*/
            resId=App.context().getResources().getIdentifier("default_book","raw",App.context().getPackageName());
        }

        return resId;
    }
    //--------------------------------------------------------



}

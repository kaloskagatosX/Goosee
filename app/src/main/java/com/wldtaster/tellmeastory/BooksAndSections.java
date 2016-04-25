package com.wldtaster.tellmeastory;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * [Description to be added]
 */
                            //ImageView tmpImage = (ImageView) findViewById(R.id.imageViewTest);
                            //tmpImage.setImageResource(mDb.getBookItemAssociatedToBookID(BookItems,"5").getBookPhotoID());

                            /*
                            MediaPlayer mPlayer = MediaPlayer.create(App.context(), mDb.getBookItemAssociatedToBookID(BookItems,"5").getAudioResource());
                            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mPlayer.start();*/

public class BooksAndSections {

    private ArrayList<SectionItem> SectionItemsAllBooks=null;
    private ArrayList<SectionItem> SectionItemsSelectedBook=null;
    private ArrayList<BookItem> BookItems;
    private LibraryDatabase mDb;
    private Cursor cSection;
    private Cursor cBook;
    private static final String TAG = BooksAndSections.class.getSimpleName();

    ArrayList<SectionItem> getSectionItems(String selectedBookID){
        try {
            mDb = new LibraryDatabase(App.context());
            cSection = mDb.getCursorToTable(LibraryDatabase.TABLES.SECTION);
            if ((cSection==null)){
                        /*This would happen if the database cannot be accessed and returns a 'null' Cursor*/
                         Log.d(TAG,"[Unable to connect to the Stories database (" + Constants.LibraryDatabaseName + ") Please check the structure and name of the database.]");
            }else {

                /**SectionItems contains all the section elements of the database*/
                SectionItemsAllBooks = mDb.getSectionItemObjects(cSection);
                Log.d(TAG,"[There are " + Integer.toString(SectionItemsAllBooks.size()) + " different sections in the database - Book ID =(" + selectedBookID +")]");
                };

            /**Close the reference to the Cursor and to the Database*/
            cSection.close();
            mDb.close();
            Log.d(TAG,"[Successfully closed the connection to the database - Section]");

        } catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

        if (selectedBookID!=null){
            SectionItemsSelectedBook =mDb.getSectionsLinkedToBookID(SectionItemsAllBooks,selectedBookID);
        }else{
            SectionItemsSelectedBook=SectionItemsAllBooks;
    }

        return SectionItemsSelectedBook;
    }

    ArrayList<BookItem> getBookItems(){
        try {
            mDb = new LibraryDatabase(App.context());
            cBook = mDb.getCursorToTable(LibraryDatabase.TABLES.BOOKS);
            if ((cBook==null)){
                        /*This would happen if the database cannot be accessed and returns a 'null' Cursor*/
                Log.d(TAG,"[Unable to connect to the Stories database (" + Constants.LibraryDatabaseName + ") Please check the structure and name of the database.]");
            }else {

                /**BookItems contains all the books elements of the database*/
                BookItems = mDb.getBookItemObjects(cBook);
                Log.d(TAG,"[There are " + Integer.toString(BookItems.size()) + " different books in the database]");
            };

            /**Close the reference to the Cursor and to the Database*/
            cBook.close();
            mDb.close();
            Log.d(TAG,"[Successfully closed the connection to the database - Section]");

        } catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

        return BookItems;
    }

}

package com.wldtaster.tellmeastory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This class defined the LibraryDatabase Class
 *
 * @author WldTasteR.Com
 * @version 0.0.1
 * @since 12/04/2016
 */

public class LibraryDatabase extends SQLiteAssetHelper {
	private static final String TAG = SQLiteAssetHelper.class.getSimpleName();
	/**
	 * Create a TAG for the Log data
	 */
	private static final String DATABASE_NAME = Constants.LibraryDatabaseName;
	/**
	 * Set the name of the database which contains the HK Stories data
	 */
	private static final int DATABASE_VERSION = Constants.LibraryVersion;

	/**
	 * Set the version of the database
	 */

	//Prototype---------------------------------------------------------------------
	public LibraryDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//Log.d(TAG, "Prototype");
	}

	/**
	 * Create a pointer to the data in the Table Section
	 */
	public Cursor getCursorToTable(String TableName) {
		try {
			SQLiteDatabase db = getWritableDatabase(); /**Open the database for reading and writing.*/
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder(); /**Create a query object called 'qb' which will be used to do SQL queries on the database*/

			qb.setTables(TableName); /** Sets the list of table set as parameter (TableName) to query*/

			/**Create a Cursor (pointer) to the selected records in the database and move to the first record*/
			Cursor c = qb.query(db, null, null, null, null, null, null); /*query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder)*/
			c.moveToFirst();

			return c;

		} catch (Exception e) {
			/**Log the exception and return a null object as the connection to the database could not be established*/
			Log.i(TAG, e.getMessage());
			return null;
		}
	}

	public ArrayList<SectionItem> getSectionItemObjects(Cursor c) {

		ArrayList<SectionItem> mSectionItems = new ArrayList<SectionItem>(); /** Creation of an ArrayList called mSectionItems and which is composed of SectionItem objects */

		/**Check that the Cursor does not point to a Null object (if the app could not access the database)*/
		if (c != null) {

			/** Iterate through each record of the database (keep going as long as there is a next record) */
			c.moveToPosition(-1);

			while (c.moveToNext()) {

				/** Create a new SectionItem object for each Section record in the datbase */
				SectionItem sectionItem = new SectionItem();


				/** Populate the SectionItem Object with the data from the database */
				sectionItem.setSectionID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.SectionID)));
				sectionItem.setSectionNumber(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.SectionNumber)));
				sectionItem.setBookID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.BookID)));
				sectionItem.setText_EN(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.Text_EN)));
				sectionItem.setText_HK(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.Text_HK)));
				sectionItem.setPageNumber(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.PageNumber)));
				sectionItem.setPagePhotoID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.PagePhotoID)));
				sectionItem.setSoundID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.SoundID)));
				sectionItem.setComments(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.SectionColumns.Comments)));
				/*-----------------------------------------------------------------------------------------------------*/

				/**Add the SectionItem to the ArrayList mSectionItems - mSectionItems is simply an array which contains all the sections*/
				mSectionItems.add(sectionItem);

			}

			/**Sort the sections according to their Section Number, within a book each section should be sequentially ordered with 1 being the first section of a book*/
			Collections.sort(mSectionItems);
		}

		return mSectionItems;
	}

	/*TODO Replace "BookName_EN" into "BookID" - This would need to be replaced in the database as well*/
	public ArrayList<SectionItem> getSectionsLinkedToBookID(ArrayList<SectionItem> SectionList, String BookID) {

		ArrayList<SectionItem> mSectionItems = new ArrayList<SectionItem>(); /** Creation of an ArrayList called mSectionItems and which is composed of SectionItem objects */

		for (SectionItem tmpSectionItem : SectionList) {
			if (tmpSectionItem.getBookID().equals(BookID)) {
				mSectionItems.add(tmpSectionItem);
			}
		}
		return mSectionItems;
	}

	public ArrayList<BookItem> getBookItemObjects(Cursor c) {

		ArrayList<BookItem> mBookItems = new ArrayList<BookItem>(); /** Creation of an ArrayList called mBookItems and which is composed of BookItem objects */

		/**Check that the Cursor does not point to a Null object (if the app could not access the database)*/
		if (c != null) {

			/** Iterate through each record of the database (keep going as long as there is a next record) */
			c.moveToPosition(-1);

			while (c.moveToNext()) {

				/** Create a new BookItem object for each Book in the datbase */
				BookItem bookItem = new BookItem();


				/** Populate the BookItem Object with the data from the database */
				bookItem.setBookID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.BookID)));
				bookItem.setBookName_EN(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.BookName_EN)));
				bookItem.setBookName_HK(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.BookName_HK)));
				bookItem.setNumberPages(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.NumberPages)));
				bookItem.setBookDescription(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.BookDescription)));
				bookItem.setBookURL(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.BookURL)));
				bookItem.setBookPhotoID(c.getString(c.getColumnIndexOrThrow(BookColumns.BookPhotoID)));
				bookItem.setBookLevel(c.getString(c.getColumnIndexOrThrow(BookColumns.BookLevel)));
				bookItem.setReleaseDate(c.getString(c.getColumnIndexOrThrow(BookColumns.ReleaseDate)));
				bookItem.setSoundID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.SoundID)));
				bookItem.setComments(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.Comments)));
				bookItem.setLastSectionID(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.LastSectionID)));
				bookItem.setStatus(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.Status)));
				bookItem.setLastReadBook(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(LibraryDatabase.BookColumns.LastReadBook))));
				/*-----------------------------------------------------------------------------------------------------*/

				/**Add the BookItem to the ArrayList mBookItems - mBookItems is simply an array which contains all the books*/
				mBookItems.add(bookItem);

			}

			/**Sort the sections according to their Section Number, within a book each section should be sequentially ordered with 1 being the first section of a book*/
			Collections.sort(mBookItems);
		}

		return mBookItems;
	}

	/**
	 * Return, if it exists the reference to the last book read
	 */
	BookItem getLastBookRead(ArrayList<BookItem> mBookItems) {

		for (BookItem tmpBookItem : mBookItems) {
			if (tmpBookItem.getLastReadBook()) {
				return tmpBookItem;
			}
		}

		return null;

	}

	/**
	 * Return, if it exists the reference to the last book read
	 */
	BookItem getBookItemAssociatedToBookID(ArrayList<BookItem> mBookItems, String selectedBookID) {

		for (BookItem tmpBookItem : mBookItems) {
			if (tmpBookItem.getBookID().equals(selectedBookID)) {
				return tmpBookItem;
			}
		}

		return null;

	}

	//--------------------------------------------------------

	/**
	 * Create the interface TABLES which will contain the tables of the database
	 */
	public interface TABLES {
		String BOOKS = "Books";
		String SECTION = "Section";
	}
	//--------------------------------------------------------

	//--------------------------------------------------------

	/**
	 * Define the columns of the Table Section
	 */
	public interface SectionColumns {
		String SectionID = "SectionID";
		String SectionNumber = "SectionNumber";
		String BookID = "BookID";
		String Text_EN = "Text_EN";
		String Text_HK = "Text_HK";
		String PageNumber = "PageNumber";
		String PagePhotoID = "PagePhotoID";
		String SoundID = "SoundID";
		String Comments = "Comments";
	}
	//--------------------------------------------------------


	/**
	 * Define the columns of the Table Book
	 */
	public interface BookColumns {
		String BookID = "BookID";
		String BookName_EN = "BookName_EN";
		String BookName_HK = "BookName_HK";
		String NumberPages = "NumberPages";
		String BookDescription = "BookDescription";
		String BookURL = "BookURL";
		String BookPhotoID = "BookPhotoID";
		String BookLevel = "BookLevel";
		String ReleaseDate = "ReleaseDate";
		String SoundID = "SoundID";
		String Comments = "Comments";
		String LastSectionID = "LastSectionID";
		String Status = "Status";
		String LastReadBook = "LastReadBook";
	}


}
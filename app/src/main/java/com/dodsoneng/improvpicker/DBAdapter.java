package com.dodsoneng.improvpicker;



import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    private static String _tag = Global.TAG + ".DBADAPT";

	public static boolean DETAIL=false;
    // Database Structure
    public static final String ROWID        = "row_id";
    public static final String LANG_ID      = "lang_id";
    public static final String ITEM_ID      = "item_id";
    public static final String ITEM_TYPE    = "item_type";
    public static final String ITEM_CONTENT = "item_content";

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "IMPRODB";
    private static final String TABLE_NAME = "TAB_ITEMS";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table " + TABLE_NAME + " (" +
                ROWID        + " integer primary key autoincrement, " +
                LANG_ID      + " int not null, " +
                ITEM_ID      + " int not null, " +
                ITEM_TYPE    + " int not null, " +
				ITEM_CONTENT + " text not null " +
        " ); ";

    private final Context context;

    DatabaseHelper DBHelper;
    public SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        Global.logcat(_tag, "constructor");
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper
    {
        private static String _tag = DBAdapter._tag + ".DBHELP";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Global.logcat(_tag, "constructor");
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Global.logcat(_tag, "onCreate()");

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Global.logcat(_tag, "onUpgrade()");

        	/*
            Log.w(TAG, "Upgrading database from version " + oldVersion
                  + " to "
                  + newVersion + ", which will destroy all old data");
            //
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            //onCreate(db);
            //
        	String upgradeQuery = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN Level INTEGER DEFAULT 0";
                    if (oldVersion == 1 && newVersion == 2)
                         db.execSQL(upgradeQuery);
            */
        }
       
    }
 
	 /***
	  * This function is used to open the database.
	  * @return this
	  * @throws SQLException
	  */
	 public DBAdapter open() throws SQLException
	 {
         Global.logcat(_tag, "open()");

         db = DBHelper.getWritableDatabase();
	     return this;
	 }
	
	 /***
	  * This function is used to close the database.
	  */
	 public void close()
	 {
         Global.logcat(_tag, "close()");

         DBHelper.close();
	 }
	 
	 /***
	  * This function is used to enter a new entry into the database
	  * without a level.
	  * THIS FUNCTION IS DEPRICATED
	  */
	 public long insertRecord (int item_id, int itemType, int langId, String itemContent)
	 {
	     ContentValues initialValues = new ContentValues();
	     initialValues.put(LANG_ID, langId);
	     initialValues.put(ITEM_ID , item_id);
	     initialValues.put(ITEM_TYPE , itemType);
	     initialValues.put(ITEM_CONTENT , itemContent);
	     return db.insert(TABLE_NAME, null, initialValues);
	 }
	 
	 /***
	  * This function returns a count of the number of entries in the database.
	  * @return int count
	  */
	 public int getNumOfRecords()
	 {
		 	int count;
		    String query = "SELECT COUNT(*) FROM " + TABLE_NAME;

		 Cursor cursor = db.rawQuery(query, null);
		 Global.logcat(_tag, "getNumOfRecords(): ["+query+"]", DETAIL);
		 cursor.moveToFirst();
		 count = cursor.getInt(0);
		 cursor.close();

		 return count;
	 }

	 public int getNumOfRecords(int langId, int itemType)
	 {
		 	int count;
		 	String query = "SELECT COUNT(*) FROM "+TABLE_NAME+" WHERE ("+LANG_ID+"="+langId+" AND "+ITEM_TYPE+"="+itemType+");";

		 Global.logcat(_tag, "getNumOfRecords(): [" + query + "]", DETAIL);
         assert db != null;
	     Cursor cursor = db.rawQuery(query, null);
	     cursor.moveToFirst();
	     count = cursor.getInt(0);
         cursor.close();
         
         return count;
	 }
	 
	 /***
	  * This function returns the wrong answer for a question
	  * based on the input of the rowID and the wrong answer number.
	  */
	 public String getItem (int langId, int itemId, int itemType)
	 {
		 	String retValue;
			String	query = "SELECT "+ITEM_CONTENT+" FROM "+TABLE_NAME+" WHERE "+LANG_ID+"="+langId+" AND "+ITEM_ID+"="+itemId+" AND "+ITEM_TYPE+"="+itemType;

		 Global.logcat(_tag, "getItem(): ["+query+"]", DETAIL);

		 Cursor cursor = db.rawQuery(query, null);

		 cursor.moveToFirst();
		 retValue = cursor.getString(0);
         cursor.close();
         return retValue;
	 
	 }	 
	 
	 /***
	  * This function will return a random number
	  * between 1 and the number of entries in the database. 
	  * @return int rand
	  */
	 public String getRandomItem(int langId, int itemType)
	 {
		 	int	numOfRecords;
		 	int randId = 0;

         numOfRecords = getNumOfRecords(langId, itemType);
		 if (numOfRecords <= 0 )
			 return "----";

		 while (randId == 0) {
             Random random = new Random();
             randId = random.nextInt(numOfRecords);
		 }

         Global.logcat(_tag, "getRandomItem(): numOfRecords="+numOfRecords+" randId="+randId, DETAIL);

		 return getItem (langId, randId, itemType);
	 }
	 
}

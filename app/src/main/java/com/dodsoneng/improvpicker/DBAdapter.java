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
	int id = 0;
	
	// Database Structure
    public static final String ROWID = "row_id";
    public static final String LANG_ID = "lang_id";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_TYPE = "item_type";
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
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
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
	     db = DBHelper.getWritableDatabase();
	     return this;
	 }
	
	 /***
	  * This function is used to close the database.
	  */
	 public void close()
	 {
	     DBHelper.close();
	 }
	 
	 /***
	  * This function is used to enter a new entry into the database
	  * without a level.
	  * THIS FUNCTION IS DEPRICATED
	  */
	 public long insertRecord (int lang_id, int item_id, int item_type, String item_content)
	 {
	     ContentValues initialValues = new ContentValues();
	     initialValues.put(LANG_ID, lang_id);
	     initialValues.put(ITEM_ID , item_id);
	     initialValues.put(ITEM_TYPE , item_type);
	     initialValues.put(ITEM_CONTENT , item_content);
	     return db.insert(TABLE_NAME, null, initialValues);
	 }
	 
	 /***
	  * This function returns a count of the number of entries in the database.
	  * @return int count
	  */
	 public int getNumOfRecords(int lang_id, int item_type)
	 {
		 int count;
	     Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME +
                                             " WHERE " + LANG_ID + "=" + lang_id +
                                             " AND " + ITEM_TYPE + "=" + item_type, null);
	     cursor.moveToFirst();
	     count = cursor.getInt(0);
         cursor.close();
         
         return count;
	 }
	 
	 /***
	  * This function returns the wrong answer for a question
	  * based on the input of the rowID and the wrong answer number.
	  */
	 public String getItem (int lang_id, int item_id, int item_type)
	 {
		 String ret_value;

		 Cursor cursor = db.rawQuery(
                 "SELECT " + ITEM_CONTENT +
                 " FROM "  + TABLE_NAME +
                 " WHERE " + LANG_ID + "=" + lang_id +
                 " AND "   + ITEM_TYPE + "=" + item_type +
                 " AND "   + ITEM_TYPE + "=" + item_type, null);
		 cursor.moveToFirst();
         ret_value = cursor.getString(0);
         cursor.close();
         return ret_value;
	 
	 }	 
	 
	 /***
	  * This function will return a random number
	  * between 1 and the number of entries in the database. 
	  * @return int rand
	  */
	 public int getRandomItem(int lang_id, int item_type)
	 {
		 int rand = 0;
		 id = getNumOfRecords(lang_id, item_type);
		 
		 while (rand == 0) {
		    Random random = new Random();
		    rand = random.nextInt(id);
		 }

		 return rand;
	    	
	 }
	 
}

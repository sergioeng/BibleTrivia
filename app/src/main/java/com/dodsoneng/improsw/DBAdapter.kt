package com.dodsoneng.improsw


import java.util.Random

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBAdapter(private val context: Context) {

    internal var DBHelper: DatabaseHelper
    var db: SQLiteDatabase? = null

    /***
     * This function returns a count of the number of entries in the database.
     * @return int count
     */
    val numOfRecords: Int
        get() {
            val count: Int
            val query = "SELECT COUNT(*) FROM $TABLE_NAME"

            val cursor = db!!.rawQuery(query, null)
            Log.d(TAG, "getNumOfRecords(): [$query]")
            cursor.moveToFirst()
            count = cursor.getInt(0)
            cursor.close()

            return count
        }

    init {
        Log.d(TAG, "constructor")
        DBHelper = DatabaseHelper(context)
    }

    class DatabaseHelper internal constructor(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        init {
            Log.d(TAG, "constructor")
        }

        override fun onCreate(db: SQLiteDatabase) {
            Log.d(TAG, "onCreate()")

            db.execSQL(DATABASE_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.d(TAG, "onUpgrade()")

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

        companion object {
            private val TAG =  "IMPROZE.DBHELP"
        }

    }

    /***
     * This function is used to open the database.
     * @return this
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun open(): DBAdapter {
        Log.d(TAG, "open()")

        db = DBHelper.writableDatabase
        return this
    }

    /***
     * This function is used to close the database.
     */
    fun close() {
        Log.d(TAG, "close()")

        DBHelper.close()
    }

    /***
     * This function is used to enter a new entry into the database
     * without a level.
     * THIS FUNCTION IS DEPRICATED
     */
    fun insertRecord(item_id: Int, itemType: Int, langId: Int, itemContent: String): Long {
        val initialValues = ContentValues()
        initialValues.put(LANG_ID, langId)
        initialValues.put(ITEM_ID, item_id)
        initialValues.put(ITEM_TYPE, itemType)
        initialValues.put(ITEM_CONTENT, itemContent)
        return db!!.insert(TABLE_NAME, null, initialValues)
    }

    fun getNumOfRecords(langId: Int, itemType: Int): Int {
        val count: Int
        val query = "SELECT COUNT(*) FROM $TABLE_NAME WHERE ($LANG_ID=$langId AND $ITEM_TYPE=$itemType);"

        Log.d(TAG, "getNumOfRecords(): [$query]")
        assert(db != null)
        val cursor = db!!.rawQuery(query, null)
        cursor.moveToFirst()
        count = cursor.getInt(0)
        cursor.close()

        return count
    }

    /***
     * This function returns the wrong answer for a question
     * based on the input of the rowID and the wrong answer number.
     */
    fun getItem(langId: Int, itemId: Int, itemType: Int): String {
        val retValue: String
        val query =
            "SELECT $ITEM_CONTENT FROM $TABLE_NAME WHERE $LANG_ID=$langId AND $ITEM_ID=$itemId AND $ITEM_TYPE=$itemType"

        Log.d(TAG, "getItem(): [$query]")

        val cursor = db!!.rawQuery(query, null)

        cursor.moveToFirst()
        retValue = cursor.getString(0)
        cursor.close()
        return retValue

    }

    /***
     * This function will return a random number
     * between 1 and the number of entries in the database.
     * @return int rand
     */
    fun getRandomItem(langId: Int, itemType: Int): String {
        val numOfRecords: Int
        var randId = 0

        numOfRecords = getNumOfRecords(langId, itemType)
        if (numOfRecords <= 0)
            return "----"

        while (randId == 0) {
            val random = Random()
            randId = random.nextInt(numOfRecords)
        }

        Log.d(TAG, "getRandomItem(): numOfRecords=$numOfRecords randId=$randId")

        return getItem(langId, randId, itemType)
    }

    companion object {

        private val TAG = "IMPROZE.DBADAPT"

        // Database Structure
        val ROWID = "row_id"
        val LANG_ID = "lang_id"
        val ITEM_ID = "item_id"
        val ITEM_TYPE = "item_type"
        val ITEM_CONTENT = "item_content"

        private val DATABASE_NAME = "IMPRODB"
        private val TABLE_NAME = "TAB_ITEMS"
        private val DATABASE_VERSION = 1

        private val DATABASE_CREATE = "create table " + TABLE_NAME + " (" +
                ROWID + " integer primary key autoincrement, " +
                LANG_ID + " int not null, " +
                ITEM_ID + " int not null, " +
                ITEM_TYPE + " int not null, " +
                ITEM_CONTENT + " text not null " +
                " ); "
    }

}

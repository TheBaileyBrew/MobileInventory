package com.thebaileybrew.mobileinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.thebaileybrew.mobileinventory.data.MobileContract.*;

/*
   Database helper for Inventory App. Manages the Database creation and version updates
*/
public class InventoryDbHelper extends SQLiteOpenHelper {
    public static final String TAG = InventoryDbHelper.class.getSimpleName();

    /* Name of the Database file */
    private static final String DATABASE_NAME = "inventory.db";

    /* Database version. If schema is changed, the version must be incremented */
    private static final int DATABASE_VERSION = 1;

    /*
       Construct a new instance of @Link InventoryDbHelper
       @param context of the app
    */
    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
       Called when the database is first created
    */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //String containing the SQL statement to create the mobile table in the database
        String SQL_CREATE_MOBILE_TABLE = "CREATE TABLE " + MobileEntry.TABLE_NAME + " ("
                + MobileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MobileEntry.COLUMN_MODEL_NAME + " TEXT NOT NULL, "
                + MobileEntry.COLUMN_MODEL_SERIAL_NUMBER + " TEXT, "
                + MobileEntry.COLUMN_MODEL_USAGE_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + MobileEntry.COLUMN_MODEL_INVENTORY_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + MobileEntry.USER_ADD_RECORD + " TEXT NOT NULL, "
                + MobileEntry.USER_ADD_RECORD_DATE + " TEXT NOT NULL, "
                + MobileEntry.USER_UPDATE_RECORD + " TEXT, "
                + MobileEntry.USER_UPDATE_RECORD_DATE + " TEXT);";
        //Execute the SQL statement
        db.execSQL(SQL_CREATE_MOBILE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

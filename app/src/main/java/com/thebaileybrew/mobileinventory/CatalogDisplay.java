package com.thebaileybrew.mobileinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thebaileybrew.mobileinventory.data.InventoryDbHelper;
import com.thebaileybrew.mobileinventory.data.MobileContract;

import static com.thebaileybrew.mobileinventory.data.MobileContract.*;

public class CatalogDisplay extends AppCompatActivity {
    private static final String TAG = CatalogDisplay.class.getSimpleName();

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_display);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDevice = new Intent(CatalogDisplay.this, EditorDisplay.class);
                startActivity(addDevice);
            }
        });

        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    //Temporary holder for displaying info on screen
    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Define a projection that specifies which colums you will use after this query
        String[] projection = {
                MobileEntry._ID,
                MobileEntry.COLUMN_MODEL_NAME,
                MobileEntry.COLUMN_MODEL_SERIAL_NUMBER,
                MobileEntry.COLUMN_MODEL_USAGE_TYPE,
                MobileEntry.COLUMN_MODEL_INVENTORY_TYPE,
                MobileEntry.USER_ADD_RECORD,
                MobileEntry.USER_ADD_RECORD_DATE,
                MobileEntry.USER_UPDATE_RECORD,
                MobileEntry.USER_UPDATE_RECORD_DATE };

        //Perform a query on the mobile table
        Cursor cursor = db.query(
                MobileEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.database_details_textview);

        try{
            //Create a header in the view
            //Add a loop to add followup devices
            displayView.setText("The Mobile Table contains " + cursor.getCount() + " devices.\n\n");
            displayView.append(
                    MobileEntry._ID + "  -  " +
                    MobileEntry.COLUMN_MODEL_NAME + "  -  " +
                    MobileEntry.COLUMN_MODEL_SERIAL_NUMBER + "  -  " +
                    MobileEntry.COLUMN_MODEL_USAGE_TYPE + "  -  " +
                    MobileEntry.COLUMN_MODEL_INVENTORY_TYPE + "  -  " +
                    MobileEntry.USER_ADD_RECORD + "  -  " +
                    MobileEntry.USER_ADD_RECORD_DATE + "  -  " +
                    MobileEntry.USER_UPDATE_RECORD + "  -  " +
                    MobileEntry.USER_UPDATE_RECORD_DATE + "  -  ");

            int idColumnIndex = cursor.getColumnIndex(MobileEntry._ID);
            int modelColumnIndex = cursor.getColumnIndex(MobileEntry.COLUMN_MODEL_NAME);
            int serialColumnIndex = cursor.getColumnIndex(MobileEntry.COLUMN_MODEL_SERIAL_NUMBER);
            int usageColumnIndex = cursor.getColumnIndex(MobileEntry.COLUMN_MODEL_USAGE_TYPE);
            int inventoryColumnIndex = cursor.getColumnIndex(MobileEntry.COLUMN_MODEL_INVENTORY_TYPE);
            int addUserColumnIndex = cursor.getColumnIndex(MobileEntry.USER_ADD_RECORD);
            int addUserDateColumnIndex = cursor.getColumnIndex(MobileEntry.USER_ADD_RECORD_DATE);
            int updateUserColumnIndex = cursor.getColumnIndex(MobileEntry.USER_UPDATE_RECORD);
            int updateUserDateColumnIndex = cursor.getColumnIndex(MobileEntry.USER_UPDATE_RECORD_DATE);

            //Iterate through all the returned rows of data from the cursor
            while(cursor.moveToNext()) {
                //Use the index to extract the String or Int value
                int currentId = cursor.getInt(idColumnIndex);
                String currentModel = cursor.getString(modelColumnIndex);
                String currentSerial = cursor.getString(serialColumnIndex);
                int currentUsage = cursor.getInt(usageColumnIndex);
                int inventoryUsage = cursor.getInt(inventoryColumnIndex);
                String currentAddUser = cursor.getString(addUserColumnIndex);
                String currentAddUserDate = cursor.getString(addUserDateColumnIndex);
                String currentUpdateUser = cursor.getString(updateUserColumnIndex);
                String currentUpdateUserDate = cursor.getString(updateUserDateColumnIndex);
                //Display the values from each column in the TextView
                displayView.append(
                        ("\n" + currentId + "  -  " +
                        currentModel + "  -  " +
                        currentSerial + "  -  " +
                        currentUsage + "  -  " +
                        inventoryUsage + "  -  " +
                        currentAddUser + "  -  " +
                        currentAddUserDate + "  -  " +
                        currentUpdateUser + "  -  " +
                        currentUpdateUserDate + "  -  "));
            }



        } finally {
            cursor.close();
        }

    }



}

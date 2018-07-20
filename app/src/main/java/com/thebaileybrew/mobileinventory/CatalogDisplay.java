package com.thebaileybrew.mobileinventory;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thebaileybrew.mobileinventory.data.InventoryDbHelper;
import com.thebaileybrew.mobileinventory.data.MobileContract;

import static com.thebaileybrew.mobileinventory.data.MobileContract.*;

public class CatalogDisplay extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CatalogDisplay.class.getSimpleName();

    private InventoryDbHelper mDbHelper;
    Boolean isFABOpen = false;
    FloatingActionButton menuFab;
    FloatingActionButton addInventoryFab;
    FloatingActionButton deviceRequestFab;
    FloatingActionButton returnToStockFab;
    LinearLayout addLayout, requestLayout, returnLayout;
    TextView addText, requestText, returnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_display);
        addLayout = findViewById(R.id.add_layout);
        addText = findViewById(R.id.dev_add_new_text);
        returnLayout = findViewById(R.id.return_layout);
        returnText = findViewById(R.id.dev_return_equip_text);
        requestLayout = findViewById(R.id.request_layout);
        requestText = findViewById(R.id.dev_request_text);

        menuFab = findViewById(R.id.dev_fab_menu); //Default Menu FAB
        addInventoryFab = findViewById(R.id.dev_add_new); //Add inventory to stock
        deviceRequestFab = findViewById(R.id.dev_request); //Request Device
        returnToStockFab = findViewById(R.id.dev_return_equip); //Return equipment to stock

        menuFab.setOnClickListener(this);
        addInventoryFab.setOnClickListener(this);
        deviceRequestFab.setOnClickListener(this);
        returnToStockFab.setOnClickListener(this);


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

    private void showFABMenu() {
        isFABOpen = true;
        addLayout.setVisibility(View.VISIBLE);
        requestLayout.setVisibility(View.VISIBLE);
        returnLayout.setVisibility(View.VISIBLE);

        menuFab.animate().rotationBy(90);
        menuFab.animate().scaleX((float) 1.25);
        menuFab.animate().scaleY((float) 1.25);
        addLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_50));
        requestLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        returnLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_150)).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                addText.setVisibility(View.INVISIBLE);
                returnText.setVisibility(View.INVISIBLE);
                requestText.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                addText.setVisibility(View.VISIBLE);
                returnText.setVisibility(View.VISIBLE);
                requestText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
    private void closeFABMenu() {
        isFABOpen = false;

        menuFab.animate().rotationBy(-270);
        addLayout.animate().translationY(0);
        requestLayout.animate().translationY(0);
        returnLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isFABOpen) {
                    addLayout.setVisibility(View.GONE);
                    requestLayout.setVisibility(View.GONE);
                    returnLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dev_fab_menu:
                if(!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;
            case R.id.dev_add_new:
                Intent addDevice = new Intent(CatalogDisplay.this, EditorDisplay.class);
                startActivity(addDevice);
                break;
            case R.id.dev_return_equip:
                break;
            case R.id.dev_request:
                break;
        }
    }
}

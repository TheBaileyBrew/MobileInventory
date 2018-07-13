package com.thebaileybrew.mobileinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.thebaileybrew.mobileinventory.data.InventoryDbHelper;
import com.thebaileybrew.mobileinventory.data.MobileContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.thebaileybrew.mobileinventory.data.MobileContract.*;

public class EditorDisplay extends AppCompatActivity{
    private static final String TAG = EditorDisplay.class.getSimpleName();

    private EditText mModelName;
    private EditText mModelSerial;
    private Spinner mModelUsageSpinner;
    private Spinner mModelInventorySpinner;

    private String mUserName = "Matthew Bailey";

    private int mModelUsage = MobileEntry.CUSTOMER_TYPE;
    private int mModelInventory = MobileEntry.NEW_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mModelName = findViewById(R.id.model_name_edittext);
        mModelSerial = findViewById(R.id.model_serial_edittext);
        mModelUsageSpinner = findViewById(R.id.model_usage_spinner);
        mModelInventorySpinner = findViewById(R.id.model_inventory_type_spinner);

        setupSpinners();
    }

    private void setupSpinners() {
        //Create the ArrayAdapter for device type spinner. List options are from the string array
        ArrayAdapter modelUsageAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_model_types, android.R.layout.simple_spinner_item);
        //Create the ArrayAdapter for inventory type spinner. List options are from the string array
        ArrayAdapter modelInventoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_inventory_types, android.R.layout.simple_spinner_item);

        //Specify the dropdown style
        modelUsageAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        modelInventoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mModelUsageSpinner.setAdapter(modelUsageAdapter);
        mModelInventorySpinner.setAdapter(modelInventoryAdapter);

        mModelUsageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedItem)) {
                    if (selectedItem.equals(getString(R.string.customer_type))) {
                        mModelUsage = MobileEntry.CUSTOMER_TYPE;
                    } else {
                        mModelUsage = MobileEntry.SALES_TYPE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModelUsage = MobileEntry.CUSTOMER_TYPE;
            }
        });
        mModelInventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedItem)) {
                    if(selectedItem.equals(getString(R.string.new_type))) {
                        mModelInventory = MobileEntry.NEW_TYPE;
                    } else if (selectedItem.equals(getString(R.string.used_type))) {
                        mModelInventory = MobileEntry.USED_TYPE;
                    } else {
                        mModelInventory = MobileEntry.SUPPORT_TYPE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModelInventory = MobileEntry.NEW_TYPE;
            }
        });
    }

    /*
       Get user input from editor and save into database
    */

    private void insertDevice() {
        //Read from the input fields
        //Use trim to eliminate leading or trailing whitespace
        String modelName = mModelName.getText().toString().trim();
        String modelSerial = mModelSerial.getText().toString().trim();
        String addUser = mUserName; //Uneditable TODO: Figure out STATIC SAVE
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        String addUserDate = sdf.format(new Date()) ; //Uneditable TODO: Figure out STATIC SAVE
        String updateUser = "Temporary Name";
        String updateUserDate = "01/01/2001 01:01:01";
        //String updateUser = get current info & update upon saving TODO: Figure out INLINE READABLE
        //String updateUserDate = get current info & update upon saving TODO: Figure out INLINE READABLE

        //Create an instance of the Database Helper
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        //Get the database into writable mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ConstantValues object where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MobileEntry.COLUMN_MODEL_NAME, modelName);
        values.put(MobileEntry.COLUMN_MODEL_SERIAL_NUMBER, modelSerial);
        values.put(MobileEntry.COLUMN_MODEL_USAGE_TYPE, mModelUsage);
        values.put(MobileEntry.COLUMN_MODEL_INVENTORY_TYPE, mModelInventory);
        values.put(MobileEntry.USER_ADD_RECORD, addUser);
        values.put(MobileEntry.USER_ADD_RECORD_DATE, addUserDate);
        values.put(MobileEntry.USER_UPDATE_RECORD, updateUser);
        values.put(MobileEntry.USER_UPDATE_RECORD_DATE, updateUserDate);

        //Insert a new row for the equipment into the database, and return the ID of that row
        long newRowId = db.insert(MobileEntry.TABLE_NAME,null, values);

        //Show toast depending on the success of insertion into database table
        if(newRowId == -1) {
            Toast.makeText(this, "Error saving equipment", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Equipment saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


    //Menu specific options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu for the editor
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertDevice();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

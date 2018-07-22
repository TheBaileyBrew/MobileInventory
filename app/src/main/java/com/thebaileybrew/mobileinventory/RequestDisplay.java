package com.thebaileybrew.mobileinventory;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class RequestDisplay extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = RequestDisplay.class.getSimpleName();

    private EditText mModelSerial;
    private CheckBox mSalesBox;
    private CheckBox mCustomerBox;
    private CheckBox mItopsBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mModelSerial = findViewById(R.id.serial_edittext);
        mSalesBox = findViewById(R.id.check_sales);
        mCustomerBox = findViewById(R.id.check_customer);
        mItopsBox = findViewById(R.id.check_ops);

        mSalesBox.setOnCheckedChangeListener(this);
        mCustomerBox.setOnCheckedChangeListener(this);
        mItopsBox.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //Allow for only ONE checkbox checked at a time
        switch(buttonView.getId()){
            case R.id.check_sales:
                mSalesBox.setChecked(true);
                mCustomerBox.setChecked(false);
                mItopsBox.setChecked(false);
                break;
            case R.id.check_customer:
                mCustomerBox.setChecked(true);
                mSalesBox.setChecked(false);
                mItopsBox.setChecked(false);
                break;
            case R.id.check_ops:
                mItopsBox.setChecked(true);
                mCustomerBox.setChecked(false);
                mSalesBox.setChecked(false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Update this device
                //updateDevice();
                // Exit activity
                finish();
                return true;
            // Respond to a click to load history
            case R.id.action_load_history:
                // Do nothing for now
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
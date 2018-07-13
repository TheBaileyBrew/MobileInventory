package com.thebaileybrew.mobileinventory.data;

import android.provider.BaseColumns;

public final class MobileContract {
    //To prevent accidental instantiation of the contract class create an empty constructor class
    private MobileContract() {}

    /*
    Inner class defines the constant values for the mobile database table
    Each entry represents a single piece of equipment
     */
    public static final class MobileEntry implements BaseColumns {
        /* Name of the Database */
        public final static String TABLE_NAME = "equipment";

        /* Unique ID reference for the equipment (only used in the table itself
           Type: INTEGER
        */
        public final static String _ID = BaseColumns._ID;

        /* Model Name
           Type: TEXT
           Foreign Key - Model Table
        */
        public final static String COLUMN_MODEL_NAME = "model";

        /* Model Serial Number
           Type: TEXT
           NOT NULL
        */
        public final static String COLUMN_MODEL_SERIAL_NUMBER = "serial";

        /* Model Device Type
           Type: INTEGER
           Only possible values are (@Link #CUSTOMER_TYPE) or (@Link #SALES_TYPE)
        */
        public final static String COLUMN_MODEL_USAGE_TYPE = "devicetype";

        /* Model Inventory Source
           Type: INTEGER
           Only possible values are (@Link #NEW_TYPE), (@Link #USED_TYPE), (@Link #SUPPORT_TYPE)
        */
        public final static String COLUMN_MODEL_INVENTORY_TYPE = "inventorytype";

        /* User Add Record
           Type: TEXT
           Foreign Key - User Table
        */
        public final static String USER_ADD_RECORD = "adduser";

        /* User Add Record Date
           Type: TEXT
        */
        public final static String USER_ADD_RECORD_DATE = "adduserdate";

        /* User Update Record
           Type: TEXT
        */
        public final static String USER_UPDATE_RECORD = "updaterecord";

        /* User Update Record Date
           Type: TEXT
        */
        public final static String USER_UPDATE_RECORD_DATE = "updaterecorddate";

        /*
           Possible values for Device Usage Type
        */
        public static final int CUSTOMER_TYPE = 0;
        public static final int SALES_TYPE = 1;

        /*
           Possible values for Device Inventory Type
        */
        public static final int NEW_TYPE = 0;
        public static final int USED_TYPE = 1;
        public static final int SUPPORT_TYPE = 2;
    }
}

package com.abazeer.abazeerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abazeer.abazeerapp.model.ReturnItemModel;
import com.abazeer.abazeerapp.model.ReturnOrderModel;
import com.abazeer.abazeerapp.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AbazeerDB";

    private static final String ID = "ID";
    private static final String PHONE = "PHONE";


    //TABLE NAME
    private static final String USERS = "USERS";
    private static final String APIURL = "APIURL";
    private static final String ORDERS = "ORDERS";
    private static final String RETURN_ORDERS = "RETURN_ORDERS";
    private static final String RETURN_ITEMS = "RETURN_ITEMS";
    private static final String ZONE = "ZONE";


    //COLUMN
    private static final String USER_ID = "USER_ID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String TOKEN = "TOKEN";
    private static final String ISDRIVER = "ISDRIVER";

    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String CUSTOMER_CODE = "CUSTOMER_CODE";
    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String ITEMS_COUNT = "ITEMS_COUNT";


    private static final String ID_DATABASE = "ID_DATABASE";
    private static final String RETURN_ID = "RETURN_ID";
    private static final String PRODUCT_ID = "PRODUCT_ID";
    private static final String PRODUCT_BARCODE = "PRODUCT_BARCODE";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String PRODUCT_UNIT_ID = "PRODUCT_UNIT_ID";
    private static final String PRODUCT_UNIT = "PRODUCT_UNIT";
    private static final String LOT_ID = "LOT_ID";
    private static final String LOT_NAME = "LOT_NAME";
    private static final String VALID = "VALID";
    private static final String DAMAGED = "DAMAGED";
    private static final String EXPIRY = "EXPIRY";
    private static final String TOTAL = "TOTAL";



    private static final String FROM = "_FROM";
    private static final String TO = "_TO";
    private static final String PRICE = "PRICE";
    private static final String TIME = "TIME";
    private static final String RECEIVETIME = "RECEIVETIME";
    private static final String STATUS_ID = "STATUS_ID";
    private static final String STATUS = "STATUS";
    private static final String DISTANCE = "DISTANCE";


    //BUILD
    private static final String USER_TABLE =  "CREATE TABLE " + USERS + "("
            + ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER,"
            + NAME + " TEXT," + PHONE + " TEXT," + TOKEN + " TEXT," + EMAIL + " TEXT ," + ISDRIVER + " INTEGER" +")";

    private static final String ORDER_TABLE =  "CREATE TABLE " + ORDERS + "("
            + ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER, "
            + PHONE + " TEXT," + PRICE + " TEXT," + TIME + " TEXT, " + RECEIVETIME + " TEXT, "
            + STATUS + " INTEGER, "+ STATUS_ID + " INTEGER, " + DISTANCE + " TEXT, " + FROM + " TEXT, "+ TO + " TEXT " +")";

    private static final String APIURL_TABLE =  "CREATE TABLE " + APIURL + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT" +")";

    private static final String RETURN_ORDERS_TABLE =
            "CREATE TABLE " + RETURN_ORDERS + "("
                    + ID + " INTEGER PRIMARY KEY,"
                    + ID_DATABASE + " INTEGER,"
                    + CUSTOMER_NAME + " TEXT,"
                    + ITEMS_COUNT + " INTEGER,"
                    + STATUS + " INTEGER"
                    +")";

    private static final String RETURN_ORDERS_ITEMS_TABLE =
            "CREATE TABLE " + RETURN_ITEMS + "("
                    + ID + " INTEGER PRIMARY KEY,"
                    + ID_DATABASE + " INTEGER,"
                    + RETURN_ID + " INTEGER,"
                    + PRODUCT_ID + " INTEGER,"
                    + PRODUCT_BARCODE + " TEXT,"
                    + PRODUCT_NAME + " TEXT,"
                    + PRODUCT_UNIT_ID + " INTEGER,"
                    + PRODUCT_UNIT + " TEXT,"
                    + LOT_ID + " INTEGER,"
                    + LOT_NAME + " TEXT,"
                    + VALID + " INTEGER,"
                    + DAMAGED + " INTEGER,"
                    + EXPIRY + " INTEGER,"
                    + TOTAL + " INTEGER"
                    +")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE);
        db.execSQL(RETURN_ORDERS_TABLE);
        db.execSQL(RETURN_ORDERS_ITEMS_TABLE);
//        db.execSQL(ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + RETURN_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + RETURN_ITEMS);
//        db.execSQL("DROP TABLE IF EXISTS " + APIURL);
//        db.execSQL("DROP TABLE IF EXISTS " + ORDERS);

        // Create tables again
        onCreate(db);
    }



    public void addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_ID, userModel.getId());
        values.put(NAME, userModel.getNameA());
        values.put(PHONE, userModel.getJob_name());
        values.put(EMAIL, userModel.getEmail());
        values.put(TOKEN, userModel.getAccessToken());
        values.put(ISDRIVER, userModel.getIsdriver());

        // Inserting Row
        db.insert(USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void addReturnOrder(ReturnOrderModel returnOrderModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMER_NAME, returnOrderModel.getPartner_name());
        values.put(ITEMS_COUNT, returnOrderModel.getItem_count());
        values.put(STATUS, returnOrderModel.getStatus());


        // Inserting Row
        db.insert(RETURN_ORDERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void addReturnItems(ReturnItemModel returnItemModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RETURN_ID, returnItemModel.getReturn_id());
        values.put(PRODUCT_ID, returnItemModel.getProduct_id());
        values.put(PRODUCT_BARCODE, returnItemModel.getBarcode());
        values.put(PRODUCT_NAME, returnItemModel.getProduct_name());
        values.put(PRODUCT_UNIT_ID, returnItemModel.getUnit_id());
        values.put(PRODUCT_UNIT, returnItemModel.getUnit_name());
        values.put(LOT_ID, returnItemModel.getLot_id());
        values.put(LOT_NAME, returnItemModel.getLot_name());
        values.put(VALID, returnItemModel.getX_studio_abz_product_valid_driver());
        values.put(DAMAGED, returnItemModel.getX_studio_abz_product_damaged_driver());
        values.put(EXPIRY, returnItemModel.getX_studio_abz_product_exp_driver());
        values.put(TOTAL, returnItemModel.getX_studio_abz_total_qty_driver());


        // Inserting Row
        db.insert(RETURN_ITEMS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
//    public void addoOrder(OrderDataModel orderDataModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        if (checkIfExistes(ORDERS , ID , String.valueOf(orderDataModel.getId()))) {
//
//            values.put(ID, orderDataModel.getId());
//            values.put(FROM, orderDataModel.getFrom());
//            values.put(TO, orderDataModel.getTo());
//            values.put(TIME, orderDataModel.getTime());
//            values.put(RECEIVETIME, orderDataModel.getReceivetime());
//            values.put(PHONE, orderDataModel.getPhone());
//            values.put(PRICE, orderDataModel.getPrice());
//            values.put(STATUS, orderDataModel.getStatus());
//            values.put(STATUS_ID, orderDataModel.getStatus_id());
//
//            // Inserting Row
//            db.insert(ORDERS, null, values);
//        }else {
//            values.put(ID, orderDataModel.getId());
//            values.put(FROM, orderDataModel.getFrom());
//            values.put(TO, orderDataModel.getTo());
//            values.put(TIME, orderDataModel.getTime());
//            values.put(RECEIVETIME, orderDataModel.getReceivetime());
//            values.put(PHONE, orderDataModel.getPhone());
//            values.put(PRICE, orderDataModel.getPrice());
//            values.put(STATUS, orderDataModel.getStatus());
//            values.put(STATUS_ID, orderDataModel.getStatus_id());
//            db.update(ORDERS, values, ID+"= ?", new String[]{String.valueOf(orderDataModel.getId())});
//        }
//        //2nd argument is String containing nullColumnHack
//        db.close(); // Closing database connection
//    }
    public boolean deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ USERS);
        int count = db.delete(USERS,null,null);
        db.close();
        if (count >0)
            return true;


        return false;
    }
    public boolean deleteReturnOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ USERS);
        int count = db.delete(RETURN_ORDERS,ID + "= ?",new String[]{ String.valueOf(id)});
        int countitems = db.delete(RETURN_ITEMS,RETURN_ID + "= ?",new String[]{ String.valueOf(id)});
        db.close();
        if (count >0)
            return true;


        return false;
    }
    public int getContactsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public int getReturn_itemsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + RETURN_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public UserModel getUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USERS, new String[] { NAME,
                        TOKEN, PHONE,USER_ID,EMAIL,ISDRIVER }, ID + "=1",
                null, null, null, null, null);
        Log.e("ErrorV",cursor.toString());
        if (cursor != null)
            cursor.moveToFirst();

        UserModel userModel = new UserModel(cursor.getString(1),Integer.parseInt(cursor.getString(3)),
                cursor.getString(2), cursor.getString(0),cursor.getString(4),cursor.getInt(5));
        // return contact
        return userModel;
    }



    private boolean checkIfExistes(String Table, String column, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String query = "select * from " + Table + " where " + column + " = ?";
        cursor = db.rawQuery(query, new String[]{id});
        Log.e("Courser", "" + cursor.getCount());
        if (cursor.getCount() > 0) {
            return false;
        }
        cursor.close();
        return true;
    }


    public List<ReturnOrderModel> getAllReturnOrders() {
        List<ReturnOrderModel> todos = new ArrayList<ReturnOrderModel>();
        String selectQuery = "SELECT  * FROM " + RETURN_ORDERS;

//        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ReturnOrderModel td = new ReturnOrderModel();
                td.setId(c.getInt((c.getColumnIndex(ID))));
                td.setId_database(c.getInt((c.getColumnIndex(ID_DATABASE))));
                td.setPartner_id(c.getInt((c.getColumnIndex(CUSTOMER_ID))));
                td.setPartner_name(c.getString((c.getColumnIndex(CUSTOMER_NAME))));
                td.setPartner_id(c.getInt((c.getColumnIndex(ITEMS_COUNT))));


                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    public List<ReturnItemModel> getAllReturnItems() {
        List<ReturnItemModel> todos = new ArrayList<ReturnItemModel>();
        String selectQuery = "SELECT  * FROM " + RETURN_ORDERS;

//        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ReturnItemModel td = new ReturnItemModel();
                td.setId(c.getInt((c.getColumnIndex(ID))));
                td.setId_database(c.getInt((c.getColumnIndex(ID_DATABASE))));
                td.setReturn_id(c.getInt((c.getColumnIndex(RETURN_ID))));
                td.setProduct_id(c.getInt((c.getColumnIndex(PRODUCT_ID))));
                td.setProduct_name(c.getString((c.getColumnIndex(PRODUCT_NAME))));
                td.setBarcode(c.getString((c.getColumnIndex(PRODUCT_BARCODE))));
                td.setUnit_id(c.getInt((c.getColumnIndex(PRODUCT_UNIT_ID))));
                td.setUnit_name(c.getString((c.getColumnIndex(PRODUCT_UNIT))));
                td.setLot_id(c.getInt((c.getColumnIndex(LOT_ID))));
                td.setLot_name(c.getString((c.getColumnIndex(LOT_NAME))));
                td.setX_studio_abz_product_valid_driver(c.getInt((c.getColumnIndex(VALID))));
                td.setX_studio_abz_product_damaged_driver(c.getInt((c.getColumnIndex(DAMAGED))));
                td.setX_studio_abz_product_exp_driver(c.getInt((c.getColumnIndex(EXPIRY))));
                td.setX_studio_abz_total_qty_driver(c.getInt((c.getColumnIndex(TOTAL))));



                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }
}


package com.abazeer.abazeerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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


    private static final String USER_ID = "USER_ID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String TOKEN = "TOKEN";



    private static final String FROM = "_FROM";
    private static final String TO = "_TO";
    private static final String PRICE = "PRICE";
    private static final String TIME = "TIME";
    private static final String RECEIVETIME = "RECEIVETIME";
    private static final String STATUS_ID = "STATUS_ID";
    private static final String STATUS = "STATUS";
    private static final String DISTANCE = "DISTANCE";


    private static final String USER_TABLE =  "CREATE TABLE " + USERS + "("
            + ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER,"
            + NAME + " TEXT," + PHONE + " TEXT," + TOKEN + " TEXT," + EMAIL + " TEXT" +")";

    private static final String ORDER_TABLE =  "CREATE TABLE " + ORDERS + "("
            + ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER, "
            + PHONE + " TEXT," + PRICE + " TEXT," + TIME + " TEXT, " + RECEIVETIME + " TEXT, "
            + STATUS + " INTEGER, "+ STATUS_ID + " INTEGER, " + DISTANCE + " TEXT, " + FROM + " TEXT, "+ TO + " TEXT " +")";

    private static final String APIURL_TABLE =  "CREATE TABLE " + APIURL + "("
            + ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT" +")";
    private static final String ZONE = "ZONE";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE);
//        db.execSQL(APIURL_TABLE);
//        db.execSQL(ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
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

        // Inserting Row
        db.insert(USERS, null, values);
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

    public UserModel getUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USERS, new String[] { NAME,
                        TOKEN, PHONE,USER_ID,EMAIL }, ID + "=1",
                null, null, null, null, null);
        Log.e("ErrorV",cursor.toString());
        if (cursor != null)
            cursor.moveToFirst();

        UserModel userModel = new UserModel(cursor.getString(1),Integer.parseInt(cursor.getString(3)),
                cursor.getString(2), cursor.getString(0),cursor.getString(4));
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
}


package com.falconssoft.woodysystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.falconssoft.woodysystem.models.BundleInfo;
import com.falconssoft.woodysystem.models.Orders;
import com.falconssoft.woodysystem.models.Users;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "WoodyDatabase";
    static SQLiteDatabase db;

    //******************************************************************
    private static final String BUNDLE_INFO_TABLE = "INVENTORY_INFO";

    private static final String BUNDLE_INFO_THICKNESS = "THICKNESS";
    private static final String BUNDLE_INFO_LENGTH = "LENGTH";
    private static final String BUNDLE_INFO_WIDTH = "WIDTH";
    private static final String BUNDLE_INFO_GRADE = "GRADE";
    private static final String BUNDLE_INFO_PIECES = "NO_OF_PIECES";
    private static final String BUNDLE_INFO_BUNDLE_NO = "BUNDLE_NO";
    private static final String BUNDLE_INFO_LOCATION = "LOCATION";
    private static final String BUNDLE_INFO_AREA = "AREA";
    private static final String BUNDLE_BARCODE = "BARCODE";

    //******************************************************************
    private static final String USERS_TABLE = "USERS_TABLE";

    private static final String USERS_USERNAME = "USERNAME";
    private static final String USERS_PASSWORD = "PASSWORD";

    //******************************************************************
    private static final String ORDERS_TABLE = "ORDERS_TABLE";

    private static final String ORDERS_THICKNESS = "THICKNESS";
    private static final String ORDERS_LENGTH = "LENGTH";
    private static final String ORDERS_WIDTH = "WIDTH";
    private static final String ORDERS_GRADE = "GRADE";
    private static final String ORDERS_PIECES = "NO_OF_PIECES";
    private static final String ORDERS_BUNDLE_NO = "BUNDLE_NO";
    private static final String ORDERS_LOCATION = "LOCATION";
    private static final String ORDERS_AREA = "AREA";
    private static final String ORDERS_PLACING_NO = "PLACING_NO";
    private static final String ORDERS_ORDER_NO = "ORDER_NO";
    private static final String ORDERS_CONTAINER_NO = "CONTAINER_NO";
    private static final String ORDERS_DATE_OF_LOAD = "DATE_OF_LOAD";
    private static final String ORDERS_DESTINATION = "DESTINATION";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INVENTORY_INFO_TABLE = "CREATE TABLE " + BUNDLE_INFO_TABLE + "("
                + BUNDLE_INFO_THICKNESS + " REAL,"
                + BUNDLE_INFO_LENGTH + " REAL,"
                + BUNDLE_INFO_WIDTH + " REAL,"
                + BUNDLE_INFO_GRADE + " TEXT,"
                + BUNDLE_INFO_PIECES + " INTEGER,"
                + BUNDLE_INFO_BUNDLE_NO + " TEXT,"
                + BUNDLE_INFO_LOCATION + " TEXT,"
                + BUNDLE_INFO_AREA + " TEXT,"
                + BUNDLE_BARCODE + " TEXT" + ")";
        db.execSQL(CREATE_INVENTORY_INFO_TABLE);

        String CREATE_TABLE_USERS = "CREATE TABLE " + USERS_TABLE + "("
                + USERS_USERNAME + " TEXT,"
                + USERS_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USERS);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + ORDERS_TABLE + "("
                + ORDERS_THICKNESS + " REAL,"
                + ORDERS_LENGTH + " REAL,"
                + ORDERS_WIDTH + " REAL,"
                + ORDERS_GRADE + " TEXT,"
                + ORDERS_PIECES + " INTEGER,"
                + ORDERS_BUNDLE_NO + " TEXT,"
                + ORDERS_LOCATION + " TEXT,"
                + ORDERS_AREA + " TEXT,"
                + ORDERS_PLACING_NO + " TEXT,"
                + ORDERS_ORDER_NO + " TEXT,"
                + ORDERS_CONTAINER_NO + " TEXT,"
                + ORDERS_DATE_OF_LOAD + " TEXT,"
                + ORDERS_DESTINATION + " TEXT " + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("ALTER TABLE BUNDLE_INFO_TABLE ADD BUNDLE_BARCODE TAXE NOT NULL DEFAULT ''");
        }catch (Exception e)
        {
            Log.e("upgrade","BUNDLE Barcode");
        }

        try {
            db.execSQL("ALTER TABLE ORDERS_TABLE ADD DESTINATION TAXE NOT NULL DEFAULT ''");
        }catch (Exception e)
        {
            Log.e("upgrade","DESTINATION");
        }

    }

    // **************************************************** Adding ****************************************************
    public void addNewBundle(BundleInfo bundleInfo) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BUNDLE_INFO_THICKNESS, bundleInfo.getThickness());
        contentValues.put(BUNDLE_INFO_LENGTH, bundleInfo.getLength());
        contentValues.put(BUNDLE_INFO_WIDTH, bundleInfo.getWidth());
        contentValues.put(BUNDLE_INFO_GRADE, bundleInfo.getGrade());
        contentValues.put(BUNDLE_INFO_PIECES, bundleInfo.getNoOfPieces());
        contentValues.put(BUNDLE_INFO_BUNDLE_NO, bundleInfo.getBundleNo());
        contentValues.put(BUNDLE_INFO_LOCATION, bundleInfo.getLocation());
        contentValues.put(BUNDLE_INFO_AREA, bundleInfo.getArea());
        contentValues.put(BUNDLE_BARCODE, bundleInfo.getBarcode());

        db.insert(BUNDLE_INFO_TABLE, null, contentValues);
        db.close();
    }

    public void addNewUser(Users users) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERS_USERNAME, users.getUsername());
        contentValues.put(USERS_PASSWORD, users.getPassword());

        db.insert(USERS_TABLE, null, contentValues);
        db.close();
    }

    public void addOrder(Orders orders) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ORDERS_THICKNESS, orders.getThickness());
        contentValues.put(ORDERS_LENGTH, orders.getLength());
        contentValues.put(ORDERS_WIDTH, orders.getWidth());
        contentValues.put(ORDERS_GRADE, orders.getGrade());
        contentValues.put(ORDERS_PIECES, orders.getNoOfPieces());
        contentValues.put(ORDERS_BUNDLE_NO, orders.getBundleNo());
        contentValues.put(ORDERS_LOCATION, orders.getLocation());
        contentValues.put(ORDERS_AREA, orders.getArea());
        contentValues.put(ORDERS_PLACING_NO, orders.getPlacingNo());
        contentValues.put(ORDERS_ORDER_NO, orders.getOrderNo());
        contentValues.put(ORDERS_CONTAINER_NO, orders.getContainerNo());
        contentValues.put(ORDERS_DATE_OF_LOAD, orders.getDateOfLoad());
        contentValues.put(ORDERS_DESTINATION, orders.getDestination());

        db.insert(ORDERS_TABLE, null, contentValues);
        db.close();
    }

    // **************************************************** Getting ****************************************************

    public List<BundleInfo> getBundleInfo() {
        List<BundleInfo> bundleInfoList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BundleInfo bundleInfo = new BundleInfo();

                bundleInfo.setThickness(Double.parseDouble(cursor.getString(0)));
                bundleInfo.setLength(Double.parseDouble(cursor.getString(1)));
                bundleInfo.setWidth(Double.parseDouble(cursor.getString(2)));
                bundleInfo.setGrade(cursor.getString(3));
                bundleInfo.setNoOfPieces(Integer.parseInt(cursor.getString(4)));
                bundleInfo.setBundleNo(cursor.getString(5));
                bundleInfo.setLocation(cursor.getString(6));
                bundleInfo.setArea(cursor.getString(7));
                bundleInfo.setBarcode(cursor.getString(8));
                bundleInfo.setChecked(false);

                bundleInfoList.add(bundleInfo);
            } while (cursor.moveToNext());
        }
        return bundleInfoList;
    }

    public List<String> getBundleNo(){
        List<String> bundleNoList = new ArrayList<>();
        String selectQuery = "SELECT BARCODE FROM " + BUNDLE_INFO_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                bundleNoList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return bundleNoList;
    }

    public List<BundleInfo> getBundleInfoForBundle(String bundel) {
        List<BundleInfo> bundleInfoList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE+" where BUNDLE_NO = '"+bundel+"'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BundleInfo bundleInfo = new BundleInfo();

                bundleInfo.setThickness(Double.parseDouble(cursor.getString(0)));
                bundleInfo.setLength(Double.parseDouble(cursor.getString(1)));
                bundleInfo.setWidth(Double.parseDouble(cursor.getString(2)));
                bundleInfo.setGrade(cursor.getString(3));
                bundleInfo.setNoOfPieces(Integer.parseInt(cursor.getString(4)));
                bundleInfo.setBundleNo(cursor.getString(5));
                bundleInfo.setLocation(cursor.getString(6));
                bundleInfo.setArea(cursor.getString(7));
                bundleInfo.setBarcode(cursor.getString(8));
                bundleInfo.setChecked(false);

                bundleInfoList.add(bundleInfo);
            } while (cursor.moveToNext());
        }
        return bundleInfoList;
    }

    public List<Users> getUsers(){
        List<Users> usersList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + USERS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users user= new Users();

                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));

                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

}

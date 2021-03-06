package com.falconssoft.woodysystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.falconssoft.woodysystem.models.BundleInfo;
import com.falconssoft.woodysystem.models.Orders;
import com.falconssoft.woodysystem.models.Pictures;
import com.falconssoft.woodysystem.models.Settings;
import com.falconssoft.woodysystem.models.Users;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "WoodyDatabase";
    static SQLiteDatabase db;

    //******************************************************************
    private static final String SETTINGS_TABLE = "SETTINGS_TABLE";

    private static final String SETTINGS_COMPANY_NAME = "COMPANY_NAME";
    private static final String SETTINGS_IP_ADDRESS = "IP_ADDRESS";
    private static final String SETTINGS_STORE = "STORE";
    private static final String SETTINGS_USER_NO = "USER_NO";

    //******************************************************************
    private static final String BUNDLE_INFO_TABLE = "BUNDLE_INFO_TABLE";

    private static final String BUNDLE_INFO_THICKNESS = "THICKNESS";
    private static final String BUNDLE_INFO_LENGTH = "LENGTH";
    private static final String BUNDLE_INFO_WIDTH = "WIDTH";
    private static final String BUNDLE_INFO_GRADE = "GRADE";
    private static final String BUNDLE_INFO_PIECES = "NO_OF_PIECES";
    private static final String BUNDLE_INFO_BUNDLE_NO = "BUNDLE_NO";
    private static final String BUNDLE_INFO_LOCATION = "LOCATION";
    private static final String BUNDLE_INFO_AREA = "AREA";
    private static final String BUNDLE_BARCODE = "BARCODE";
    private static final String BUNDLE_INFO_ORDERED = "ORDERED";
    private static final String BUNDLE_INFO_FLAG = "FLAG";
    private static final String BUNDLE_INFO_ADD_DATE = "ADD_DATE";
    private static final String BUNDLE_INFO_PRINTED = "PRINTED";
    private static final String BUNDLE_INFO_DESCRIPTION = "DESCRIPTION";
    private static final String BUNDLE_INFO_SERIAL = "SERIAL";

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

    //******************************************************************
    private static final String PICTURES_TABLE = "PICTURES_TABLE";

    private static final String ORDER_NO = "ORDER_NO";
    private static final String PICTURE_1 = "PICTURE_1";
    private static final String PICTURE_2 = "PICTURE_2";
    private static final String PICTURE_3 = "PICTURE_3";
    private static final String PICTURE_4 = "PICTURE_4";
    private static final String PICTURE_5 = "PICTURE_5";
    private static final String PICTURE_6 = "PICTURE_6";
    private static final String PICTURE_7 = "PICTURE_7";
    private static final String PICTURE_8 = "PICTURE_8";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_SETTINGS = "CREATE TABLE " + SETTINGS_TABLE + "("
                + SETTINGS_COMPANY_NAME + " TEXT,"
                + SETTINGS_IP_ADDRESS + " TEXT,"
                + SETTINGS_STORE + " TEXT,"
                + SETTINGS_USER_NO + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_SETTINGS);

        String CREATE_INVENTORY_INFO_TABLE = "CREATE TABLE " + BUNDLE_INFO_TABLE + "("
                + BUNDLE_INFO_THICKNESS + " REAL,"
                + BUNDLE_INFO_LENGTH + " REAL,"
                + BUNDLE_INFO_WIDTH + " REAL,"
                + BUNDLE_INFO_GRADE + " TEXT,"
                + BUNDLE_INFO_PIECES + " INTEGER,"
                + BUNDLE_INFO_BUNDLE_NO + " TEXT,"
                + BUNDLE_INFO_LOCATION + " TEXT,"
                + BUNDLE_INFO_AREA + " TEXT,"
                + BUNDLE_BARCODE + " TEXT,"
                + BUNDLE_INFO_ORDERED + " INTEGER,"
                + BUNDLE_INFO_FLAG + " TEXT,"
                + BUNDLE_INFO_ADD_DATE + " TEXT,"
                + BUNDLE_INFO_PRINTED + " INTEGER,"
                + BUNDLE_INFO_DESCRIPTION + " TEXT,"
                + BUNDLE_INFO_SERIAL + " TEXT NOT NULL DEFAULT ''"+ ")";
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

        String CREATE_PICTURES_TABLE = "CREATE TABLE " + PICTURES_TABLE + "("
                + ORDER_NO + " TEXT,"
                + PICTURE_1 + " TEXT,"
                + PICTURE_2 + " TEXT,"
                + PICTURE_3 + " TEXT,"
                + PICTURE_4 + " TEXT,"
                + PICTURE_5 + " TEXT,"
                + PICTURE_6 + " TEXT,"
                + PICTURE_7 + " TEXT,"
                + PICTURE_8 + " TEXT " + ")";
        db.execSQL(CREATE_PICTURES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("ALTER TABLE SETTINGS_TABLE ADD " + SETTINGS_USER_NO + " TEXT");
        }catch (Exception e){
            Log.e("upgrade", "USER NO");
        }

        try {
            db.execSQL("ALTER TABLE BUNDLE_INFO_TABLE ADD " + BUNDLE_INFO_SERIAL + " TEXT NOT NULL DEFAULT ''");
        } catch (Exception e) {
            Log.e("upgrade", "SERIAL");
        }

        try {
            db.execSQL("UPDATE BUNDLE_INFO_TABLE SET " + BUNDLE_INFO_SERIAL + " = SUBSTR (BUNDLE_NO,(INSTR (BUNDLE_NO, '.', -1))+1 ,10)");
        } catch (Exception e) {
            Log.e("upgrade", "SERIAL");
        }

        try {
            db.execSQL("ALTER TABLE BUNDLE_INFO_TABLE ADD BARCODE TEXT NOT NULL DEFAULT ''");
        } catch (Exception e) {
            Log.e("upgrade", "BUNDLE Barcode");
        }

        try {
            db.execSQL("ALTER TABLE ORDERS_TABLE ADD DESTINATION TEXT NOT NULL DEFAULT ''");
        } catch (Exception e) {
            Log.e("upgrade", "DESTINATION");
        }

        try {
            String CREATE_TABLE_SETTINGS = "CREATE TABLE " + SETTINGS_TABLE + "("
                    + SETTINGS_COMPANY_NAME + " TEXT,"
                    + SETTINGS_IP_ADDRESS + " TEXT,"
                    + SETTINGS_STORE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SETTINGS);
        } catch (Exception e) {
            Log.e("upgrade", "BUNDLE ORDERED");
        }

        try {
            db.execSQL("ALTER TABLE BUNDLE_INFO_TABLE ADD DESCRIPTION TEXT NOT NULL DEFAULT ''");
        } catch (Exception e) {
            Log.e("upgrade", "BUNDLE Barcode");
        }

    }

    // **************************************************** Adding ****************************************************
//    public void addSettingsUserNo(String userNo){
//        db = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(SETTINGS_USER_NO, userNo);
//        db.insert(SETTINGS_TABLE, null, contentValues);
//        db.close();
//    }

    public void addSettings(Settings settings) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SETTINGS_COMPANY_NAME, settings.getCompanyName());
        contentValues.put(SETTINGS_IP_ADDRESS, settings.getIpAddress());
        contentValues.put(SETTINGS_STORE, settings.getStore());

//        SettingsFile.companyName = settings.getCompanyName();
//        SettingsFile.ipAddress = settings.getIpAddress();
//        SettingsFile.store = settings.getStore();

        db.insert(SETTINGS_TABLE, null, contentValues);
        db.close();
    }

    public void addNewBundle(BundleInfo bundleInfo) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

//        contentValues.put(BUNDLE_INFO_SERIAL, bundleInfo.getSerial());
        contentValues.put(BUNDLE_INFO_THICKNESS, bundleInfo.getThickness());
        contentValues.put(BUNDLE_INFO_LENGTH, bundleInfo.getLength());
        contentValues.put(BUNDLE_INFO_WIDTH, bundleInfo.getWidth());
        contentValues.put(BUNDLE_INFO_GRADE, bundleInfo.getGrade());
        contentValues.put(BUNDLE_INFO_PIECES, bundleInfo.getNoOfPieces());
        contentValues.put(BUNDLE_INFO_BUNDLE_NO, bundleInfo.getBundleNo());
        contentValues.put(BUNDLE_INFO_LOCATION, bundleInfo.getLocation());
        contentValues.put(BUNDLE_INFO_AREA, bundleInfo.getArea());
//        contentValues.put(BUNDLE_BARCODE, bundleInfo.getBarcode());
        contentValues.put(BUNDLE_INFO_ORDERED, 0);//bundleInfo.getOrdered());
        contentValues.put(BUNDLE_INFO_FLAG, "0");
        contentValues.put(BUNDLE_INFO_ADD_DATE, bundleInfo.getAddingDate());
        contentValues.put(BUNDLE_INFO_PRINTED, bundleInfo.getIsPrinted());
        contentValues.put(BUNDLE_INFO_DESCRIPTION, bundleInfo.getDescription());
        contentValues.put(BUNDLE_INFO_SERIAL, bundleInfo.getSerialNo());

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

    public void addPictures(Pictures pictures) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

//        byte[] byteImage1 = {};
//        if (pictures.getPic1() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic1().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage1 = stream.toByteArray();
//        }
//
//        byte[] byteImage2 = {};
//        if (pictures.getPic2() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic2().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage2 = stream.toByteArray();
//        }
//
//        byte[] byteImage3 = {};
//        if (pictures.getPic3() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic3().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage3 = stream.toByteArray();
//        }
//
//        byte[] byteImage4 = {};
//        if (pictures.getPic4() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic4().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage4 = stream.toByteArray();
//        }
//
//        byte[] byteImage5 = {};
//        if (pictures.getPic5() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic5().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage5 = stream.toByteArray();
//        }
//
//        byte[] byteImage6 = {};
//        if (pictures.getPic6() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic6().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage6 = stream.toByteArray();
//        }
//
//        byte[] byteImage7 = {};
//        if (pictures.getPic7() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic7().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage7 = stream.toByteArray();
//        }
//
//        byte[] byteImage8 = {};
//        if (pictures.getPic8() != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            pictures.getPic8().compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byteImage8 = stream.toByteArray();
//        }

        contentValues.put(ORDER_NO, pictures.getOrderNo());
        contentValues.put(PICTURE_1, pictures.getPic1());
        contentValues.put(PICTURE_2, pictures.getPic2());
        contentValues.put(PICTURE_3, pictures.getPic3());
        contentValues.put(PICTURE_4, pictures.getPic4());
        contentValues.put(PICTURE_5, pictures.getPic5());
        contentValues.put(PICTURE_6, pictures.getPic6());
        contentValues.put(PICTURE_7, pictures.getPic7());
        contentValues.put(PICTURE_8, pictures.getPic8());

        db.insert(PICTURES_TABLE, null, contentValues);
        db.close();
    }

    // **************************************************** Getting ****************************************************

    public Settings getSettings() {
        Settings settings = new Settings();
        String selectQuery = "SELECT * FROM " + SETTINGS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                settings.setCompanyName(cursor.getString(0));
                settings.setIpAddress(cursor.getString(1));
                settings.setStore(cursor.getString(2));
                settings.setUserNo(cursor.getString(3));

//                SettingsFile.companyName = cursor.getString(0);
//                SettingsFile.ipAddress = cursor.getString(1);
//                SettingsFile.store = cursor.getString(2);
            } while (cursor.moveToNext());
        }
        return settings;
    }

    public List<BundleInfo> getBundleInfo() {
        List<BundleInfo> bundleInfoList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE + " where ORDERED = '0' and LOCATION = (select STORE from SETTINGS_TABLE)";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BundleInfo bundleInfo = new BundleInfo();

                bundleInfo.setThickness(Double.parseDouble(cursor.getString(0)));
                bundleInfo.setLength(Double.parseDouble(cursor.getString(1)));
                bundleInfo.setWidth(Double.parseDouble(cursor.getString(2)));
                bundleInfo.setGrade(cursor.getString(3));
                bundleInfo.setNoOfPieces(Double.parseDouble(cursor.getString(4)));
                bundleInfo.setBundleNo(cursor.getString(5));
                bundleInfo.setLocation(cursor.getString(6));
                bundleInfo.setArea(cursor.getString(7));
                bundleInfo.setBarcode(cursor.getString(8));
                bundleInfo.setOrdered(Integer.parseInt(cursor.getString(9)));
                bundleInfo.setChecked(false);
                bundleInfo.setDescription(cursor.getString(10));

                bundleInfoList.add(bundleInfo);
            } while (cursor.moveToNext());
        }
        return bundleInfoList;
    }

    public List<BundleInfo> getAllBundleInfo(String flag) {
        List<BundleInfo> bundleInfoList = new ArrayList<>();

//        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE + " where LOCATION = '" + location + "' AND FLAG = '" + flag + "'";

        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE + " where LOCATION = (select STORE from SETTINGS_TABLE)"
                + " AND FLAG = '" + flag + "' ORDER BY cast(SERIAL as unsigned) ASC";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BundleInfo bundleInfo = new BundleInfo();

                bundleInfo.setThickness(Double.parseDouble(cursor.getString(0)));
                bundleInfo.setLength(Double.parseDouble(cursor.getString(1)));
                bundleInfo.setWidth(Double.parseDouble(cursor.getString(2)));
                bundleInfo.setGrade(cursor.getString(3));
                bundleInfo.setNoOfPieces(Double.parseDouble(cursor.getString(4)));
                bundleInfo.setBundleNo(cursor.getString(5));
                bundleInfo.setLocation(cursor.getString(6));
                bundleInfo.setArea(cursor.getString(7));
                bundleInfo.setBarcode(cursor.getString(8));
                bundleInfo.setOrdered(Integer.parseInt(cursor.getString(9)));
                bundleInfo.setHideFlag(cursor.getString(10));
                bundleInfo.setIsPrinted(cursor.getInt(12));
                bundleInfo.setDescription(cursor.getString(13));
                bundleInfo.setSerialNo(cursor.getString(14));

                bundleInfo.setChecked(false);

                bundleInfoList.add(bundleInfo);
            } while (cursor.moveToNext());
        }
        return bundleInfoList;
    }

    public List<String> getBundleNo() {
        List<String> bundleNoList = new ArrayList<>();
        String selectQuery = "SELECT BUNDLE_NO FROM " + BUNDLE_INFO_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                bundleNoList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return bundleNoList;
    }

    public List<BundleInfo> getBundleInfoForBundle(String bundel) {
        List<BundleInfo> bundleInfoList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BUNDLE_INFO_TABLE + " where BUNDLE_NO = '" + bundel + "'";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BundleInfo bundleInfo = new BundleInfo();

                bundleInfo.setThickness(Double.parseDouble(cursor.getString(0)));
                bundleInfo.setLength(Double.parseDouble(cursor.getString(1)));
                bundleInfo.setWidth(Double.parseDouble(cursor.getString(2)));
                bundleInfo.setGrade(cursor.getString(3));
                bundleInfo.setNoOfPieces(Double.parseDouble(cursor.getString(4)));
                bundleInfo.setBundleNo(cursor.getString(5));
                bundleInfo.setLocation(cursor.getString(6));
                bundleInfo.setArea(cursor.getString(7));
                bundleInfo.setBarcode(cursor.getString(8));
                bundleInfo.setOrdered(Integer.parseInt(cursor.getString(10)));
                bundleInfo.setChecked(false);

                bundleInfoList.add(bundleInfo);
            } while (cursor.moveToNext());
        }
        return bundleInfoList;
    }

    public List<Users> getUsers() {
        List<Users> usersList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + USERS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();

                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));

                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    // **************************************************** Getting ****************************************************

    public void updateSettingsUserNo(String userNo) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SETTINGS_USER_NO, userNo);
        db.update(SETTINGS_TABLE, values, null, null);
    }

    public void updateBundlesSerial(String bundleNo, String serial) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUNDLE_INFO_SERIAL, serial);
        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    public void updateTableBundles(String bundleNo) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUNDLE_INFO_ORDERED, 1);
        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    public void updateBundlesFlag(String bundleNo) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUNDLE_INFO_FLAG, "1");
        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    public void updateCheckPrinting(String bundleNo, int printed) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUNDLE_INFO_PRINTED, printed);
        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    public void updateAllPrinting(String bundleNo, int printed) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BUNDLE_INFO_PRINTED, printed);
        db.update(BUNDLE_INFO_TABLE, values, BUNDLE_INFO_BUNDLE_NO + " = '" + bundleNo + "'", null);
    }

    // **************************************************** Delete ****************************************************

    public void deleteSettings() {
        db = this.getWritableDatabase();
        db.delete(SETTINGS_TABLE, null, null);
        db.close();
    }

    public void deleteUsers() {
        db = this.getWritableDatabase();
        db.delete(USERS_TABLE, null, null);
        db.close();
    }

    public void deleteBundle(String bundleNo) {
        db = this.getWritableDatabase();
        db.delete(BUNDLE_INFO_TABLE, " BUNDLE_NO=?", new String[]{bundleNo});
        db.close();
    }

    public void deleteOrder(String orderNo) {
        db = this.getWritableDatabase();
        db.delete(ORDERS_TABLE, " Order_NO=?", new String[]{orderNo});
        db.close();
    }

}

package com.example.futuro.socialemotionmining;

/**
 * Created by futuro on 22-07-2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by futuro on 04-05-2017.
 */
public class DBadapter extends SQLiteOpenHelper{
    public SQLiteDatabase db;

    //static final String DATABASE_NAME = "Db_Addcompany.db";
    static final String DATABASE_NAME = "application.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String TABLE_USER = "tblapp";
    private static final String COLUMN_APP_ID = "app_id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_APP_CAT = "app_cat";
    private static final String COLUMN_APP_DESC = "app_desc";
    private static final String COLUMN_APP_ICON = "app_icon";
    //  static final String DATABASE_CREATE = "create table " + "LOGINN" + "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
    // static final String DATABASE_CREATE = "create table " + "login" + "( " + "ID" + "  INTEGER DEFAULT 100," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
  public static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_APP_NAME + " TEXT,"+ COLUMN_APP_CAT + " TEXT,"+ COLUMN_APP_DESC+ " TEXT,"
          + COLUMN_APP_ICON + " TEXT" + ")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String USER_NAME = "USERNAME";
    private static  final String TABLE_NAME="login";

    String result,vhclereg,phoneno,lastid,id;
    String selectQueryphone="SELECT PHONENO FROM login";
    String selectQueryuser = "SELECT USERNAME FROM login";
    String selectQueryvehicle = "SELECT VEHICLENO FROM login";


    String selectlastid="SELECT LAST_INSERT_ROWID()";

    public DBadapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String uName, String cat,String desc,byte[] icon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_APP_NAME, uName);
        newValues.put(COLUMN_APP_CAT, cat);
        newValues.put(COLUMN_APP_DESC, desc);
        newValues.put(COLUMN_APP_ICON, icon);
        db.insert(TABLE_USER ,null, newValues);
db.close();
    }

    public int deleteEntry(String uName) {

        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("login", where,
                new String[]{uName});
        return numberOFEntriesDeleted;
    }

   /* public boolean checkUser(String uname, String password) {

        // array of columns to fetchjl
        String[] columns = {
                COLUMN_APP_ID_ID
        };
  db = this.getReadableDatabase();
        // selection criteria
       // String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {uname, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }*/

    public void updateEntry(String uName, String pssword) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", uName);
        updatedValues.put("PASSWORD", pssword);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{uName});
    }


    public ArrayList<String> fetchUser(String username) {
ArrayList<String >de=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" +username+"'" ,  null);


        // Cursor c = db.rawQuery(selectQuery,null);
        if (c != null) {
            c.moveToFirst();

            do {
       String appname = c.getString(1);
                String cat=c.getString(2);
                String decs=c.getString(3);
                byte[] blob = c.getBlob(c.getColumnIndex("app_icon"));
                de.add(appname);
                de.add(cat);
                de.add(decs);
            } while (c.moveToNext());
        }

    return de;
}
    public byte[] fetchImage(String username) {
        byte[] blob = new byte[0];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" +username+"'" ,  null);


        // Cursor c = db.rawQuery(selectQuery,null);
        if (c != null) {
            c.moveToFirst();

            do {
                //String appname = c.getColumnName(1);
               // String cat=c.getColumnName(2);
               // String decs=c.getColumnName(3);
                 blob = c.getBlob(c.getColumnIndex("app_icon"));

            } while (c.moveToNext());
        }

        return blob;
    }

    public String fetchUser() {
        Cursor c = db.rawQuery(
                selectQueryuser ,  null);


        if (c != null) {
            c.moveToFirst();

            do {
                result = c.getString(0);
            } while (c.moveToNext());
        }

        return result;
    }
    /* public String fetchID() {
         Cursor c = db.rawQuery(
                selectid ,  null);


         if (c != null) {
             c.moveToFirst();

             do {
                id = c.getString(0);
             } while (c.moveToNext());
         }

         return id;
     }*/
    public String fetchvehicle() {
        Cursor c = db.rawQuery(
                selectQueryvehicle ,  null);


        // Cursor c = db.rawQuery(selectQuery,null);
        if (c != null) {
            c.moveToFirst();

            do {
                vhclereg = c.getString(0);
            } while (c.moveToNext());
        }

        return vhclereg;
    }
    public String fetchphone() {
        Cursor c = db.rawQuery(
                selectQueryphone ,  null);


        // Cursor c = db.rawQuery(selectQuery,null);
        if (c != null) {
            c.moveToFirst();

            do {
                phoneno= c.getString(0);
            } while (c.moveToNext());
        }

        return phoneno;
    }
    public String fetchlastid() {
        Cursor c = db.rawQuery(
                selectlastid ,  null);


        // Cursor c = db.rawQuery(selectQuery,null);
        if (c != null) {
            c.moveToFirst();

            do {
                lastid= c.getString(0);
            } while (c.moveToNext());
        }

        return lastid;
    }
    public List<MyObject> read(String searchTerm) {

        List<MyObject> recordsList = new ArrayList<MyObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_USER;
        sql += " WHERE " + COLUMN_APP_NAME + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + COLUMN_APP_NAME + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(COLUMN_APP_NAME));
                MyObject myObject = new MyObject(objectName);

                // add to list
                recordsList.add(myObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }



}





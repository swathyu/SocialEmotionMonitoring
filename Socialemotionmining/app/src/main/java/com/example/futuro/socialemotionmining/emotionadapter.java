package com.example.futuro.socialemotionmining;

/**
 * Created by futuro on 22-07-2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by futuro on 04-05-2017.
 */
public class emotionadapter extends SQLiteOpenHelper{
    public SQLiteDatabase db;

    //static final String DATABASE_NAME = "Db_Addcompany.db";
    static final String DATABASE_NAME = "application.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String TABLE_USER = "tblemotion";
    private static final String COLUMN_APP_ID = "app_id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_APP_COM = "comment";
    private static final String COLUMN_APP_EMO = "emotion";
    private static final String COLUMN_APP_SCORE = "score";
        private static final String COLUMN_USER_NAME = "uname";
    private static final String COLUMN_DATE = "date";
    //  static final String DATABASE_CREATE = "create table " + "LOGINN" + "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
    // static final String DATABASE_CREATE = "create table " + "login" + "( " + "ID" + "  INTEGER DEFAULT 100," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
  public static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_APP_NAME + " TEXT,"+ COLUMN_APP_COM + " VARCHAR,"+ COLUMN_APP_EMO+ " TEXT,"
          + COLUMN_APP_SCORE + " VARCHAR," + COLUMN_USER_NAME + " VARCHAR,"+COLUMN_DATE + " VARCHAR "+")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String USER_NAME = "USERNAME";
    private static  final String TABLE_NAME="login";

    String result,vhclereg,phoneno,lastid,id;
    String selectQueryphone="SELECT PHONENO FROM login";
    String selectQueryuser = "SELECT USERNAME FROM login";
    String selectQueryvehicle = "SELECT VEHICLENO FROM login";
    //String selectid="SELECT COUNT(SCORE) FROM tblemo WHERE ";

    String selectlastid="SELECT LAST_INSERT_ROWID()";

    public emotionadapter(Context context) {
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

    public void insertEntry( String uName, String com, String emo, String score,String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_USER_TABLE);
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_APP_NAME, uName);
        newValues.put(COLUMN_APP_COM, com);
        newValues.put(COLUMN_APP_EMO, emo);
        newValues.put(COLUMN_APP_SCORE, score);
        newValues.put(COLUMN_USER_NAME,name);
        newValues.put(COLUMN_DATE,date);

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
                String comment=c.getString(2);
                String uname=c.getString(5);
                String date=c.getString(6);
                byte[] blob = c.getBlob(c.getColumnIndex("app_icon"));
                de.add(appname);
                de.add(comment);
                de.add(uname);
                de.add(date);
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
    public String fetchcount(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_USER_TABLE);
        Cursor c = db.rawQuery(
                "SELECT SUM(score) FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" + username + "'", null);
        if (c != null) {
            c.moveToFirst();

            do {
                id = c.getString(0);
            } while (c.moveToNext());
        }
        return id;
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


}





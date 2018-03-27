package com.example.futuro.socialemotionmining;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {
ListView lv;
    CustomAdapter adapter;
    emotionadapter edapter;
    ArrayList<String>data=new ArrayList<>();
   // SQLiteHelper SQLITEHELPER;
   Cursor cursor;
    private static final String TABLE_USER = "tblemotion";
    private static final String COLUMN_APP_ID = "app_id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_APP_COM = "comment";
    private static final String COLUMN_APP_EMO = "emotion";
    private static final String COLUMN_APP_SCORE = "score";
    private static final String COLUMN_USER_NAME = "uname";
    private static final String COLUMN_DATE = "date";
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> comment = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> score = new ArrayList<String>();
    SQLiteDatabase SQLITEDATABASE;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent in=getIntent();
   a = in.getStringExtra("a");
        edapter=new emotionadapter(this);
        lv= (ListView) findViewById(R.id.list);

        adapter=new CustomAdapter(this,getData());
        lv.setAdapter(adapter);




    }
    private void ShowSQLiteDBdata(String username) {

        SQLITEDATABASE = edapter.getWritableDatabase();

        Cursor cursor = SQLITEDATABASE.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" + username + "'", null);

        name.clear();
        comment.clear();
        date.clear();
        // SUBJECT_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {

                name.add(cursor.getString(5));

                comment.add(cursor.getString(2));

                date.add(cursor.getString(6));
                score.add(cursor.getString(4));

                //SUBJECT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Subject)));

            } while (cursor.moveToNext());
        }
      cursor.close();
    }


    private ArrayList getData()
    {
        ShowSQLiteDBdata(a);
        ArrayList<Model> spacecrafts=new ArrayList<>();


        for(int i=0;i<name.size();i++)
        {
            Model s=new Model();
            s.setName(name.get(i));
            s.setComments(comment.get(i));
            s.setImage(R.drawable.user);
            s.setDate(date.get(i));
            s.setScore(score.get(i));
            spacecrafts.add(s);
        }



        return spacecrafts;
    }
}
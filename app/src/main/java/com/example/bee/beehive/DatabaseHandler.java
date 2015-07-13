package com.example.bee.beehive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuno on 15/06/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "beehiveDB";
//    private static final String TABLE_HIVES = "hives";
    private static final String TABLE_APIARIES = "apiaries";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "apiary_name";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_APIARIES_TABLE = "CREATE TABLE " + TABLE_APIARIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_APIARIES_TABLE);
        System.out.println("CREATING DATABASE");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_APIARIES);

        // Creates tables again
        onCreate(db);
    }


    public void addApiary(Apiary apiary)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, apiary.getName());
      //  System.out.println(values.toString());
        db.insert(TABLE_APIARIES, null, values);

        db.close();
    }

    public Apiary getApiary(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor: query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor cursor = db.query(
                TABLE_APIARIES, new String[] {KEY_ID, KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Apiary apiary = new Apiary(Integer.parseInt(cursor.getString(0)), cursor.getString(1));

        return apiary;
    }

    public List<Apiary> getAllApiaries()
    {
        List<Apiary> apiaryList = new ArrayList<Apiary>();

        String selectQuery = "SELECT *FROM " + TABLE_APIARIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Apiary apiary = new Apiary();
                apiary.setID(Integer.parseInt(cursor.getString(0)));
                apiary.setName(cursor.getString(1));
                apiaryList.add(apiary);
            } while (cursor.moveToNext());
        }
        return apiaryList;
    }


}

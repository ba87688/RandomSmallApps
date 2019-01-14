package com.evan.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //create contant for database name and version
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    //id for tables and columns
    //table name
    public static final String TABLE_NOTES = "notes";
    //table columns
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATED = "noteCreated";

    //create a list of columns to return when called
    public static final String[] ALL_COLUMNS =
            {NOTE_ID, NOTE_TEXT, NOTE_CREATED};

    //create SQL table, statement to create table
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NOTES + " (" +
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TEXT +" TEXT, " +
            NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
            ")";


    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //first drop table
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
        //create a new table
        onCreate(db);

    }
}

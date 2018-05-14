package com.example.b.fatwhite_v2_5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by B on 2018-05-07.
 */

public class LocalDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_WORD =
              "create table Local_Word("
            + "id integer primary key autoincrement,"
            + "word text not null,"
            + "soundmark text,"
            + "translation text not null,"
            + "sentence text,"
            + "importance integer);";

    public static final String CREATE_HISTORYWORD =
              "create table History_Word("
            + "id integer primary key autoincrement,"
            + "word_id integer not null,"
            + "word text not null,"
            + "soundmark text,"
            + "translation text not null,"
            + "sentence text,"
            + "importance integer,"
            + "times integer not null);";

    public LocalDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_WORD);
        db.execSQL(CREATE_HISTORYWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ }

}
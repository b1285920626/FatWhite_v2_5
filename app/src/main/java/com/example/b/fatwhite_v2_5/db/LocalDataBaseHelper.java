package com.example.b.fatwhite_v2_5.db;

import android.content.ContentValues;
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

    public static final String CREATE_PRIVATEWORD =
            "create table Local_PrivateWord("
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
            + "times integer not null,"
            + "passdays integer not null);";

    public static final String CREATE_UserInfo =
              "create table User_info("
            + "ID integer primary key autoincrement,"
            + "User_ID text,"
            + "User_name text,"
            + "User_openid text,"
            + "User_age integer,"
            + "User_level integer,"
            + "User_rate integer,"
            + "passdays integer,"
            + "lastday text);";



    public LocalDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_WORD);
        db.execSQL(CREATE_HISTORYWORD);
        db.execSQL(CREATE_UserInfo);
        db.execSQL(CREATE_PRIVATEWORD);
        ContentValues values = new ContentValues();
        values.put("User_ID","");
        values.put("User_name","");
        values.put("User_openid","");
        values.put("User_age","0");
        values.put("User_level","0");
        values.put("User_rate","0");
        values.put("passdays","0");
        values.put("lastday","0");
        db.insert("User_info",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ }

}
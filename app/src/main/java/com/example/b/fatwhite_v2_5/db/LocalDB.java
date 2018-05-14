package com.example.b.fatwhite_v2_5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.b.fatwhite_v2_5.model.HistoryWord;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 2018-05-08.
 */

public class LocalDB {
    public static final String DB_NAME = "Local_db";
    private static final int VERSION = 1;

    private static LocalDB localDB;
    private SQLiteDatabase db;

    public LocalDB(Context context){
        LocalDataBaseHelper dbHelper = new LocalDataBaseHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取加锁的DB实例，synchronized表示加锁
    public synchronized static LocalDB getInstance(Context context){
        if(localDB == null){
            localDB = new LocalDB(context);
        }
        return localDB;
    }

    //存储word实例到数据库
    public void saveWord(Word word){
        if(word != null){
            ContentValues values = new ContentValues();
            values.put("word",word.get_word());
            values.put("soundmark",word.get_soundmark());
            values.put("translation",word.get_translation());
            values.put("sentence",word.get_sentence());
            values.put("importance",word.get_word());
            db.insert("Local_Word",null,values);
        }
    }

    //读取全表？？
    public List<Word> loadWords (){
        List<Word> list = new ArrayList<Word>();
        Cursor cursor = db.query("Local_Word", null, null, null, null, null, null);//SQLite的查询？？
        if(cursor.moveToFirst()){
            do{
                Word word = new Word();
                word.set_id(cursor.getInt(cursor.getColumnIndex("id")));
                word.set_word(cursor.getString(cursor.getColumnIndex("word")));
                word.set_soundmark(cursor.getString(cursor.getColumnIndex("soundmark")));
                word.set_translation(cursor.getString(cursor.getColumnIndex("translation")));
                word.set_sentence(cursor.getString(cursor.getColumnIndex("sentence")));
                word.set_importance(cursor.getInt(cursor.getColumnIndex("importance")));
                list.add(word);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //读取25个未背的词的表
    public List<Word> loadtodayWords (){
        List<Word> list = loadWords();
        List<Word> newlist = new ArrayList<Word>();
        try {
            Cursor cursor = db.query("History_Word", null, null, null, null, null, null);//SQLite的查询？？
            if(cursor.moveToFirst()){
                int theid;
                do{
                    theid = cursor.getInt(cursor.getColumnIndex("word_id"));
                    for(int i = 0;i <= theid; i++ ){
                        if(list.get(i).get_id() == theid) list.remove(i);
                    }
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.e("SQL", "loadtodayWords: ", e);
        }

        newlist.addAll(list.subList(0,25));
        return newlist;
    }
}

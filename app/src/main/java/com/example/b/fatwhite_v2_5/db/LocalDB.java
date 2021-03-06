package com.example.b.fatwhite_v2_5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.b.fatwhite_v2_5.model.HistoryWord;
import com.example.b.fatwhite_v2_5.model.NewWord;
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

    public void savePrivateWord(NewWord word){
        if(word != null){
            ContentValues values = new ContentValues();
            values.put("word",word.get_word());
//            values.put("soundmark",word.get_soundmark());
            values.put("translation",word.get_translation());
//            values.put("sentence",word.get_sentence());
//            values.put("importance",word.get_word());
            db.insert("Local_PrivateWord",null,values);
        }
    }

    public List<NewWord> loadPrivateWord (){

        List<NewWord> list = new ArrayList<NewWord>();
        Cursor cursor = db.query("Local_PrivateWord", null, null, null, null, null, "word");//SQLite的查询？？
        if(cursor.moveToFirst()){
            do{
                NewWord word = new NewWord();
                word.set_id(cursor.getInt(cursor.getColumnIndex("id")));
                word.set_word(cursor.getString(cursor.getColumnIndex("word")));
//                word.set_soundmark(cursor.getString(cursor.getColumnIndex("soundmark")));
                word.set_translation(cursor.getString(cursor.getColumnIndex("translation")));
//                word.set_sentence(cursor.getString(cursor.getColumnIndex("sentence")));
//                word.set_importance(cursor.getInt(cursor.getColumnIndex("importance")));
                list.add(word);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //读取Word全表？？
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
                String t;
                do{
                    t = cursor.getString(cursor.getColumnIndex("word"));
                    for(int i = 0; i < list.size(); i++ ){
                        if(list.get(i).get_word().equals(t)) list.remove(i);
                    }
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.e("SQL", "loadtodayWords: ", e);
        }

        newlist.addAll(list.subList(0,25));
        return newlist;
    }

    public List<HistoryWord> loadhistoryWords (){
        List<HistoryWord> list = new ArrayList<HistoryWord>();
        Cursor cursor = db.query("History_Word", null, null, null, null, null, null);//SQLite的查询？？
        if(cursor.moveToFirst()){
            do{
                HistoryWord word = new HistoryWord();
                word.set_id(cursor.getInt(cursor.getColumnIndex("id")));
                word.set_word_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                word.set_word(cursor.getString(cursor.getColumnIndex("word")));
                word.set_soundmark(cursor.getString(cursor.getColumnIndex("soundmark")));
                word.set_translation(cursor.getString(cursor.getColumnIndex("translation")));
                word.set_sentence(cursor.getString(cursor.getColumnIndex("sentence")));
                word.set_importance(cursor.getInt(cursor.getColumnIndex("importance")));
                word.set_times(cursor.getInt(cursor.getColumnIndex("times")));
                word.set_passdays(cursor.getInt(cursor.getColumnIndex("passdays")));
                list.add(word);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void saveHistoryWord(HistoryWord word){
        if(word != null){
            ContentValues values = new ContentValues();
            values.put("word_id",word.get_word_id());
            values.put("word",word.get_word());
            values.put("soundmark",word.get_soundmark());
            values.put("translation",word.get_translation());
            values.put("sentence",word.get_sentence());
            values.put("importance",word.get_word());
            values.put("times",word.get_times());
            values.put("passdays",word.get_passdays());
            db.insert("History_Word",null,values);
        }
    }

    public int delete(String str) {
        //执行删除操作
        int count = 0;
        try {
            //返回被删除的行数
            count = db.delete("Local_PrivateWord", "word='"+str+"'", null);
        } catch (Exception e) {
            Log.i("db-del-e", e.toString());
        }
        return count;
    }

    public int clearnewword(){
        //清空表
        int count = 0;
        try {
            //返回被删除的行数
            count = db.delete("Local_PrivateWord", null, null);
        } catch (Exception e) {
            Log.i("db-del-e", e.toString());
        }

        return count;
    }

    public int clearword(){
        //清空表
        int count = 0;
        try {
            //返回被删除的行数
            count = db.delete("Local_Word", null, null);
        } catch (Exception e) {
            Log.i("db-del-e", e.toString());
        }

        return count;
    }

    public int clearhisword(){
        //清空表
        int count = 0;
        try {
            //返回被删除的行数
            count = db.delete("History_Word", null, null);
        } catch (Exception e) {
            Log.i("db-del-e", e.toString());
        }

        return count;
    }

    public void updatehistory(HistoryWord word){
        if(word != null){
            ContentValues values = new ContentValues();
            values.put("word_id",word.get_word_id());
            values.put("word",word.get_word());
            values.put("soundmark",word.get_soundmark());
            values.put("translation",word.get_translation());
            values.put("sentence",word.get_sentence());
            values.put("importance",word.get_word());
            values.put("times",word.get_times());
            values.put("passdays",word.get_passdays());
            db.update("History_Word",values,"word = ?",new String[]{word.get_word()});
        }
    }


}

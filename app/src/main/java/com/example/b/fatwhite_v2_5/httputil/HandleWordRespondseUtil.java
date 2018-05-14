package com.example.b.fatwhite_v2_5.httputil;

import android.text.TextUtils;

import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.model.Word;


public class HandleWordRespondseUtil {
    //解析和处理服务器返回的数据

    public synchronized static boolean handlewordResponse(LocalDB localDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allWords = response.split("#");
            if (allWords != null && allWords.length > 0) {
                for (String p : allWords) {
                    String[] array = p.split("\\|");
                    Word word = new Word();
                    word.set_word(array[0]);
                    word.set_soundmark(array[1]);
                    word.set_translation(array[2]);
                    word.set_sentence(array[3]);
                    word.set_importance(Integer.valueOf(array[4]).intValue());
                    // 将解析出来的数据存储到word表
                    localDB.saveWord(word);
                }
                return true;
            }
        }
        return false;
    }
}
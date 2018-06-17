package com.example.b.fatwhite_v2_5.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.model.HistoryWord;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.List;
import java.util.Locale;

public class FuxiActivity extends FragmentActivity {
    TextToSpeech tts;
    private List<HistoryWord> todayList;
    private LocalDB localDB;
    private int flag = 0;//第几个单词
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuxi);

        //tts是安卓的语音合成引擎，具体怎么回事我也不知道，反正这么写就对了
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        //设置音调
        tts.setPitch(0.7f);
        //设置语速
        tts.setSpeechRate(1.4f);

        localDB = LocalDB.getInstance(this);
        todayList = localDB.loadhistoryWords();
        if(todayList.size() > 20){
            todayList.subList(20,todayList.size()).clear();
        }

        editText = (EditText)findViewById(R.id.editText);
        fill_word();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    //显示单词
    public void show_nextword(){
        if(todayList.size() == 0){
            //完成一组啦。。。
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("恭喜：")//设置对话框的标题
                    .setMessage("完成任务啦")//设置对话框的内容
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {//设置对话框的按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            FuxiActivity.this.finish();
                        }
                    }).create();
            dialog.setCancelable(false);//点击外面没有用
            dialog.show();
        }else {
            fill_word();
        }
    }

    //填充单词
    public void fill_word(){
        HistoryWord word = todayList.get(flag);
        tts.speak(word.get_word(),TextToSpeech.QUEUE_ADD,null,null);
    }

    //显示例句
    public void button_show_sentence_f_onClick (View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_notifications_black_24dp)//设置标题的图片
                .setTitle("例句：")//设置对话框的标题
                .setMessage(todayList.get(flag).get_sentence())//设置对话框的内容
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    //清空输入
    public void clear_edittext(View view){
        editText.setText("");
    }

    //单词点击发音
    public void read_current_f_word (View view){
        String string = todayList.get(flag).get_word();
        tts.speak(string,TextToSpeech.QUEUE_ADD,null,null);
    }

    public void buttonok_onclick(View view){
        String str = editText.getText().toString();
        if(str.equals(todayList.get(flag).get_word())){
            editText.setText("");
            todayList.remove(todayList.get(flag));
            show_nextword();
            Toast.makeText(this,"正确",Toast.LENGTH_SHORT).show();
        }else {
            tts.speak(todayList.get(flag).get_word(),TextToSpeech.QUEUE_ADD,null,null);
            Toast.makeText(this,"错啦",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }
}

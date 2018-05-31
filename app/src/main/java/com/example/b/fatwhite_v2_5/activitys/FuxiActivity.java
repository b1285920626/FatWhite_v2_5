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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.fragment.LearnCheckFragment2;
import com.example.b.fatwhite_v2_5.fragment.LearnOptionsFragment;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.List;
import java.util.Locale;

public class FuxiActivity extends FragmentActivity {
    TextToSpeech tts;
    private List<Word> todayList;
    private LocalDB localDB;
    private int flag = 0;//第几个单词
    EditText editText;

    LearnOptionsFragment learnOptionsFragment = new LearnOptionsFragment();
    LearnCheckFragment2 learnCheckFragment = new LearnCheckFragment2();
    Fragment current_fragment =new Fragment();

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
        todayList = localDB.loadtodayWords();

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout_f,learnCheckFragment);
        transaction.commit();

        current_fragment = learnOptionsFragment;
    }

    @Override
    protected void onResume() {
        fill_word();
        super.onResume();
    }

    //显示单词
    public void show_nextword(){
        flag++;
        if(todayList.size() == 5){
            //完成一组啦。。。
        }
        if((todayList.size() - flag) == 6) flag = 0;

        Word word = todayList.get(flag);
        if(word.get_thistimes() == 1||word.get_thistimes() == 3){
            replacefragment(learnOptionsFragment);
            fill_word();
        }else{
            replacefragment(learnCheckFragment);
            fill_word();
            tts.speak(word.get_word(),TextToSpeech.QUEUE_ADD,null,null);
        }
    }

    //填充单词头部
    public void fill_word(){
        Word word = todayList.get(flag);

        TextView current_word = (TextView)findViewById(R.id.current_word_f);

        current_word.setText("再听一次");

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

    //替换碎片
    private void replacefragment(Fragment fragment){
        if(current_fragment != fragment){
            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            transaction.show(fragment);
            transaction.hide(current_fragment);
            current_fragment = fragment;
            transaction.commit();
        }
    }

    //单词点击发音
    public void read_current_f_word (View view){
        String string = todayList.get(flag).get_word();
        tts.speak(string,TextToSpeech.QUEUE_ADD,null,null);
    }

    public void button_onclick(View view){
        editText = (EditText)findViewById(R.id.editText);
        String str = editText.getText().toString();
        if(str.equals(todayList.get(flag).get_word())){
            show_nextword();
            editText.setText("");
            todayList.remove(flag);
            Toast.makeText(this,"正确",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"错啦",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }
}

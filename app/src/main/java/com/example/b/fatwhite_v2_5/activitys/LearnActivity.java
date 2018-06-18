package com.example.b.fatwhite_v2_5.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.fragment.LearnCheckFragment;
import com.example.b.fatwhite_v2_5.fragment.LearnOptionsFragment;
import com.example.b.fatwhite_v2_5.model.HistoryWord;
import com.example.b.fatwhite_v2_5.model.Word;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class LearnActivity extends FragmentActivity {
    TextToSpeech tts;
    private List<Word> todayList;
    private LocalDB localDB;
    private int rightoptions;
    private int flag = 0;//第几个单词

    LearnOptionsFragment learnOptionsFragment = new LearnOptionsFragment();
    LearnCheckFragment learnCheckFragment = new LearnCheckFragment();
    Fragment current_fragment =new Fragment();

    String STORE_NAME = "User_info";
    SharedPreferences user_info;
    SharedPreferences.Editor editor;

    Gson gson = new Gson();

//------------------------------------------初始化-----------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

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
        tts.setSpeechRate(1.3f);

        localDB = LocalDB.getInstance(this);
        todayList = localDB.loadtodayWords();

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout,learnCheckFragment);
        transaction.hide(learnCheckFragment);
        transaction.add(R.id.fragment_layout,learnOptionsFragment);
        transaction.commit();

        current_fragment = learnOptionsFragment;

        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        editor = user_info.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fill_word();
        fill_optionsfragment();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

//------------------------------------------显示部分----------------------------------------------------------------------------

    //显示单词
    public void show_nextword(){
        flag++;
        if(todayList.size() == 5){
            //完成一组啦。。。
            AlertDialog dialog = new AlertDialog.Builder(LearnActivity.this)
                    .setTitle("恭喜：")//设置对话框的标题
                    .setMessage("完成任务啦")//设置对话框的内容
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {//设置对话框的按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                    }).create();
            dialog.setCancelable(false);//点击外面没有用
            dialog.show();

            return;
            }
        if((todayList.size() - flag) == 5) flag = 0;

        Word word = todayList.get(flag);
        if(word.get_thistimes() == 0||word.get_thistimes() == 2){
            replacefragment(learnOptionsFragment);
            fill_word();
            fill_optionsfragment();
        }else{
            replacefragment(learnCheckFragment);
            fill_word();
        }
    }

    //填充单词头部
    public void fill_word(){
        Word word = todayList.get(flag);

        TextView current_word = (TextView)findViewById(R.id.current_word);
        TextView current_soundmark = (TextView)findViewById(R.id.current_soundmark);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        current_word.setText(word.get_word());
        current_soundmark.setText(word.get_soundmark());
        ratingBar.setRating(word.get_thistimes());

        tts.speak(word.get_word(),TextToSpeech.QUEUE_ADD,null,null);
    }

    //填充learnoptionsfragment
    public void fill_optionsfragment(){
        Word word = todayList.get(flag);

        //产生4个随机数不为i
        Random random = new Random();
        int[] a = new int[4];
        int j = 0;
        int k;
        do{
            k = random.nextInt(todayList.size());
            boolean tap = true;
            for(int n = 0; n < j; n++){ if(a[n] == k) tap = false; }
            if(flag != k && tap) {a[j] = k;j++; }
        }while(j < 4);

        //放置错误选项
        learnOptionsFragment.setOptions_A(this,todayList.get(a[0]).get_translation());
        learnOptionsFragment.setOptions_B(this,todayList.get(a[1]).get_translation());
        learnOptionsFragment.setOptions_C(this,todayList.get(a[2]).get_translation());
        learnOptionsFragment.setOptions_D(this,todayList.get(a[3]).get_translation());

        //放置正确选项
        rightoptions = random.nextInt(4);
        switch (rightoptions){
            case 0:learnOptionsFragment.setOptions_A(this,word.get_translation());break;
            case 1:learnOptionsFragment.setOptions_B(this,word.get_translation());break;
            case 2:learnOptionsFragment.setOptions_C(this,word.get_translation());break;
            case 3:learnOptionsFragment.setOptions_D(this,word.get_translation());break;
        }
    }


    //显示例句
    public void button_show_sentence_onClick (View view){
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

//--------------------------------------点击-------------------------------------------------------------------------------------------

    //单词点击发音
    public void read_current_word (View view){
        TextView current = (TextView) findViewById(R.id.current_word);
        String string = current.getText().toString();
        tts.speak(string,TextToSpeech.QUEUE_ADD,null,null);
    }

    //check碎片的点击
    public void click_here_onclick(View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_notifications_black_24dp)//设置标题的图片
                .setTitle("翻译：")//设置对话框的标题
                .setMessage(todayList.get(flag).get_translation())//设置对话框的内容
                .setNeutralButton("认识哦", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todayList.get(flag).set_thistimes(todayList.get(flag).get_thistimes()+1);
                        if(todayList.get(flag).get_thistimes() == 4) {//判断满4移出队列并放入历史表
                            localDB.saveHistoryWord(word2historyword(todayList.get(flag)));
                            //新增
                            todayList.remove(flag);
                            //周日下午新增
                            editor.putInt("user_rate",user_info.getInt("user_rate",0)+1).commit();
                            flag--;
                        }
                        dialog.dismiss();
                        show_nextword();
                    }
                })
                .setPositiveButton("记错了", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todayList.get(flag).set_thistimes(0);
                        show_nextword();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public HistoryWord word2historyword(Word word){
        HistoryWord historyWord = new HistoryWord();

        historyWord.set_word_id(word.get_id());
        historyWord.set_word(word.get_word());
        historyWord.set_soundmark(word.get_soundmark());
        historyWord.set_translation(word.get_translation());
        historyWord.set_sentence(word.get_sentence());
        historyWord.set_importance(word.get_importance());
        historyWord.set_times(1);
        historyWord.set_passdays(1);//记得改下
        return historyWord;
    }

    //options选项那个碎片上几个东西的点击反应
    public void options_A_onclick(View view){
        if(rightoptions == 0) {
            todayList.get(flag).set_thistimes(todayList.get(flag).get_thistimes()+1);
            show_nextword();
        }
        else {
            show_translation();
            todayList.get(flag).set_thistimes(0);
        }
    }
    public void options_B_onclick(View view){
        if(rightoptions == 1) {
            todayList.get(flag).set_thistimes(todayList.get(flag).get_thistimes()+1);
            show_nextword();
        }
        else {
            show_translation();
            todayList.get(flag).set_thistimes(0);
        }
    }
    public void options_C_onclick(View view){
        if(rightoptions == 2) {
            todayList.get(flag).set_thistimes(todayList.get(flag).get_thistimes()+1);
            show_nextword();
        }
        else {
            show_translation();
            todayList.get(flag).set_thistimes(0);
        }
    }
    public void options_D_onclick(View view){
        if(rightoptions == 3) {
            todayList.get(flag).set_thistimes(todayList.get(flag).get_thistimes()+1);
            show_nextword();
        }
        else {
            show_translation();
            todayList.get(flag).set_thistimes(0);
        }
    }
    public void button_dontknow_onclick(View view){
        show_translation();
        todayList.get(flag).set_thistimes(0);
    }
    //弹窗显示翻译
    public void show_translation(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_notifications_black_24dp)//设置标题的图片
                .setTitle("翻译：")//设置对话框的标题
                .setMessage(todayList.get(flag).get_translation())//设置对话框的内容
                .setPositiveButton("下一个", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show_nextword();
                        dialog.dismiss();
                    }
                }).create();
       // dialog.setCancelable(false);//点外面不消失
        dialog.show();
    }


    public void onBackPressed(View view){
        super.onBackPressed();
    }
}

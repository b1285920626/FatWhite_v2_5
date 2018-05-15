package com.example.b.fatwhite_v2_5.activitys;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.fragment.LearnCheckFragment;
import com.example.b.fatwhite_v2_5.fragment.LearnOptionsFragment;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class LearnActivity extends FragmentActivity {
    TextToSpeech tts;
    private List<Word> todayList;
    private LocalDB localDB ;
    private int rightoptions;
    private int flag = 0;//第几个单词

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        Toast.makeText(LearnActivity.this, "按返回键退出", Toast.LENGTH_SHORT).show();

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
        tts.setPitch(0.8f);
        //设置语速
        tts.setSpeechRate(1.5f);

        localDB = LocalDB.getInstance(this);
        todayList = localDB.loadtodayWords();

        replacefragment(new LearnOptionsFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        show_word(flag);
    }

    //显示单词
    private void show_word(int i){
        Word word = todayList.get(i);
        TextView current_word = (TextView)findViewById(R.id.current_word);
        current_word.setText(word.get_word());

        if(word.get_thistimes() == 0||word.get_thistimes() == 2){
            word.set_thistimes(word.get_thistimes()+1);
            LearnOptionsFragment learnOptionsFragment = new LearnOptionsFragment();
            //产生4个随机数不为i
            Random random = new Random();
            int[] a = new int[4];
            int j = 0;
            int k;
            do{
                k = random.nextInt(25);
                boolean tap = true;
                for(int n = 0; n < j; n++){ if(a[n] == k) tap = false; }
                if(i != k && tap) {a[j] = k;j++; }
            }while(j < 4);

            //放置错误选项
           learnOptionsFragment.setOptions_A(this,todayList.get(a[0]).get_translation());
           learnOptionsFragment.setOptions_B(this,todayList.get(a[1]).get_translation());
           learnOptionsFragment.setOptions_C(this,todayList.get(a[2]).get_translation());
           learnOptionsFragment.setOptions_D(this,todayList.get(a[3]).get_translation());

            //放置正确选项
            switch (rightoptions){
                case 0:learnOptionsFragment.setOptions_A(this,word.get_translation());break;
                case 1:learnOptionsFragment.setOptions_B(this,word.get_translation());break;
                case 2:learnOptionsFragment.setOptions_C(this,word.get_translation());break;
                case 3:learnOptionsFragment.setOptions_D(this,word.get_translation());break;
            }
        }else{
            replacefragment(new LearnCheckFragment());
        }

        flag++;
    }


    //显示例句
    public void button_show_sentence_onClick (View view){

    }

    //替换碎片
    private void replacefragment(Fragment fragment){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout,fragment);
        transaction.commit();
    }

    //单词点击发音
    public void read_current_word (View view){
        TextView current = (TextView) findViewById(R.id.current_word);
        String test_string = current.getText().toString();

        tts.speak(test_string,TextToSpeech.QUEUE_ADD,null,null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tts != null) {
            tts.shutdown();
        }
    }
}

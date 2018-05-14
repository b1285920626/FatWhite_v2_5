package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.fragment.LearnOptionsFragment;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.List;
import java.util.Locale;

public class LearnActivity extends Activity {
    TextToSpeech tts;
    private List<Word> todayList;

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

        //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
        //在系统中原生的Fragment是通过getFragmentManager获得的。
        FragmentManager FM = getFragmentManager();
        //2.开启一个事务，通过调用beginTransaction方法开启。
        FragmentTransaction MfragmentTransaction =FM.beginTransaction();
        //把自己创建好的fragment创建一个对象
        LearnOptionsFragment f1 = new LearnOptionsFragment();
        //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
        MfragmentTransaction.add(R.id.fragment_layout,f1);
        //提交事务，调用commit方法提交。
        MfragmentTransaction.commit();
    }


    //显示例句
    public void button_show_sentence_onClick (View view){

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

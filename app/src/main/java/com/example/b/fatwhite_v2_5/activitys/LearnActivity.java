package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;

import java.util.Locale;

public class LearnActivity extends Activity {
    TextToSpeech tts;
    int result;

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
        tts.setPitch(0.7f);
        //设置语速
        tts.setSpeechRate(1.5f);
    }

    public void read_current_word (View view){
        TextView current = (TextView) findViewById(R.id.current_word);
        String test_string = current.getText().toString();

        tts.speak(test_string,TextToSpeech.QUEUE_ADD,null,null);
    }

    public void button_show_sentence_onClick (View view){

    }
//    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            tts.setLanguage(Locale.ENGLISH);
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tts != null) {
            tts.shutdown();
        }
    }
}

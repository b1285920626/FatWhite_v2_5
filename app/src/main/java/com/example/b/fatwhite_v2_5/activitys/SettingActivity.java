package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.httputil.DownloadTask;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    //下载按钮
    public void Button_download_onClick(View view)
    {
        new DownloadTask(this).execute();
    }


}

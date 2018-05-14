package com.example.b.fatwhite_v2_5;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.activitys.LearnActivity;
import com.example.b.fatwhite_v2_5.fragment.HomeFragment;
import com.example.b.fatwhite_v2_5.fragment.SettingFragment;
import com.example.b.fatwhite_v2_5.httputil.DownloadTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replacefragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    
                    return true;
                case R.id.navigation_notifications:
                    replacefragment(new SettingFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //初始化时候就用这个碎片了
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment_layout,new HomeFragment());
        transaction.commit();
    }

    //下载按钮
    public void Button_download_onClick(View view)
    {
        new DownloadTask(this).execute();
    }

    //按钮点击事件//开始学习按钮
    public void Button_BeginLearn_onClick(View view){
        Intent intent = new Intent(MainActivity.this,LearnActivity.class);
        startActivity(intent);
    }

    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_layout,fragment);
        transaction.commit();
    }
}

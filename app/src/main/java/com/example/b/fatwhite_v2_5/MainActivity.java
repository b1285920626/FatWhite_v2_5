package com.example.b.fatwhite_v2_5;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.activitys.ExamActivity;
import com.example.b.fatwhite_v2_5.activitys.LearnActivity;
import com.example.b.fatwhite_v2_5.activitys.NewWordActivity;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.fragment.HomeFragment;
import com.example.b.fatwhite_v2_5.fragment.MoreFragment;
import com.example.b.fatwhite_v2_5.fragment.SettingFragment;
import com.example.b.fatwhite_v2_5.httputil.DownloadTask;
import com.example.b.fatwhite_v2_5.model.Userinfo;

public class MainActivity extends AppCompatActivity {
    Userinfo userinfo;
    int n;

    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();
    MoreFragment moreFragment = new MoreFragment();
    Fragment current_fragment = new Fragment();
    LocalDB localDB;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replacefragment(homeFragment);
                    return true;
                case R.id.navigation_dashboard:
                    replacefragment(moreFragment);
                    return true;
                case R.id.navigation_notifications:
                    replacefragment(settingFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //初始化时候就用这个碎片了
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment_layout,settingFragment);
        transaction.add(R.id.main_fragment_layout,moreFragment);
        transaction.hide(settingFragment);
        transaction.hide(moreFragment);
        transaction.add(R.id.main_fragment_layout,homeFragment);
        transaction.commit();
        current_fragment = homeFragment;

        localDB = LocalDB.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume(){
        super.onResume();
        homeFragment.settextview_3(this,Integer.toString(localDB.loadWords().size() - localDB.loadhistoryWords().size()));
        homeFragment.settextview_1(this,Integer.toString(20));
        homeFragment.settextview_2(this,Integer.toString(20-localDB.load_Userinfo().get_User_rate()));
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

    //按钮开始做题
    public void button_newexam_onclick(View view){
        Intent intent = new Intent(MainActivity.this, ExamActivity.class);
        startActivity(intent);
    }

    //按钮生词本
    public void button_newwordbook_onclick(View view){
        Intent intent = new Intent(MainActivity.this,NewWordActivity.class);
        startActivity(intent);
    }

    private void replacefragment(Fragment fragment){
        if(current_fragment != fragment){
            FragmentTransaction transaction =getFragmentManager().beginTransaction();
            transaction.show(fragment);
            transaction.hide(current_fragment);
            current_fragment = fragment;
            transaction.commit();
        }
    }

    //两次返回键退出

    public void onBackPressed(View view){
        super.onBackPressed();
    }

}

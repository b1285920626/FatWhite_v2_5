package com.example.b.fatwhite_v2_5;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.activitys.ExamActivity;
import com.example.b.fatwhite_v2_5.activitys.FuxiActivity;
import com.example.b.fatwhite_v2_5.activitys.LearnActivity;
import com.example.b.fatwhite_v2_5.activitys.NewWordActivity;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.fragment.HomeFragment;
import com.example.b.fatwhite_v2_5.fragment.MoreFragment;
import com.example.b.fatwhite_v2_5.fragment.SettingFragment;
import com.example.b.fatwhite_v2_5.httputil.HttpGetUtil;

public class MainActivity extends AppCompatActivity {
    private String type;
    private Spinner spinner;

    String STORE_NAME = "User_info";
    SharedPreferences user_info;
    SharedPreferences.Editor editor;

    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();
    MoreFragment moreFragment = new MoreFragment();
    Fragment current_fragment = new Fragment();
    LocalDB localDB;
    Handler handler = new Handler();

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

        //初始化时候就用这几个碎片了
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
        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        editor = user_info.edit();

        handler=new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case 1:
                        Toast.makeText(MainActivity.this, "下载成功 ", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(MainActivity.this, "下载失败 "+msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();

        spinner = (Spinner)findViewById(R.id.spinner_type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String)spinner.getSelectedItem();
                editor.putString("type",type);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume(){
        homeFragment.settextview_3(this,Integer.toString(localDB.loadWords().size() - localDB.loadhistoryWords().size()));
        homeFragment.settextview_1(this,Integer.toString(20));
 //改成用那个的。。。
//        homeFragment.settextview_2(this,Integer.toString(20-localDB.load_Userinfo().get_User_rate()));
        super.onResume();
    }

    //下载按钮
    public void Button_download_onClick(View view)
    {
        ProgressDialog pd = new ProgressDialog(MainActivity.this); // 显示进度对话框
        pd.setMessage("下载中");
        pd.show();

        String tablename = "word_"+user_info.getString("type","cet4");
        HttpGetUtil httpGetUtil = new HttpGetUtil();
        httpGetUtil.initargs(handler,MainActivity.this,tablename,localDB);
        httpGetUtil.sendHttpGETRequest();

        pd.dismiss();
    }

    //按钮点击事件//开始学习按钮
    public void Button_BeginLearn_onClick(View view){
        Intent intent = new Intent(MainActivity.this,LearnActivity.class);
        startActivity(intent);
    }

    //按钮点击事件//开始复习按钮
    public void Button_Beginfuxi_onClick(View view){
        Intent intent = new Intent(MainActivity.this,FuxiActivity.class);
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

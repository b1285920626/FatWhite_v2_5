package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.MainActivity;
import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.httputil.HttpPostUtil;

public class LoginActivity extends Activity {
    EditText editText_userid;
    EditText editText_userpw;
    Context context;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_userid = (EditText)findViewById(R.id.editText_userid);
        editText_userpw = (EditText)findViewById(R.id.editText_userpw);

        context = LoginActivity.this;
        handler=new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case 1:
                        if(msg.obj.toString().equals("success")){
                            Toast.makeText(context, "登录成功 ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else if(msg.obj.toString().equals("fail")){
                            Toast.makeText(context, "账号或密码错误 ", Toast.LENGTH_SHORT).show();
                            break;
                        }else {
                            Toast.makeText(context, "出状况啦", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 0:
                        Toast.makeText(context, "网络错误 ", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    }

    public void buttonn_login_onclick(View view) {
        ProgressDialog pd = new ProgressDialog(context); // 显示进度对话框
        pd.setMessage("登录中...");
        pd.show();
        HttpPostUtil httpPostUtil = new HttpPostUtil();
        httpPostUtil.initargs(handler,context);
        String id = editText_userid.getText().toString();
        String pw = editText_userpw.getText().toString();
        if( !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pw)) {
            pd.dismiss();
            httpPostUtil.sendHttpPOSTRequest(editText_userid.getText().toString(), editText_userpw.getText().toString());
        }else {
            pd.dismiss();
            Toast.makeText(this,"有空的啊...",Toast.LENGTH_SHORT).show();
        }
    }

    //获取SharedPreferences，如果需要进行保存等修改操作，
    // 首先得通过其edit()方法获得SharedPreferences.Editor
    //然后就可以通过putInt、putString等方法以键值对(key-value)的方式保存数据，
    // 或者remove移除某个键(key)，及调用clear方法删除所有内容。
    // 最后需要调用commit方法是使修改生效。
    // 读取可以通过getInt、getString等方法获取对应键(key)保存着的数据，
    // 如果没有找到key，则返回第二个参数作为默认值。
//    String STORE_NAME = "User_info";
//    SharedPreferences user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
//    SharedPreferences.Editor editor = user_info.edit();
}

package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.MainActivity;
import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.util.HttpPostUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends Activity {
    private String openidString;
    private String type;
    private String nickname;
    private String[] typearray;
    private Tencent mTencent;

    Spinner spinner;
    Context context;
    AlertDialog dialog_setting;
    String STORE_NAME = "User_info";
    SharedPreferences user_info;
    SharedPreferences.Editor editor;
    //笔记
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
    //笔记end

    EditText editText_userid;
    EditText editText_userpw;

//---------------------------------------初始化--------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTencent = Tencent.createInstance("1106884204",getApplicationContext());//获取QQ登录实例
        context = LoginActivity.this;

        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        editor = user_info.edit();

        editText_userid = (EditText)findViewById(R.id.editText_userid);
        editText_userpw = (EditText)findViewById(R.id.editText_userpw);
    }


//-------------------------------------QQ登录-----------------------------------------------------------------------------------------------
    //QQ登录按钮
    public void buttun_qq_login_onclick(View view){
        mTencent.login(LoginActivity.this,"all",new BaseUiListener());
    }

    //腾讯SDK示例中的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }
    }

    private class BaseUiListener implements IUiListener {
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                Log.v("TAG", "---"+response.toString());
                openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);
                editor.putString("User_openid",openidString);
                editor.commit();

                mTencent.setAccessToken(((JSONObject) response).getString("access_token"),((JSONObject) response).getString("expires_in"));
                Log.v("TAG", "---"+openidString);
               } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    //用户信息获取到了
                    try {
                        nickname = ((JSONObject) o).getString("nickname");
                        Toast.makeText(getApplicationContext(),"欢迎"+nickname, Toast.LENGTH_SHORT).show();
                        editor.putString("User_name",nickname);
                        editor.commit();
                        Log.v("UserInfo",o.toString());
//------------------------------------------发送openid获得type，就下面这一行，处理在handler里--------------------------------------------------------------------------------------------------
                        HttpPostUtil.send_data(1,"openid="+openidString,"LoginServer",context,handler);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Log.v("UserInfo","onError");
                }

                @Override
                public void onCancel() {
                    Log.v("UserInfo","onCancel");
                }
            });

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "拉取验证信息错误", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "用户取消登录", Toast.LENGTH_SHORT).show();
        }
    }

//---------------------------------------弹框选类型，处理返回的数据-----------------------------------------------------------------------------------------

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if(message.arg1 == 0){
                Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            else{
                typearray = message.obj.toString().split("\\|");
                if(typearray.length == 1) {
                    editor.putString("type",typearray[0]);
                    editor.commit();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View myview = View.inflate(context,R.layout.dialog_setting,null);

                    builder.setView(myview);
                    dialog_setting = builder.create();
                    dialog_setting.setTitle("请选择一个种类...");
                    dialog_setting.setCancelable(false);

                    spinner = (Spinner)myview.findViewById(R.id.spinner_settype);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,typearray);
                    spinner.setAdapter(adapter);

                    dialog_setting.show();
                }
            }
        }
    };

//----------------------------------弹框里的确定按钮---------------------------------------------------------------------------------------------------------------------

    public void button_settingok_onclick(View view){
        //上传userinfo
        type = spinner.getSelectedItem().toString();
        try {
            String string = "user_openid=" + URLEncoder.encode(openidString, "UTF-8")
                          + "&user_name=" + URLEncoder.encode(nickname, "UTF-8")
                          + "&user_type=" + URLEncoder.encode(type, "UTF-8");

            HttpPostUtil.send_data(0,string,"Set_User_info",context,handler);
        }catch (Exception e){
            Log.e("上传..",e.toString());
        }

        editor.putString("type",type);
        editor.commit();
        dialog_setting.dismiss();

        Intent intent = new Intent(context, MainActivity.class);//加个单词量测试界面
        startActivity(intent);
        LoginActivity.this.finish();
    }

    //原登录按钮
    public void buttonn_login_onclick(View view) {
        //测试后删除↓
//        Intent intent = new Intent(context, MainActivity.class);
//        startActivity(intent);
//        LoginActivity.this.finish();
        //测试后删除↑

        ProgressDialog pd = new ProgressDialog(context); // 显示进度对话框
        pd.setMessage("登录中...");
        pd.show();

        String id = editText_userid.getText().toString();
        String pw = editText_userpw.getText().toString();
        String data = "user_id=" + id + "&password=" + pw;
        String ad = "LoginServer2";
        if( !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pw)) {
//            sendHttpPOSTRequest(editText_userid.getText().toString(), editText_userpw.getText().toString());
            openidString = id;
            HttpPostUtil.send_data(2,data,ad,context,handler1);
            pd.dismiss();
        }else {
            Toast.makeText(this,"有空的啊...",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    public void button_signup_onclick(View view){
        Intent intent = new Intent(context, SignupActivity.class);
        startActivity(intent);
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
            if(message.arg1 == 2){
                if(!message.obj.toString().equals("failed")){
                    editor.putString("User_openid",openidString);
                    editor.putString("type",message.obj.toString());
                    editor.commit();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };
//
//    private Handler handler=new Handler(){
//        public void handleMessage(Message msg) {
//            switch (msg.arg1){
//                case 1:
//                    if(msg.obj.toString().equals("success")){
//                        Toast.makeText(context, "登录成功 ", Toast.LENGTH_SHORT).show();
//                        break;
//                    }else if(msg.obj.toString().equals("fail")){
//                        Toast.makeText(context, "账号或密码错误 ", Toast.LENGTH_SHORT).show();
//                        break;
//                    }else {
//                        Toast.makeText(context, "出状况啦", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case 0:
//                    Toast.makeText(context, "网络错误 ", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
//
//    public void sendHttpPOSTRequest (final String user_ID,final String user_pw)
//    {
//        String IP = getString(R.string.IP_1);
//        final String address = "http://"+IP+":8080/FatWhite_Server/LoginServer";
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("登录中，请稍候... ...");
//        progressDialog.show();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                String msg = "";
//                try{
//                    URL url = new URL(address);
//                    connection = (HttpURLConnection)url.openConnection();
//                    connection.setRequestMethod("POST");
//
//                    connection.setReadTimeout(3000);
//                    connection.setConnectTimeout(3000);
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
//
//                    connection.setUseCaches(false);
//
//                    String senddata = "user_ID="+ URLEncoder.encode(user_ID,"UTF-8")
//                            + "&user_pw="+URLEncoder.encode(user_pw,"UTF-8");
//
//                    OutputStream outputStream = connection.getOutputStream();
//                    outputStream.write(senddata.getBytes());
//                    outputStream.flush();
//                    outputStream.close();
//                    connection.connect();
//                    //以上为发送
//
//                    if (connection.getResponseCode() == 200) {
//                        // 获取响应的输入流对象
//                        InputStream is = connection.getInputStream();
//                        // 创建字节输出流对象
//                        ByteArrayOutputStream message = new ByteArrayOutputStream();
//
//                        // 定义缓冲区
//                        int len = 0;
//                        byte buffer[] = new byte[1024];
//                        // 按照缓冲区的大小，循环读取
//                        while ((len = is.read(buffer)) != -1) {
//                            // 根据读取的长度写入到os对象中
//                            message.write(buffer, 0, len);
//                        }
//                        // 释放资源
//                        is.close();
//                        message.close();
//                        // 返回字符串
//                        msg = new String(message.toByteArray());
//                    }
//                    message.arg1 = 1;
//                    message.obj = msg;
//                    handler.sendMessage(message);
//                    Intent intent = new Intent(context, MainActivity.class);
//                    startActivity(intent);
//                }catch (Exception e)
//                {
//                    message.arg1 = 0;
//                    message.obj = e;
//                    handler.sendMessage(message);
//                } finally {
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//        progressDialog.dismiss();
//    }

}

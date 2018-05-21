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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends Activity {
    private Message message = new Message();
    EditText editText_userid;
    EditText editText_userpw;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_userid = (EditText)findViewById(R.id.editText_userid);
        editText_userpw = (EditText)findViewById(R.id.editText_userpw);

        context = LoginActivity.this;
    }

   private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    if(msg.obj.toString().equals("success")){
                        Toast.makeText(context, "登录成功 ", Toast.LENGTH_SHORT).show();
                        break;
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

    public void buttonn_login_onclick(View view) {
        ProgressDialog pd = new ProgressDialog(context); // 显示进度对话框
        pd.setMessage("登录中...");
        pd.show();

        String id = editText_userid.getText().toString();
        String pw = editText_userpw.getText().toString();
        if( !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pw)) {
            sendHttpPOSTRequest(editText_userid.getText().toString(), editText_userpw.getText().toString());
            pd.dismiss();
        }else {
            Toast.makeText(this,"有空的啊...",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    public void sendHttpPOSTRequest (final String user_ID,final String user_pw)
    {
        String IP = getString(R.string.IP_1);
        final String address = "http://"+IP+":8080/FatWhite_Server/LoginServer";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("登录中，请稍候... ...");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                String msg = "";
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");

                    connection.setReadTimeout(3000);
                    connection.setConnectTimeout(3000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setUseCaches(false);

                    String senddata = "user_ID="+ URLEncoder.encode(user_ID,"UTF-8")
                            + "&user_pw="+URLEncoder.encode(user_pw,"UTF-8");

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(senddata.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    connection.connect();
                    //以上为发送

                    if (connection.getResponseCode() == 200) {
                        // 获取响应的输入流对象
                        InputStream is = connection.getInputStream();
                        // 创建字节输出流对象
                        ByteArrayOutputStream message = new ByteArrayOutputStream();

                        // 定义缓冲区
                        int len = 0;
                        byte buffer[] = new byte[1024];
                        // 按照缓冲区的大小，循环读取
                        while ((len = is.read(buffer)) != -1) {
                            // 根据读取的长度写入到os对象中
                            message.write(buffer, 0, len);
                        }
                        // 释放资源
                        is.close();
                        message.close();
                        // 返回字符串
                        msg = new String(message.toByteArray());
                    }
                    message.arg1 = 1;
                    message.obj = msg;
                    handler.sendMessage(message);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    message.arg1 = 0;
                    message.obj = e;
                    handler.sendMessage(message);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
        progressDialog.dismiss();
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

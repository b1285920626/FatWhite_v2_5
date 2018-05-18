package com.example.b.fatwhite_v2_5.httputil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.b.fatwhite_v2_5.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPostUtil {
    private Handler handler;
    private Context context;
    private Message message = new Message();

    String address;

//    @Override
    public void initargs(Handler uiHandle,Context context)
    {
        this.context = context;
        handler = uiHandle;
        String IP = context.getString(R.string.IP_1);
        address = "http://"+IP+":8080/FatWhite_Server/LoginServer";
    }

    public void sendHttpPOSTRequest (final String user_ID,final String user_pw)
    {
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
}

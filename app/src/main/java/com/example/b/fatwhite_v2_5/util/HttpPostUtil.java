package com.example.b.fatwhite_v2_5.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.b.fatwhite_v2_5.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostUtil {

//----------------------------------获取连接-----------------------------------------------------------------------------------------------------

    private static HttpURLConnection getconnection (Context context,String ad){
        String IP = context.getString(R.string.IP_1);
        String address = "http://"+IP+":8080/FatWhite_Server/" + ad;
        HttpURLConnection connection;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            return connection;
        }catch (Exception e){
            Log.e("URL",e.toString());
            return null;
        }
    }

//----------------------------------发送数据--------------------------------------------------------------------------------------------

    public static void send_data (final int flag/*用来表示返回的是不是状态，状态标0，数据标1
    */,final String data, final String addr, final Context context,final Handler handler){

        new Thread(new Runnable() {
            Message message = handler.obtainMessage();

            @Override
            public void run() {
                HttpURLConnection connection = getconnection(context,addr);
                try {
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    connection.connect();

                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();// 获取响应的输入流对象
                        ByteArrayOutputStream msg = new ByteArrayOutputStream(); // 创建字节输出流对象

                        // 定义缓冲区
                        int len = 0;
                        byte buffer[] = new byte[1024];
                        while ((len = is.read(buffer)) != -1) {// 按照缓冲区的大小，循环读取
                            msg.write(buffer, 0, len);// 根据读取的长度写入到os对象中
                        }
                        // 释放资源
                        is.close();
                        msg.close();
                        String reponse = new String(msg.toByteArray());

                        message.arg1 = flag;
                        message.obj = reponse;
                        handler.sendMessage(message);
                    }else {
                        message.arg1 = 0;
                        message.obj = "服务器正忙...";
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    Log.e("test---------",e.toString());
                    message.arg1 = 0;
                    message.obj = "服务器掉线...";
                    handler.sendMessage(message);
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

}

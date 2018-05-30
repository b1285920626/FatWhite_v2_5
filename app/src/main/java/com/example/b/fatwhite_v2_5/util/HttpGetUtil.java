package com.example.b.fatwhite_v2_5.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by B on 2018-05-09.
 */

public class HttpGetUtil {
    private Handler handler;
    private Message message = new Message();

    String address;
    LocalDB localDB;
    public void initargs(Handler uiHandle,Context context,String tablename,LocalDB localDB)
    {
        handler = uiHandle;
        this.localDB = localDB;

        String IP =context.getString(R.string.IP_1);
        address = "http://"+IP+":8080/FatWhite_Server/DownloadServer?tablename="+tablename;
    }

    public void sendHttpGETRequest()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    HandleWordRespondseUtil.handlewordResponse(localDB,response.toString());
                    message.arg1 = 1;
                    handler.sendMessage(message);
                } catch (Exception e)
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
    }


}
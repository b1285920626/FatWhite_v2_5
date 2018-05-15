package com.example.b.fatwhite_v2_5.httputil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by B on 2018-05-08.
 */
//奇怪但好用的线程类
public class DownloadTask extends AsyncTask<Void,Context,Boolean> {
//    protected String IP = "10.10.8.103";//服务器IP地址
    protected String IP = "192.168.191.1";//猎豹"192.168.191.1"
    private ProgressDialog pd = null;
    private Context context = null;
    private LocalDB localDB;
    private List<Word> wordList = new ArrayList<Word>();


    //构造函数，可以传参
    public DownloadTask(Context inCont){
        context = inCont;
        localDB = new LocalDB(context);
    }

    //启动前
    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context); // 显示进度对话框
        pd.setMessage("正在加载...");
//        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    //动作
    @Override
    protected Boolean doInBackground(Void... params) {
        String tablename = "word_cet4";
        String address = "http://"+IP+":8080/FatWhite_Server/DownloadServer?tablename="+tablename;

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                HandleWordRespondseUtil.handlewordResponse(localDB, response);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

        });

        return true;
    }

    //与动作同时进行
    protected void onProgressUpdate(Integer... values) {
        pd.setMessage("Downloaded " + values[0] + "%");//进度条？出来一个圈
    }

    //可以用来更新UI，在结束时候执行
    @Override
    protected void onPostExecute(Boolean result) {
        pd.dismiss();

        // 在这里提示下载结果
        if (result) {
            Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }
}

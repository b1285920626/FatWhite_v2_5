package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.model.Paper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HistoryPaperActivity extends Activity {
    Context context;

    TextView textViewpapername;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    String IP;
    String STORE_NAME_0 = "paper";
    SharedPreferences paper;
    SharedPreferences.Editor editor;

    String STORE_NAME_1 = "User_info";
    SharedPreferences user_info;

    String address;
    String paper_name;

    Paper mypaper = new Paper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Intent intent = getIntent();
        paper_name = intent.getStringExtra("paper_name");

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_report_problem_black_24dp)//设置标题的图片
                .setTitle("注意：")//设置对话框的标题
                .setMessage("在答题卡中选择提交后才有效！！！")//设置对话框的内容
                .setNeutralButton("我知道了", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);//点击外面没有用
        dialog.show();

        context = this;
        textViewpapername = (TextView)findViewById(R.id.textView_papername);
        textView1 = (TextView)findViewById(R.id.exam_textView0);
        textView2 = (TextView)findViewById(R.id.exam_textView1);
        textView3 = (TextView)findViewById(R.id.exam_textView2);
        textView4 = (TextView)findViewById(R.id.exam_textView3);

        paper = context.getSharedPreferences(STORE_NAME_0, MODE_PRIVATE);
        user_info = getSharedPreferences(STORE_NAME_1, MODE_PRIVATE);
        editor = paper.edit();

        IP = getString(R.string.IP_1);
        address = "http://"+IP+":8080/FatWhite_Server/PapersServer?paper="+paper_name+"&type="+user_info.getString("type","cet4");

        HistoryPaperActivity.MyTask myTask = new HistoryPaperActivity.MyTask();
        myTask.execute();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message message) {
            Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    //获取试卷的线程
    private class MyTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
//                data_paper = response.toString().split("#");

                Gson gson = new Gson();
                editor.putString("mypaper_json",response.toString());
                editor.commit();
                mypaper = gson.fromJson(response.toString(),Paper.class);
            } catch (Exception e) {
                Log.e("httptests", "run: ", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            textViewpapername.setText(mypaper.getname());
            textView1.setText(mypaper.getquestion_0().getquestion().replaceAll("\\|","\n\n"));
            textView2.setText(mypaper.getquestion_1().getquestion().replaceAll("\\|","\n\n"));
            textView3.setText(mypaper.getquestion_2().getquestion().replaceAll("\\|","\n\n"));
            textView4.setText(mypaper.getquestion_3().getquestion().replaceAll("\\|","\n\n"));
        }
    }
}

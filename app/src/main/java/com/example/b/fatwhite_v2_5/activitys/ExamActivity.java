package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExamActivity extends Activity {
    AlertDialog dialog_answercard;
    Spinner spinner_choose;
    String[] data_paper;
    Context context;
    String address;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;

    Handler handler;

    String STORE_NAME = "paper";
    SharedPreferences user_info;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_report_problem_black_24dp)//设置标题的图片
                .setTitle("注意：")//设置对话框的标题
                .setMessage("在答题卡中选择提交后才有效哦！！！")//设置对话框的内容
                .setNeutralButton("我知道了", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);//点击外面没有用
        dialog.show();

        context = this;

        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        editor = user_info.edit();

//        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
//        View myview = View.inflate(ExamActivity.this,R.layout.dialog_choose,null);
////        spinner_choose = (Spinner)myview.findViewById(R.id.spinner_choose);
//
//        builder.setView(myview);AlertDialog dialog_choose;
//        dialog_choose = builder.create();
//        dialog_choose.setTitle("选择试卷");
//        dialog.setCancelable(false);//点击外面没有用



        //选择试卷
        String IP = getString(R.string.IP_1);
        address = "http://"+IP+":8080/FatWhite_Server/PapersServer?paper=roll";
//        sendHttpGETRequest1();

//        spinner_choose = (Spinner)myview.findViewById(R.id.spinner_choose);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_multiple_choice,data_paper);
//        spinner_choose.setAdapter(adapter);
//
//        dialog_choose.show();

//        textView_article = (TextView)findViewById(R.id.textView_article);
//        textView_question = (TextView)findViewById(R.id.textView_question);

        textView1 = (TextView)findViewById(R.id.exam_textView0);
        textView2 = (TextView)findViewById(R.id.exam_textView1);
        textView3 = (TextView)findViewById(R.id.exam_textView2);
        textView4 = (TextView)findViewById(R.id.exam_textView3);
        textView5 = (TextView)findViewById(R.id.exam_textView4);
        textView6 = (TextView)findViewById(R.id.exam_textView5);
        textView7 = (TextView)findViewById(R.id.exam_textView6);
        textView8 = (TextView)findViewById(R.id.exam_textView7);


//        handler = new Handler(){
//            public void handleMessage(Message msg){
//                if(msg.arg1 == 1){
//
//                }
//            }
//        };
        MyTask mTask = new MyTask();
        mTask.execute();

    }

    //答题卡按钮
    public void button_answercard_onclick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        View myview = View.inflate(ExamActivity.this,R.layout.dialog_answercard,null);

        //获取控件记得写
        builder.setView(myview);
        dialog_answercard = builder.create();
        dialog_answercard.setTitle("答题卡:");
        dialog_answercard.show();

        Point outsize =new Point();
        getWindowManager().getDefaultDisplay().getSize(outsize);
        dialog_answercard.getWindow().setLayout(outsize.x,outsize.y/3*2);
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }

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
                data_paper = response.toString().split("#");
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
                editor.putString("1",data_paper[0]);
                editor.putString("2",data_paper[1]);
                editor.putString("3",data_paper[2]);
                editor.putString("4",data_paper[3]);
                editor.putString("5",data_paper[4]);
                editor.putString("6",data_paper[5]);
                editor.putString("7",data_paper[6]);
                editor.putString("8",data_paper[7]);

                textView1.setText(data_paper[0]);
                textView2.setText(data_paper[1]);
                textView3.setText(data_paper[2]);
                textView4.setText(data_paper[3]);
                textView5.setText(data_paper[4]);
                textView6.setText(data_paper[5]);
                textView7.setText(data_paper[6]);
                textView8.setText(data_paper[7]);
        }
    }

    public void sendHttpGETRequest1()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    data_paper = response.toString().split("#");



//                    Message message = new Message();
//                    message.arg1 = 1;
//                    handler.sendMessage(message);
                } catch (Exception e)
                {
                    Log.e("httptests", "run: ",e );
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}


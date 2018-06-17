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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.model.Paper;
import com.example.b.fatwhite_v2_5.util.HttpPostUtil;
import com.example.b.fatwhite_v2_5.util.ToSBC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

    String paper_name;
    List<String> myanswerlist;

    Paper mypaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historypaper);
        Intent intent = getIntent();
        paper_name = intent.getStringExtra("paper_name");

        context = this;
        textViewpapername = (TextView)findViewById(R.id.h_textView_papername);
        textView1 = (TextView)findViewById(R.id.h_exam_textView0);
        textView2 = (TextView)findViewById(R.id.h_exam_textView1);
        textView3 = (TextView)findViewById(R.id.h_exam_textView2);
        textView4 = (TextView)findViewById(R.id.h_exam_textView3);

        paper = context.getSharedPreferences(STORE_NAME_0, MODE_PRIVATE);
        user_info = getSharedPreferences(STORE_NAME_1, MODE_PRIVATE);

        String address = "HistoryPaperServer";
        try {
            String data = "openid=" + user_info.getString("User_openid", "")
                    + "&paper_name=" + URLEncoder.encode(paper_name, "UTF-8");

            HttpPostUtil.send_data(1,data,address,context,handler);
        }catch (Exception e){}

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message message) {
            String str[] = message.obj.toString().split("#");
            mypaper = new Gson().fromJson(str[0],Paper.class);
            myanswerlist = new Gson().fromJson(str[1],new TypeToken<List<String>>(){}.getType());

            textViewpapername.setText(mypaper.getname());
            textView1.setText(mypaper.getquestion_0().getquestion().replaceAll("\\|","\n\n"));
            textView2.setText(mypaper.getquestion_1().getquestion().replaceAll("\\|","\n\n"));
            textView3.setText(mypaper.getquestion_2().getquestion().replaceAll("\\|","\n\n"));
            textView4.setText(mypaper.getquestion_3().getquestion().replaceAll("\\|","\n\n"));
        }
    };

    public void button_answer_onclick(View view){
        List<String[]> errorlist = new ArrayList<>();
        String[] answer = (mypaper.getquestion_0().getanswer()+"|"+mypaper.getquestion_1().getanswer()+"|"+mypaper.getquestion_2().getanswer()+"|"+mypaper.getquestion_3().getanswer()).split("\\|");//字符串有问题
        for (int i = 0; i < answer.length; i++){
            String num = Integer.toString(i+26);

            String[] erroranswer = new String[3];
            erroranswer[0] = num;
            erroranswer[1] = answer[i];
            erroranswer[2] = myanswerlist.get(i);

            errorlist.add(erroranswer);
        }

        StringBuilder output = new StringBuilder();
        output.append(ToSBC.dbc2sbc("题号 |正确选项|你的选项")+"\n");
        for (int i = 0; i < errorlist.size(); i++){
            output.append(ToSBC.dbc2sbc(errorlist.get(i)[0]+".|"+errorlist.get(i)[1]+"   |"+errorlist.get(i)[2])+"\n");
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_notifications_black_24dp)//设置标题的图片
                .setTitle("结果：")//设置对话框的标题
                .setMessage(output.toString())//设置对话框的内容
                .setNeutralButton("我知道了", new DialogInterface.OnClickListener() {//设置对话框的按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);//点击外面没有用
        dialog.show();
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }

    //获取试卷的线程
//    private class MyTask extends AsyncTask<Void, Integer, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            HttpURLConnection connection = null;
//            try {
//                URL url = new URL(address);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setConnectTimeout(3000);
//                connection.setReadTimeout(3000);
//                InputStream in = connection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                String[] str = response.toString().split("#");
//
//                Gson gson = new Gson();
//                mypaper = gson.fromJson(str[0],Paper.class);
//                myanswerlist = gson.fromJson(str[1],new TypeToken<List<String>>(){}.getType());
//            } catch (Exception e) {
//                Log.e("httptests", "run: ", e);
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            }
//            return false;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            textViewpapername.setText(mypaper.getname());
//            textView1.setText(mypaper.getquestion_0().getquestion().replaceAll("\\|","\n\n"));
//            textView2.setText(mypaper.getquestion_1().getquestion().replaceAll("\\|","\n\n"));
//            textView3.setText(mypaper.getquestion_2().getquestion().replaceAll("\\|","\n\n"));
//            textView4.setText(mypaper.getquestion_3().getquestion().replaceAll("\\|","\n\n"));
//        }
//    }
}

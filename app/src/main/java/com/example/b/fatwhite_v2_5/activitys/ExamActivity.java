package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.model.Paper;
import com.example.b.fatwhite_v2_5.util.ToSBC;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//-----------------------------------开始---------------------------------------------------------------------------

public class ExamActivity extends Activity {
    AlertDialog dialog_answercard;
    Spinner spinner;
    TextView textViewpapername;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    Spinner spinner26;
    Spinner spinner27;
    Spinner spinner28;
    Spinner spinner29;
    Spinner spinner30;
    Spinner spinner31;
    Spinner spinner32;
    Spinner spinner33;
    Spinner spinner34;
    Spinner spinner35;

    Spinner spinner36;
    Spinner spinner37;
    Spinner spinner38;
    Spinner spinner39;
    Spinner spinner40;
    Spinner spinner41;
    Spinner spinner42;
    Spinner spinner43;
    Spinner spinner44;
    Spinner spinner45;

    Spinner spinner46;
    Spinner spinner47;
    Spinner spinner48;
    Spinner spinner49;
    Spinner spinner50;

    Spinner spinner51;
    Spinner spinner52;
    Spinner spinner53;
    Spinner spinner54;
    Spinner spinner55;

    AlertDialog dialog_setting;

    Paper mypaper = new Paper();

    Context context;
    String address;
    String[] myanswer;

    String IP;
    String STORE_NAME_0 = "paper";
    SharedPreferences paper;
    SharedPreferences.Editor editor;

    String STORE_NAME_1 = "User_info";
    SharedPreferences user_info;

//---------------------------分割线----------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

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

        myanswer = new String[40];

        //选择试卷
        IP = getString(R.string.IP_1);
//        address = "http://"+IP+":8080/FatWhite_Server/PapersServer?paper=roll&type="+user_info.getString("type","cet4");

        FirstTask firstTask = new FirstTask();
        firstTask.execute();
    }

    //退出按钮，存储和清空原答案
    public void onBackPressed(View view){
        //取出paper的数据
  //      paper.;

        editor.clear().commit();
        super.onBackPressed();
    }


//---------------------初始化完成---------------------------------------------------------------------------------------

    //答题卡按钮
    public void button_answercard_onclick(View view){
        //获取控件....(╯‵□′)╯︵┻━┻
        if(spinner26 == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
            View myview = View.inflate(ExamActivity.this,R.layout.dialog_answercard,null);
            builder.setView(myview);
            dialog_answercard = builder.create();
            dialog_answercard.setTitle("答题卡:");

            Point outsize =new Point();
            getWindowManager().getDefaultDisplay().getSize(outsize);
            dialog_answercard.getWindow().setLayout(outsize.x,outsize.y/7*6);

            spinner26 = (Spinner) myview.findViewById(R.id.spinner26);
            spinner27 = (Spinner) myview.findViewById(R.id.spinner27);
            spinner28 = (Spinner) myview.findViewById(R.id.spinner28);
            spinner29 = (Spinner) myview.findViewById(R.id.spinner29);
            spinner30 = (Spinner) myview.findViewById(R.id.spinner30);
            spinner31 = (Spinner) myview.findViewById(R.id.spinner31);
            spinner32 = (Spinner) myview.findViewById(R.id.spinner32);
            spinner33 = (Spinner) myview.findViewById(R.id.spinner33);
            spinner34 = (Spinner) myview.findViewById(R.id.spinner34);
            spinner35 = (Spinner) myview.findViewById(R.id.spinner35);

            spinner36 = (Spinner) myview.findViewById(R.id.spinner36);
            spinner37 = (Spinner) myview.findViewById(R.id.spinner37);
            spinner38 = (Spinner) myview.findViewById(R.id.spinner38);
            spinner39 = (Spinner) myview.findViewById(R.id.spinner39);
            spinner40 = (Spinner) myview.findViewById(R.id.spinner40);
            spinner41 = (Spinner) myview.findViewById(R.id.spinner41);
            spinner42 = (Spinner) myview.findViewById(R.id.spinner42);
            spinner43 = (Spinner) myview.findViewById(R.id.spinner43);
            spinner44 = (Spinner) myview.findViewById(R.id.spinner44);
            spinner45 = (Spinner) myview.findViewById(R.id.spinner45);

            spinner46 = (Spinner) myview.findViewById(R.id.spinner46);
            spinner47 = (Spinner) myview.findViewById(R.id.spinner47);
            spinner48 = (Spinner) myview.findViewById(R.id.spinner48);
            spinner49 = (Spinner) myview.findViewById(R.id.spinner49);
            spinner50 = (Spinner) myview.findViewById(R.id.spinner50);

            spinner51 = (Spinner) myview.findViewById(R.id.spinner51);
            spinner52 = (Spinner) myview.findViewById(R.id.spinner52);
            spinner53 = (Spinner) myview.findViewById(R.id.spinner53);
            spinner54 = (Spinner) myview.findViewById(R.id.spinner54);
            spinner55 = (Spinner) myview.findViewById(R.id.spinner55);

            spinner26.setOnItemSelectedListener(spinSelectedListener);
            spinner27.setOnItemSelectedListener(spinSelectedListener);
            spinner28.setOnItemSelectedListener(spinSelectedListener);
            spinner29.setOnItemSelectedListener(spinSelectedListener);
            spinner30.setOnItemSelectedListener(spinSelectedListener);
            spinner31.setOnItemSelectedListener(spinSelectedListener);
            spinner32.setOnItemSelectedListener(spinSelectedListener);
            spinner33.setOnItemSelectedListener(spinSelectedListener);
            spinner34.setOnItemSelectedListener(spinSelectedListener);
            spinner35.setOnItemSelectedListener(spinSelectedListener);
            spinner36.setOnItemSelectedListener(spinSelectedListener);
            spinner37.setOnItemSelectedListener(spinSelectedListener);
            spinner38.setOnItemSelectedListener(spinSelectedListener);
            spinner39.setOnItemSelectedListener(spinSelectedListener);
            spinner40.setOnItemSelectedListener(spinSelectedListener);
            spinner41.setOnItemSelectedListener(spinSelectedListener);
            spinner42.setOnItemSelectedListener(spinSelectedListener);
            spinner43.setOnItemSelectedListener(spinSelectedListener);
            spinner44.setOnItemSelectedListener(spinSelectedListener);
            spinner45.setOnItemSelectedListener(spinSelectedListener);
            spinner46.setOnItemSelectedListener(spinSelectedListener);
            spinner47.setOnItemSelectedListener(spinSelectedListener);
            spinner48.setOnItemSelectedListener(spinSelectedListener);
            spinner49.setOnItemSelectedListener(spinSelectedListener);
            spinner50.setOnItemSelectedListener(spinSelectedListener);
            spinner51.setOnItemSelectedListener(spinSelectedListener);
            spinner52.setOnItemSelectedListener(spinSelectedListener);
            spinner53.setOnItemSelectedListener(spinSelectedListener);
            spinner54.setOnItemSelectedListener(spinSelectedListener);
            spinner55.setOnItemSelectedListener(spinSelectedListener);
        }
        spinner26.setSelection(paper.getInt("26",0));
        spinner27.setSelection(paper.getInt("27",0));
        spinner28.setSelection(paper.getInt("28",0));
        spinner29.setSelection(paper.getInt("29",0));
        spinner30.setSelection(paper.getInt("30",0));
        spinner31.setSelection(paper.getInt("31",0));
        spinner32.setSelection(paper.getInt("32",0));
        spinner33.setSelection(paper.getInt("33",0));
        spinner34.setSelection(paper.getInt("34",0));
        spinner35.setSelection(paper.getInt("35",0));

        spinner36.setSelection(paper.getInt("36",0));
        spinner37.setSelection(paper.getInt("37",0));
        spinner38.setSelection(paper.getInt("38",0));
        spinner39.setSelection(paper.getInt("39",0));
        spinner40.setSelection(paper.getInt("40",0));
        spinner41.setSelection(paper.getInt("41",0));
        spinner42.setSelection(paper.getInt("42",0));
        spinner43.setSelection(paper.getInt("43",0));
        spinner44.setSelection(paper.getInt("44",0));
        spinner45.setSelection(paper.getInt("45",0));

        spinner46.setSelection(paper.getInt("46",0));
        spinner47.setSelection(paper.getInt("47",0));
        spinner48.setSelection(paper.getInt("48",0));
        spinner49.setSelection(paper.getInt("49",0));
        spinner50.setSelection(paper.getInt("50",0));

        spinner51.setSelection(paper.getInt("51",0));
        spinner52.setSelection(paper.getInt("52",0));
        spinner53.setSelection(paper.getInt("53",0));
        spinner54.setSelection(paper.getInt("54",0));
        spinner55.setSelection(paper.getInt("55",0));
        dialog_answercard.show();
    }

    //监听spinner
    Spinner.OnItemSelectedListener spinSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.spinner26:
//                    Toast.makeText(ExamActivity.this,"aa:"+Integer.toString(spinner26.getSelectedItemPosition()),Toast.LENGTH_SHORT).show();
                    editor.putInt("26",spinner26.getSelectedItemPosition()); editor.commit(); myanswer[0] = spinner26.getSelectedItem().toString();break;
                case R.id.spinner27:
                    editor.putInt("27",spinner27.getSelectedItemPosition()); editor.commit(); myanswer[1] = spinner27.getSelectedItem().toString();break;
                case R.id.spinner28:
                    editor.putInt("28",spinner28.getSelectedItemPosition()); editor.commit(); myanswer[2] = spinner28.getSelectedItem().toString();break;
                case R.id.spinner29:
                    editor.putInt("29",spinner29.getSelectedItemPosition()); editor.commit(); myanswer[3] = spinner29.getSelectedItem().toString();break;
                case R.id.spinner30:
                    editor.putInt("30",spinner30.getSelectedItemPosition()); editor.commit(); myanswer[4] = spinner30.getSelectedItem().toString();break;
                case R.id.spinner31:
                    editor.putInt("31",spinner31.getSelectedItemPosition()); editor.commit(); myanswer[5] = spinner31.getSelectedItem().toString();break;
                case R.id.spinner32:
                    editor.putInt("32",spinner32.getSelectedItemPosition()); editor.commit(); myanswer[6] = spinner32.getSelectedItem().toString();break;
                case R.id.spinner33:
                    editor.putInt("33",spinner33.getSelectedItemPosition()); editor.commit(); myanswer[7] = spinner33.getSelectedItem().toString();break;
                case R.id.spinner34:
                    editor.putInt("34",spinner34.getSelectedItemPosition()); editor.commit(); myanswer[8] = spinner34.getSelectedItem().toString();break;
                case R.id.spinner35:
                    editor.putInt("35",spinner35.getSelectedItemPosition()); editor.commit(); myanswer[9] = spinner35.getSelectedItem().toString();break;

                case R.id.spinner36:
                    editor.putInt("36",spinner36.getSelectedItemPosition()); editor.commit(); myanswer[10] = spinner36.getSelectedItem().toString();break;
                case R.id.spinner37:
                    editor.putInt("37",spinner37.getSelectedItemPosition()); editor.commit(); myanswer[11] = spinner37.getSelectedItem().toString();break;
                case R.id.spinner38:
                    editor.putInt("38",spinner38.getSelectedItemPosition()); editor.commit(); myanswer[12] = spinner38.getSelectedItem().toString();break;
                case R.id.spinner39:
                    editor.putInt("39",spinner39.getSelectedItemPosition()); editor.commit(); myanswer[13] = spinner39.getSelectedItem().toString();break;
                case R.id.spinner40:
                    editor.putInt("40",spinner40.getSelectedItemPosition()); editor.commit(); myanswer[14] = spinner40.getSelectedItem().toString();break;
                case R.id.spinner41:
                    editor.putInt("41",spinner41.getSelectedItemPosition()); editor.commit(); myanswer[15] = spinner41.getSelectedItem().toString();break;
                case R.id.spinner42:
                    editor.putInt("42",spinner42.getSelectedItemPosition()); editor.commit(); myanswer[16] = spinner42.getSelectedItem().toString();break;
                case R.id.spinner43:
                    editor.putInt("43",spinner43.getSelectedItemPosition()); editor.commit(); myanswer[17] = spinner43.getSelectedItem().toString();break;
                case R.id.spinner44:
                    editor.putInt("44",spinner44.getSelectedItemPosition()); editor.commit(); myanswer[18] = spinner44.getSelectedItem().toString();break;
                case R.id.spinner45:
                    editor.putInt("45",spinner45.getSelectedItemPosition()); editor.commit(); myanswer[19] = spinner45.getSelectedItem().toString();break;

                case R.id.spinner46:
                    editor.putInt("46",spinner46.getSelectedItemPosition()); editor.commit(); myanswer[20] = spinner46.getSelectedItem().toString();break;
                case R.id.spinner47:
                    editor.putInt("47",spinner47.getSelectedItemPosition()); editor.commit(); myanswer[21] = spinner47.getSelectedItem().toString();break;
                case R.id.spinner48:
                    editor.putInt("48",spinner48.getSelectedItemPosition()); editor.commit(); myanswer[22] = spinner48.getSelectedItem().toString();break;
                case R.id.spinner49:
                    editor.putInt("49",spinner49.getSelectedItemPosition()); editor.commit(); myanswer[23] = spinner49.getSelectedItem().toString();break;
                case R.id.spinner50:
                    editor.putInt("50",spinner50.getSelectedItemPosition()); editor.commit(); myanswer[24] = spinner50.getSelectedItem().toString();break;

                case R.id.spinner51:
                    editor.putInt("51",spinner51.getSelectedItemPosition()); editor.commit(); myanswer[25] = spinner51.getSelectedItem().toString();break;
                case R.id.spinner52:
                    editor.putInt("52",spinner52.getSelectedItemPosition()); editor.commit(); myanswer[26] = spinner52.getSelectedItem().toString();break;
                case R.id.spinner53:
                    editor.putInt("53",spinner53.getSelectedItemPosition()); editor.commit(); myanswer[27] = spinner53.getSelectedItem().toString();break;
                case R.id.spinner54:
                    editor.putInt("54",spinner54.getSelectedItemPosition()); editor.commit(); myanswer[28] = spinner54.getSelectedItem().toString();break;
                case R.id.spinner55:
                    editor.putInt("55",spinner55.getSelectedItemPosition()); editor.commit(); myanswer[29] = spinner55.getSelectedItem().toString();break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

//----------------------------------答题卡分割线-----------------------------------------------------------------------

    //提交按钮，交卷
    public void button_putin_onclick(View view){
        dialog_answercard.dismiss();
        paper = context.getSharedPreferences(STORE_NAME_0,0);

        List<String[]> errorlist = new ArrayList<>();
        String[] answer = (mypaper.getquestion_0().getanswer()+"|"+mypaper.getquestion_1().getanswer()+"|"+mypaper.getquestion_2().getanswer()+"|"+mypaper.getquestion_3().getanswer()).split("\\|");//字符串有问题
        for (int i = 0; i < answer.length; i++){
            String num = Integer.toString(i+26);
            if(!answer[i].equals(myanswer[i])){
                String[] erroranswer = new String[3];
                erroranswer[0] = num;
                erroranswer[1] = answer[i];
                erroranswer[2] = myanswer[i];
                errorlist.add(erroranswer);
            }
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

        Gson gson = new Gson();
        String myerrorlist = gson.toJson(errorlist);
        editor.putString("errorlist",myerrorlist).commit();
        Log.e("errorlist",myerrorlist);
    }

//----------------------------------↓弹框选试卷名----------------------------------------------------------------------------

    //询问试卷名称
    private class FirstTask extends AsyncTask<Void, Integer, Boolean> {
        String[] papernames;

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://"+IP+":8080/FatWhite_Server/PapersServer?paper=15242005956&type="+user_info.getString("type","cet4"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                response.append("roll#");
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                papernames = response.toString().split("#");
            }catch (Exception e){
                Log.e("httptests", "run: ", e);
            }finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myview = View.inflate(context,R.layout.dialog_setting,null);

            builder.setView(myview);
            dialog_setting = builder.create();
            dialog_setting.setTitle("请选择试卷...");
            dialog_setting.setCancelable(false);

            spinner = (Spinner)myview.findViewById(R.id.spinner_settype);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,papernames);
            spinner.setAdapter(adapter);

            dialog_setting.show();
        }
    }

    //弹框里那个确定按钮，从登录活动里复制来的
    public void button_settingok_onclick(View view){
        //上传userinfo
        String papername = spinner.getSelectedItem().toString();
        address = "http://"+IP+":8080/FatWhite_Server/PapersServer?paper="+papername+"&type="+user_info.getString("type","cet4");
        editor.putString("papername",papername);
        textViewpapername.setText(papername);

        MyTask mTask = new MyTask();
        mTask.execute();

        editor.commit();
        dialog_setting.dismiss();
    }

//-----------------------------下载+显示试卷----------------------------------------------------------------------------------

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


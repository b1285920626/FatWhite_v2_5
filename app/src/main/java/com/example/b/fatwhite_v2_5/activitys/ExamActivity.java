package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.b.fatwhite_v2_5.R;

public class ExamActivity extends Activity {
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
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }
}


package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.MainActivity;
import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.util.HttpPostUtil;

public class SignupActivity extends Activity {
    private EditText newUserName;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private Spinner spinner_type;

    private Context context;
    private String[] typearray;

    String STORE_NAME = "User_info";
    SharedPreferences user_info;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context = SignupActivity.this;
        newUserName = (EditText) findViewById(R.id.newUserName);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newPasswordAgain = (EditText) findViewById(R.id.newPasswordAgain);
        spinner_type = (Spinner) findViewById(R.id.spinner_type);

        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        editor = user_info.edit();

        HttpPostUtil.send_data(1,"openid=a","LoginServer",context,handler);//用不存在的openid获取type列表
    }

    public void button_ok_signup_onclick(View view){
        if (newPassword.getText().toString().equals(newPasswordAgain.getText().toString())){
            String data = "user_id="+newUserName.getText().toString()
                        + "&password="+newPassword.getText().toString()
                        + "&type="+spinner_type.getSelectedItem().toString();

            if(!TextUtils.isEmpty(newUserName.getText().toString())&&!TextUtils.isEmpty(newPassword.getText().toString())&&!TextUtils.isEmpty(newPasswordAgain.getText().toString())){
                HttpPostUtil.send_data(0,data,"Set_User_info2",context,handler);
            }else {
                Toast.makeText(this,"有空的啊", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"两次密码不一致", Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if(message.arg1 == 1){
                typearray = message.obj.toString().split("\\|");
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,typearray);
                spinner_type.setAdapter(adapter);
            }else if(message.arg1 == 0){
                Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
                if(message.obj.toString().equals("success")){
                    editor.clear().commit();
                    editor.putString("User_openid",newUserName.getText().toString()).putString("type",spinner_type.getSelectedItem().toString()).commit();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    SignupActivity.this.finish();
                }
            }
        }
    };
}

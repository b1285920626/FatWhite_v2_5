package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.model.NewWord;
import com.example.b.fatwhite_v2_5.model.Word;
import com.example.b.fatwhite_v2_5.util.HttpPostUtil;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewWordActivity extends Activity {
    EditText editText_word;
    EditText editText_translation;
    AlertDialog dialog;
    LocalDB localDB;
    List<NewWord> wordList = new ArrayList<NewWord>();
    String[] data;
    Context context;

    ArrayAdapter<String> adapter;
    ListView listView;

    String STORE_NAME = "User_info";
    SharedPreferences user_info;

//--------------------------------------初始化------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newword);

        context = this;
        localDB = new LocalDB(NewWordActivity.this);
        try{
            wordList = localDB.loadPrivateWord();
        } catch (Exception e){
            System.out.println(e.toString());
        }

        data = new String[wordList.size()];
        for(int i = 0; i < wordList.size();i++) data[i] = wordList.get(i).get_word();
        adapter = new ArrayAdapter<String>(NewWordActivity.this,android.R.layout.simple_expandable_list_item_1,data);
        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        //搜索，用了filter过滤器
        EditText editText_search = (EditText)findViewById(R.id.editText_search);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { adapter.getFilter().filter(s); listView.setAdapter(adapter); }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //我们需要的内容，跳转页面或显示详细信息 列表点击
                final String myword=(String)listView.getItemAtPosition(position);
                String temptranslation = "";
                for(int i = 0; i < wordList.size();i++){
                    if(wordList.get(i).get_word().equals(myword)) temptranslation = wordList.get(i).get_translation();
                }

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_notifications_black_24dp)//设置标题的图片
                        .setTitle(myword)//设置对话框的标题
                        .setMessage(temptranslation)//设置对话框的内容
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {//设置对话框的按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton("删除",new DialogInterface.OnClickListener() {//设置对话框的按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除并刷新列表
                                try{
                                    int temp = localDB.delete(myword);
                                    wordList = localDB.loadPrivateWord();
                                    Toast.makeText(context,"删除成功", Toast.LENGTH_SHORT).show();
                                } catch (Exception e){
                                    System.out.println(e.toString());
                                }
                                data = new String[wordList.size()];
                                for(int i = 0; i < wordList.size();i++) data[i] = wordList.get(i).get_word();
                                adapter = new ArrayAdapter<String>(NewWordActivity.this,android.R.layout.simple_expandable_list_item_1,data);
                                listView.setAdapter(adapter);
                                refreshCapacity();
                            }
                        }).create();
                Log.e("testnewword",myword);
                dialog.show();

            }
        });

        user_info = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
        refreshCapacity();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message message) {
            Toast.makeText(context,message.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };

//--------------------------------------按钮弹框-------------------------------------------------------------------------------

    public void button_addnewword_onclick (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewWordActivity.this);
        View myview = View.inflate(NewWordActivity.this,R.layout.dialog_newword_view,null);

        editText_word = (EditText)myview.findViewById(R.id.newword_input);
        editText_translation = (EditText)myview.findViewById(R.id.newtranslation_input);
        builder.setView(myview);
        dialog = builder.create();
        dialog.setTitle("添加新词");
        dialog.show();

        Point outsize =new Point();
        getWindowManager().getDefaultDisplay().getSize(outsize);
        dialog.getWindow().setLayout(outsize.x,outsize.y/3);
    }

//--------------------------------------保存新词-------------------------------------------------------------------------------

    public void button_add_onclick(View view){
        NewWord word =new NewWord();
        if(wordList.size() >= getResources().getInteger(R.integer.list_maxnum)){
            Log.e("maxnum",Integer.toString(getResources().getInteger(R.integer.list_maxnum)));
            return;
        }
        else if( !TextUtils.isEmpty(editText_word.getText()) && !TextUtils.isEmpty(editText_translation.getText())){
            for(int i = 0; i < wordList.size(); i++){
                if(editText_word.getText().toString().equals(wordList.get(i).get_word())){
                    editText_word.setText("");
                    Toast.makeText(this,"单词已存在，请重新输入",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            word.set_word(editText_word.getText().toString());
            word.set_translation(editText_translation.getText().toString());

            try{
                localDB.savePrivateWord(word);
                dialog.dismiss();
            }catch (Exception e){
                Toast.makeText(NewWordActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }


        try{
            wordList = localDB.loadPrivateWord();
        } catch (Exception e){
            System.out.println(e.toString());
        }
        data = new String[wordList.size()];
        for(int i = 0; i < wordList.size();i++) data[i] = wordList.get(i).get_word();
        adapter = new ArrayAdapter<String>(NewWordActivity.this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);
        refreshCapacity();
    }
//----------------------------------------退出时上传----------------------------------------------------------------------------------------------------------------
    public void onBackPressed(View view){
        //上传
        Gson gson = new Gson();
        String wordlist = gson.toJson(wordList);
        try { wordlist = URLEncoder.encode(wordlist, "UTF-8"); }catch (Exception e){}

        String worddata = "openid="+user_info.getString("User_openid","")+"&wordlist="+wordlist;
        String address = "Save_wordlist";

        HttpPostUtil.send_data(0,worddata,address,context,handler);

        super.onBackPressed();
    }

    //刷新标题栏上的容量显示
    public void refreshCapacity(){
        TextView capacity = (TextView)findViewById(R.id.capacity);
        String str ="生词本容量："+ wordList.size()+"/"+getResources().getInteger(R.integer.list_maxnum);
        capacity.setText(str);
    }
}

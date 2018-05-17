package com.example.b.fatwhite_v2_5.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b.fatwhite_v2_5.R;
import com.example.b.fatwhite_v2_5.db.LocalDB;
import com.example.b.fatwhite_v2_5.model.Word;

import java.util.ArrayList;
import java.util.List;

public class NewWordActivity extends Activity {
    EditText editText_word;
    EditText editText_translation;
    EditText editText_search;
    AlertDialog dialog;
    LocalDB localDB;
    List<Word> wordList = new ArrayList<Word>();
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newword);

        localDB = new LocalDB(NewWordActivity.this);
        try{
        wordList = localDB.loadPrivateWord();}
        catch (Exception e){
            System.out.println(e.toString());
        }

        data = new String[wordList.size()];
        for(int i = 0; i < wordList.size();i++) data[i] = wordList.get(i).get_word();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewWordActivity.this,android.R.layout.simple_expandable_list_item_1,data);
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        EditText editText_search = (EditText)findViewById(R.id.editText_search);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { adapter.getFilter().filter(s); }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //我们需要的内容，跳转页面或显示详细信息

            }
        });
    }

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

    public void button_add_onclick(View view){
        Word word =new Word();
        if( !TextUtils.isEmpty(editText_word.getText()) && !TextUtils.isEmpty(editText_translation.getText())){
            word.set_word(editText_word.getText().toString());
            word.set_translation(editText_translation.getText().toString());
        }
        try{
            localDB.savePrivateWord(word);
            dialog.dismiss();
        }catch (Exception e){
            Toast.makeText(NewWordActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(View view){
        super.onBackPressed();
    }
}

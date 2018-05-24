package com.example.b.fatwhite_v2_5.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

public class SettingFragment extends Fragment {
    TextView textview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

    public void settextview(Activity activity, String str){
        textview = (TextView) activity.findViewById(R.id.textView_useless);
        textview.setText("当前用户：" + str);
    }
}

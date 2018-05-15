package com.example.b.fatwhite_v2_5.fragment;

import android.app.Activity;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

public class HomeFragment extends Fragment {
    TextView textview1;
    TextView textview2;
    TextView textview3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    public void settextview_1(Activity activity, String str){
        textview1 = (TextView) activity.findViewById(R.id.textview_1);
        textview1.setText(str);
    }

    public void settextview_2(Activity activity, String str){
        textview2 = (TextView) activity.findViewById(R.id.textview_2);
        textview2.setText(str);
    }

    public void settextview_3(Activity activity, String str){
        textview3 = (TextView) activity.findViewById(R.id.textview_3);
        textview3.setText(str);
    }
}

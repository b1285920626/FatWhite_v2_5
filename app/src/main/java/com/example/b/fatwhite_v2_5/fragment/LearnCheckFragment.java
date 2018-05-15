package com.example.b.fatwhite_v2_5.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

public class LearnCheckFragment extends Fragment {
    View view;
    private TextView click_here;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fragment_learn_check,container,false);
        this.view = view;
        return view;
    }

    public void setClick_here(Activity activity, String str){
        click_here = (TextView)activity.findViewById(R.id.click_here);
        click_here.setText(str);
    }

}

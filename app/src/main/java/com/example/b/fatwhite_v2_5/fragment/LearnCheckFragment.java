package com.example.b.fatwhite_v2_5.fragment;

import android.app.Fragment;
import android.os.Bundle;
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
        setview(view);
        return view;
    }

    public void setview(View view){
        this.view = view;
        click_here = (TextView)view.findViewById(R.id.click_here);
        click_here.setText("点这儿看翻译");
    }

    public void setClick_here(String str){
        click_here.setText(str);
    }

}

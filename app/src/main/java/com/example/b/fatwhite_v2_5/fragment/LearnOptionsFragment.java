package com.example.b.fatwhite_v2_5.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

public class LearnOptionsFragment extends Fragment {
    private TextView options_A;
    private TextView options_B;
    private TextView options_C;
    private TextView options_D;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fragment_learn_options,container,false);

        options_A = (TextView)view.findViewById(R.id.options_A);
        options_B = (TextView)view.findViewById(R.id.options_B);
        options_C = (TextView)view.findViewById(R.id.options_C);
        options_D = (TextView)view.findViewById(R.id.options_D);

        return view;
    }

    public void setview(){

    }

    public void setOptions_A(String str){
        options_A.setText(str);
    }

    public void setOptions_B(String str){
        options_B.setText(str);
    }

    public void setOptions_C(String str){
        options_C.setText(str);
    }

    public void setOptions_D(String str){
        options_D.setText(str);
    }

}

package com.example.b.fatwhite_v2_5.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.fatwhite_v2_5.R;

public class LearnOptionsFragment extends Fragment {
//    View view;
    private TextView options_A;
    private TextView options_B;
    private TextView options_C;
    private TextView options_D;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fragment_learn_options,container,false);
//        this.view = view;
        return view;
    }

    public void setOptions_A(Activity activity, String str){
        options_A = (TextView) activity.findViewById(R.id.options_A);
        options_A.setText("A、"+str);
    }

    public void setOptions_B(Activity activity,String str){
        options_B = activity.findViewById(R.id.options_B);
        options_B.setText("B、"+str);
    }

    public void setOptions_C(Activity activity,String str){
        options_C = activity.findViewById(R.id.options_C);
        options_C.setText("C、"+str);
    }

    public void setOptions_D(Activity activity,String str){
        options_D = activity.findViewById(R.id.options_D);
        options_D.setText("D、"+str);
    }

}

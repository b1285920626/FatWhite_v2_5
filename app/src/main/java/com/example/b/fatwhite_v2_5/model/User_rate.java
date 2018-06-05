package com.example.b.fatwhite_v2_5.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User_rate {
    private int tail;
    private List<Integer> id_flag_list;
    private int today_rate;
    private int plan;

    private String date;

    public User_rate(){
        tail = 0;
        id_flag_list = new ArrayList<>();
        plan = 20;

        Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = dateFormat.format( now );

		if(!nowdate.equals(date)){
		    today_rate = 0;
		    date = nowdate;
        }
    }

    public void setTail (int tail){
        if(tail == this.tail + 1) {
            this.tail = tail;
            for(int i = id_flag_list.size(); i > -1; i--){
                if(id_flag_list.get(i) <= tail ){
                    id_flag_list.remove(i);
                }
            }
        }else {
            add_id_flag(tail);
        }
    }
    public int getTail(){
        return tail;
    }

    public void add_id_flag(int flag){
        id_flag_list.add(id_flag_list.size(),flag);
    }
    public List<Integer> getId_flag_list(){
        return id_flag_list;
    }

    public void setplan(int plan){
        this.plan = plan;
    }
    public int getplan(){
        return plan;
    }

    public void settoday_rate(){
        today_rate++;
    }
    public int gettoday_rate(){
        return today_rate;
    }
}

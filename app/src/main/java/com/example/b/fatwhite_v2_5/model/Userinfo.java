package com.example.b.fatwhite_v2_5.model;

public class Userinfo {
    private String User_openid;
    private String User_name;
    private String type;//cet4?cst6。。。
    private int User_rate_today;//今日进度

    public String get_User_name(){
        return User_name;
    }
    public void set_User_name(String User_name){ this.User_name = User_name; }

    public String get_User_openid(){
        return User_openid;
    }
    public void set_User_openid(String User_openid){ this.User_openid = User_openid; }

    public String get_type(){
        return type;
    }
    public void set_type(String type){
        this.type = type;
    }

    public int get_User_rate_today(){
        return User_rate_today;
    }
    public void set_User_rate_today(int User_rate_today){
        this.User_rate_today = User_rate_today;
    }

}

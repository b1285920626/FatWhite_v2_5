package com.example.b.fatwhite_v2_5.model;

public class Userinfo {
    private String User_ID;
    private String User_name;
    private String User_openid;
    private int User_age;
    private int User_level;//cet4?cst6?...
    private int User_rate;//今日进度
    private int passdays;//背单词的组数，一组视作一天
    private String lastday;

    public String get_User_ID(){
        return User_ID;
    }
    public void set_User_ID(String User_ID){ this.User_ID = User_ID; }

    public String get_User_name(){
        return User_name;
    }
    public void set_User_name(String User_name){ this.User_name = User_name; }

    public String get_User_openid(){
        return User_openid;
    }
    public void set_User_openid(String User_openid){ this.User_openid = User_openid; }

    public int get_User_age(){
        return User_age;
    }
    public void set_User_age(int User_age){
        this.User_age = User_age;
    }

    public int get_User_level(){
        return User_level;
    }
    public void set_User_level(int User_level){
        this.User_level = User_level;
    }

    public int get_User_rate(){
        return User_rate;
    }
    public void set_User_rate(int User_rate){
        this.User_rate = User_rate;
    }

    public int get_passdays(){
        return passdays;
    }
    public void set_passdays(int passdays){
        this.passdays = passdays;
    }

    public String get_lastday(){
        return lastday;
    }
    public void set_lastday(String lastday){
        this.lastday = lastday;
    }
}

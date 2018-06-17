package com.example.b.fatwhite_v2_5.model;

public class HistoryPaper {
    private String name;
    private String type;//这是真的type，就那个cet4什么什么的
    private String datetime;
    private int question_0_id;
    private int question_1_id;
    private int question_2_id;
    private int question_3_id;

    public void setname(String name) {this.name = name;}
    public String getname () {return name+datetime;}

    public void settype(String type) {this.type = type;}
    public String gettype () {return type;}

    public void setdatetime(String datetime) {this.datetime = datetime;}

    public void setquestion_0_id (int question_0_id) {this.question_0_id = question_0_id;}
    public int getquestion_0_id () {return question_0_id; }

    public void setquestion_1_id (int question_1_id) {this.question_1_id = question_1_id;}
    public int getquestion_1_id () {return question_1_id; }

    public void setquestion_2_id (int question_2_id) {this.question_2_id = question_2_id;}
    public int getquestion_2_id () {return question_2_id; }

    public void setquestion_3_id (int question_3_id) {this.question_3_id = question_3_id;}
    public int getquestion_3_id () {return question_3_id; }

}

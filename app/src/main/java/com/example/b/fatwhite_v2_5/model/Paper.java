package com.example.b.fatwhite_v2_5.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Paper {
	private int id;
	private String type;//这是真的type，就那个cet4什么什么的
	private String name;
	private String datetime;
	private Question question_0;
	private Question question_1;
	private Question question_2;
	private Question question_3;
	
	public void setid(int id) { this.id = id;}
	public int getid() {return id;}
	
	public void settype(String type) {this.type = type;}
	public String gettype () {return type;}
	
	public void setname(String name) {this.name = name;}
	public String getname () {return name;}
	
	public void setdatetime(String datetime) {this.datetime = datetime;
//		Date now = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//		datetime = dateFormat.format( now );
//		System.out.println(datetime);
	}
	public String getdatatime() {return datetime;}
	
	public void setquestion_0(Question question) {this.question_0 = question;}
	public Question getquestion_0() {return question_0;}
	
	public void setquestion_1(Question question) {this.question_1 = question;}
	public Question getquestion_1() {return question_1;}
	
	public void setquestion_2(Question question) {this.question_2 = question;}
	public Question getquestion_2() {return question_2;}
	
	public void setquestion_3(Question question) {this.question_3 = question;}
	public Question getquestion_3() {return question_3;}
}

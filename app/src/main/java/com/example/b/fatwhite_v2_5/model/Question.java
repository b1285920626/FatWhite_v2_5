package com.example.b.fatwhite_v2_5.model;

public class Question {
	private int id;
	private int type;//这是那个表示顺序的
	private String question;
	private String answer;
	
	public void setid (int id) {this.id = id;}
	public int getid () {return id; }

	public void settype (int type) {this.type = type;}
	public int gettype () {return type; }
	
	public void setquestion (String question) {this.question = question;}
	public String getquestion () {return question; }
	
	public void setanswer (String answer) {this.answer = answer;}
	public String getanswer () {return answer; }
}

package com.example.b.fatwhite_v2_5.model;

public class NewWord {
    private int id;
    private String word;
    private String translation;

    public int get_id(){
        return id;
    }
    public void set_id(int id){
        this.id = id;
    }

    public String get_word(){
        return word;
    }
    public void set_word(String word){ this.word = word; }

    public String get_translation(){
        return translation;
    }
    public void set_translation(String translation){
        this.translation = translation;
    }
}

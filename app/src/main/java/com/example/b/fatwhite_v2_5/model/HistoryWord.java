package com.example.b.fatwhite_v2_5.model;

public class HistoryWord {
    private int id;
    private int word_id;
    private String word;
    private String soundmark;
    private String translation;
    private String sentence;
    private int importance;
    private int times;

    public int get_id(){
        return id;
    }
    public void set_id(int id){
        this.id = id;
    }

    public int get_word_id(){
        return word_id;
    }
    public void set_word_id(int word_id){
        this.word_id = word_id;
    }

    public String get_word(){
        return word;
    }
    public void set_word(String word){ this.word = word; }

    public String get_soundmark(){
        return soundmark;
    }
    public void set_soundmark(String soundmark){
        this.soundmark = soundmark;
    }

    public String get_translation(){
        return translation;
    }
    public void set_translation(String translation){
        this.translation = translation;
    }

    public String get_sentence(){
        return sentence;
    }
    public void set_sentence(String sentence){
        this.sentence = sentence;
    }

    public int get_importance(){
        return importance;
    }
    public void set_importance(int importance){
        this.importance = importance;
    }

    public int get_times(){
        return times;
    }
    public void set_times(int times){
        this.times = times;
    }
}

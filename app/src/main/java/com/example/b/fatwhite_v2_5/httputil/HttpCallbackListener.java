package com.example.b.fatwhite_v2_5.httputil;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}

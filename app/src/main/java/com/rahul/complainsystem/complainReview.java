package com.rahul.complainsystem;

/**
 * Created by LENOVO on 07-05-2016.
 */
public class complainReview {
    String resolve;
    complainReview(String a){
        resolve = a;
    }

    @Override
    public String toString() {
        return  ""+resolve;
    }
}

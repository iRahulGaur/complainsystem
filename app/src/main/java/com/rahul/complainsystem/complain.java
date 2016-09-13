package com.rahul.complainsystem;

/**
 * Created by LENOVO on 07-05-2016.
 */
public class complain {
    String cno;
    String name;
    String block;
    String house;
    String type;
    String detail;
    String date;
    String resolve;
    String phone;
    String imei;
    complain(String a, String b, String c, String d,
             String e, String f, String g, String h,
             String i, String j){
        cno = a;
        name = b;
        block = c;
        house = d;
        type = e;
        detail = f;
        date = g;
        resolve = h;
        phone = i;
        imei = j;
    }

    @Override
    public String toString() {
        return  "Complain Number = "+cno+
                " Name = "+name+
                " Block = "+block+
                " House No. = "+house+
                " Phone Number = "+phone+
                " Complain Type = "+type+
                " Complain Date = "+date+
                " Resolve on = "+resolve+
                " Detail = "+detail;
    }
}

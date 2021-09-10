package com.example.tourplanner;

public class Event {
    String id;
    String title;
    String place;
    String email;
    String phone;
    String des;
    String date;
    String link;
    Event(String id,String title, String place, String email, String phone, String des, String date,String link){
        this.id=id;
        this.title=title;
        this.place=place;
        this.email=email;
        this.phone=phone;
        this.des=des;
        this.date=date;
        this.link=link;
    }
}

package com.example.tourplanner;

public class User {
    String phone,firstName,lastName;
    String password;
    String id;
    String email;
    User(String firstName,String lastName,String phone, String password,String id, String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.password=password;
        this.id=id;
        this.email=email;

    }
}

package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class EventShow extends AppCompatActivity {
    TextView titleTF,placeTF,emailTF,phoneTF,dateTF,desTF,linkTF;

    String title;
    String place;
    String email;
    String phone;
    String date;
    String des;
    String id;
    String admin;
    String link;
    //String url="http://192.168.43.136/tourPlanner/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_show);

    }
    @Override
    protected void onStart()
    {
        super.onStart();

        Bundle bundle = getIntent().getExtras();

        id=bundle.getString("id");
        title=bundle.getString("title");
        place=bundle.getString("place");
        email=bundle.getString("email");
        phone=bundle.getString("phone");
        date=bundle.getString("date");
        des=bundle.getString("des");
        admin=bundle.getString("admin");
        link=bundle.getString("link");


        titleTF=findViewById(R.id.stitle);
        placeTF=findViewById(R.id.sPlace);
        emailTF=findViewById(R.id.semail);
        phoneTF=findViewById(R.id.sphone);
        dateTF=findViewById(R.id.sData);
        desTF=findViewById(R.id.sDescription);
        linkTF=findViewById(R.id.sDescription);


        titleTF.setText(title);
        placeTF.setText(place);
        emailTF.setText(email);
        phoneTF.setText(phone);
        dateTF.setText(date);
        desTF.setText(des);
        linkTF.setText(link);


        //
        //System.out.println(obj.title+"_________________________________________");

    }
}





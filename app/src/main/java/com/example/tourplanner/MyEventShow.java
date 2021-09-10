package com.example.tourplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MyEventShow extends AppCompatActivity {
    ListView lv;
    ArrayList<Event> holder = new ArrayList<>();
    TextView titleTF,placeTF,emailTF,phoneTF,dateTF,desTF,linkTF;

    String title;
    String place;
    String email;
    String phone;
    String date;
    String des;
    String id;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tour_show);

    }
    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventShow.this,editEvent.class);
                i.putExtra("id", id);
                i.putExtra("title", title);
                i.putExtra("place", place);
                i.putExtra("email", email);
                i.putExtra("phone", phone);
                i.putExtra("date", date);
                i.putExtra("des", des);
                i.putExtra("link", link);
                System.out.println(id);
                System.out.println(title);
                System.out.println(place);
                System.out.println(email);
                System.out.println(phone);
                System.out.println(date);
                System.out.println(des);
                System.out.println(link);
                startActivity(i);
            }
        });

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
        link=bundle.getString("link");


        titleTF=findViewById(R.id.stitle);
        placeTF=findViewById(R.id.sPlace);
        emailTF=findViewById(R.id.semail);
        phoneTF=findViewById(R.id.sphone);
        dateTF=findViewById(R.id.sData);
        desTF=findViewById(R.id.sDescription);
        linkTF=findViewById(R.id.link);



        titleTF.setText(title);
        placeTF.setText(place);
        emailTF.setText(email);
        phoneTF.setText(phone);
        dateTF.setText(date);
        desTF.setText(des);
        linkTF.setText(link);
        findViewById(R.id.btnDrive).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventShow.this,drive.class);
                i.putExtra("link",link);
                System.out.println(link+"TourId___________________________________");
                startActivity(i);
            }
        });

        findViewById(R.id.btnmembers).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventShow.this,EventMemList.class);
                i.putExtra("id",id);
                System.out.println(id+"TourId___________________________________");
                startActivity(i);


            }
        });
        findViewById(R.id.btnmsg).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventShow.this,msgActivity.class);
                i.putExtra("tour_id",id);

                startActivity(i);


            }
        });
        findViewById(R.id.btnAlarm).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventShow.this, AlarmActivity.class);
                startActivity(i);


            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                MyEventShow.this.finish();
            }
        });
    }


}





package com.example.tourplanner;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {
    TextView bTF;
    EditText dayET,hourET,minET,secET;
    int day,hour,min,sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        createNotificationChanel();
        bTF=(TextView)findViewById(R.id.set);
        dayET=(EditText)findViewById(R.id.day);
        hourET=(EditText)findViewById(R.id.hour);
        minET=(EditText)findViewById(R.id.min);
        secET=(EditText)findViewById(R.id.sec);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                AlarmActivity.this.finish();
            }
        });

        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                String d=dayET.getText().toString();
                if(d.isEmpty()){
                    d="0";
                }
                String h=hourET.getText().toString();
                if(h.isEmpty()){
                    h="0";
                }
                String m=minET.getText().toString();
                if(m.isEmpty()){
                    m="0";
                }
                String s=secET.getText().toString();
                if(s.isEmpty()){
                    s="0";
                }
                day=Integer.parseInt(d);

                hour=Integer.parseInt(h);
                min=Integer.parseInt(m);
                sec=Integer.parseInt(s);
                Toast.makeText(AlarmActivity.this,"Reminder set",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(AlarmActivity.this,ReminderBroadcast.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmActivity.this,0,i,0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                long timeAtButtonClick=System.currentTimeMillis();
                long rday=86400000*day;
                long rhour=3600000*hour;
                long rmin=60000*min;
                long seconds=1000*sec;
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        timeAtButtonClick+seconds+rday+rmin,
                        pendingIntent);

            }
        });
    }
    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name="LemubitReminderChanel";
            String description="Chanel for lemobit reminder";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel=new NotificationChannel("a",name,importance);
            chanel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }
    }
    private void showDialog(String message, String title, String buttonlabel, boolean closeDialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message);
        builder.setTitle(title);

////Setting message manually and performing action on button click
        builder.setCancelable(true)//false dile only back diye cancel hobe
                .setNegativeButton(buttonlabel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(closeDialog){
                            dialog.cancel();
                        }
                        else{
                            finish();
                        }


                    }
                });
////Creating dialog box
        AlertDialog alert = builder.create();
////Setting the title manually
        alert.setTitle("Alert!");
        alert.show();
    }
}

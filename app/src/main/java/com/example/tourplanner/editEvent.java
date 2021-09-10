package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class editEvent extends AppCompatActivity {
    String apiurl="http://192.168.42.158/tourPlanner/update_Tour.php";
    ListView lv;
    ArrayList<Event> holder = new ArrayList<>();
    EditText titleTF,placeTF,emailTF,phoneTF,dateTF,desTF,linkTF;

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
        setContentView(R.layout.activity_tour_edit);

    }
    @Override
    public void onResume(){
        super.onResume();


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
        System.out.println(id);
        System.out.println(title);
        System.out.println(place);
        System.out.println(email);
        System.out.println(phone);
        System.out.println(date);
        System.out.println(des);
        System.out.println(link);
        System.out.println("Edit_______________________________________");

        titleTF=(EditText) findViewById(R.id.eltitle);
        placeTF=findViewById(R.id.elPlace);
        dateTF=findViewById(R.id.elDataTime);
        desTF=findViewById(R.id.elDescription);
        linkTF=findViewById(R.id.elLink);



        titleTF.setText(title);
        placeTF.setText(place);
        dateTF.setText(date);
        desTF.setText(des);
        linkTF.setText(link);



        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                title=titleTF.getText().toString();
                place=placeTF.getText().toString();
                des=desTF.getText().toString();
                date=dateTF.getText().toString();
                link=linkTF.getText().toString();


                insertData();
            }
        });

    }

    private void insertData() {
        System.out.println(id+"___"+email+"___"+phone+"___"+title+"___"+place+"___"+des+"___"+date+"___"+link);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, apiurl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();
                System.out.println(response.toString()+"______________________________");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(SignupActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                System.out.println(error.toString()+"______________________________");

            }
        }

        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();

                params.put("title",title);
                params.put("place",place);
                params.put("email",email);
                params.put("phone",phone);
                params.put("date",date);
                params.put("des",des);
                params.put("userId",id);
                params.put("link",link);
                System.out.println("Working____________________________________________");


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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





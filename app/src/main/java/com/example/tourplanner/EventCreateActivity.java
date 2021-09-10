package com.example.tourplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


public class EventCreateActivity extends AppCompatActivity {
    private EditText titleTF,placeTF, desTF,dateTf,linkTf;
    String title,id;
    String place;
    String des;
    String email;
    String phone;
    String date;
    String link;
    String errorMsg="";
    String url="http://192.168.42.158/tourPlanner/insertTour.php";
    SharedPreferences.Editor prefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_create);
        SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
        id=sharedPreferences.getString("id",null);
        email=sharedPreferences.getString("email",null);
        phone=sharedPreferences.getString("phone",null);


        titleTF=(EditText)findViewById(R.id.eltitle);
        placeTF=(EditText)findViewById(R.id.elPlace);
        desTF=(EditText)findViewById(R.id.elDescription);
        dateTf=(EditText)findViewById(R.id.elDataTime);
        linkTf=(EditText)findViewById(R.id.elLink);




        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                title=titleTF.getText().toString();
                place=placeTF.getText().toString();
                des=desTF.getText().toString();
                date=dateTf.getText().toString();
                link=linkTf.getText().toString();


                insertData();
            }
        });
    }

    private void insertData() {

        System.out.println(id+"___"+email+"___"+phone+"___"+title+"___"+place+"___"+des+"___"+date+"___"+link);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
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

}





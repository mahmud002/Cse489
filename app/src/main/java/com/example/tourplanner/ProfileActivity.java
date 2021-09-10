package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText fnameTF, lnameTF,emailTF, phoneTF;
    TextView hedderTV;
    String fname,id;
    String lname;
    String email;
    String phone;
    String hedder="User Profile";
    String errorMsg="";

    private static final String apiurl="http://192.168.42.158/tourPlanner/update_User.php";
    private static final String apiurl2="http://192.168.42.158/tourPlanner/delete_user.php";

    ArrayList<User> holder = new ArrayList<>();
    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
        id=sharedPreferences.getString("id",null);
        fname=sharedPreferences.getString("fname",null);
        lname=sharedPreferences.getString("lname",null);
        email=sharedPreferences.getString("email",null);
        phone=sharedPreferences.getString("phone",null);


        hedderTV=(TextView) findViewById(R.id.sHedding);
        fnameTF=(EditText) findViewById(R.id.elName);
        lnameTF=(EditText) findViewById(R.id.ellName);
        emailTF=(EditText) findViewById(R.id.elEmail);
        phoneTF=(EditText) findViewById(R.id.elPhone);
        hedderTV.setText("User Profile");
        fnameTF.setText(fname);
        lnameTF.setText(lname);
        emailTF.setText(email);
        phoneTF.setText(phone);
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                fname=fnameTF.getText().toString();
                lname=lnameTF.getText().toString();
                email=emailTF.getText().toString();
                phone=phoneTF.getText().toString();
                //password=passwordTF.getText().toString();
                //connPassword= conPasswordTF.getText().toString();
                //System.out.println(password+"__________________"+connPassword);

                if(fname.isEmpty()){
                    errorMsg="First Name Cant be Empty";
                }
                if(phone.isEmpty()){
                    errorMsg="Phone Number Cant be Empty";
                }

                if(errorMsg.isEmpty()){

                    insertData();
                    showDialog("Data Save Successfully", "info", "OK", true);
                }else{
                    showDialog(errorMsg, "info", "OK", true);
                    errorMsg="";
                }

                //SignupActivity.this.finish();
            }
        });
        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                deleteData();
                //ProfileActivity.this.finish();
                finish();
                System.exit(0);

            }
        });

    }

    private void deleteData() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, apiurl2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                System.out.println(error.toString()+"______________________________");

            }
        }

        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("id",id);


                System.out.println("Working____________________________________________");


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void insertData() {



        System.out.println(fname+lname+email+phone);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, apiurl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                System.out.println(error.toString()+"______________________________");

            }
        }

        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("id",id);
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("phone",phone);

                System.out.println("Working____________________________________________");


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




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
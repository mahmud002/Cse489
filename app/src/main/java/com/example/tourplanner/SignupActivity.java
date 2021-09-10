package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


import androidx.appcompat.app.AppCompatActivity;


public class SignupActivity extends AppCompatActivity {
    private EditText fnameTF, lnameTF,emailTF, phoneTF, passwordTF,conPasswordTF;
    String fname;
    String lname;
    String email;
    String phone;
    String password;
    String connPassword;
    String errorMsg="";
    String url="http://192.168.42.158/tourPlanner/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_create);

        fnameTF=(EditText)findViewById(R.id.elName);
        lnameTF=(EditText)findViewById(R.id.ellName);
        emailTF=(EditText)findViewById(R.id.elEmail);
        phoneTF=(EditText)findViewById(R.id.elPhone);
        passwordTF=(EditText)findViewById(R.id.password);
        conPasswordTF=(EditText)findViewById(R.id.confirm_password);


        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                fname=fnameTF.getText().toString();
                lname=lnameTF.getText().toString();
                email=emailTF.getText().toString();
                phone=phoneTF.getText().toString();
                password=passwordTF.getText().toString();
                connPassword= conPasswordTF.getText().toString();
                System.out.println(password+"__________________"+connPassword);

                if(fname.isEmpty()){
                    errorMsg="First Name Cant be Empty";
                }
                if(phone.isEmpty()){
                    errorMsg="Phone Number Cant be Empty";
                }
                if(password.equals(connPassword)==false){
                    errorMsg="Password & Confirm Password Doesn't Match";
                }
                if(password.length()<4){
                    errorMsg="Password Cant be less then 4";
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
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                SignupActivity.this.finish();
            }
        });
    }

    private void insertData() {


        System.out.println(fname+lname+email+phone+password+connPassword);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                System.out.println(error.toString()+"______________________________");

            }
        }

        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();

                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("phone",phone);
                params.put("password",password);
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





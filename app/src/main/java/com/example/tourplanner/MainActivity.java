package com.example.tourplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    private EditText phoneTF, passwordTF;
    String phone,password,id,email;
    int flag=0;
    private static final String apiurl="http://192.168.42.158/tourPlanner/read.php";
    ListView lv;
    ArrayList<User> holder = new ArrayList<>();
    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                phoneTF=(EditText)findViewById(R.id.logphone);
                passwordTF=(EditText)findViewById(R.id.logpassword);

                phone=phoneTF.getText().toString();
                password=passwordTF.getText().toString();

                //Enumeration<String> e = Collections.enumeration(holder);
                //while(e.hasMoreElements())
                //   System.out.println(e.nextElement()+"_____________________________________");

                for(int i =0; i<holder.size(); i++){
                    User u = holder.get(i);
                    if(u.phone.equals(phone) && u.password.equals(password)){
                        //System.out.println("Login Success________________________");
                        flag=1;
                        SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor=sharedPreferences.edit();
                        prefsEditor.putString("id",u.id);
                        prefsEditor.putString("fname",u.firstName);
                        prefsEditor.putString("lname",u.lastName);
                        prefsEditor.putString("email",u.email);
                        prefsEditor.putString("phone",u.phone);
                        prefsEditor.commit();

                        break;
                    }
                }
                if(flag==1){
                    SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor=sharedPreferences.edit();


                    prefsEditor.commit();
                    Intent i=new Intent(MainActivity.this,EventActivity.class);
                    phone=null;
                    password=null;
                    startActivity(i);
                    //showDialog("Login Success", "Warning", "OK", true);
                }else{

                    showDialog("Phone or Password doesn't match", "Warning", "OK", true);
                }


            }
        });

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        fetchdata();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        findViewById(R.id.linkSignup).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

    }

    private void fetchdata() {

        class dbManager extends AsyncTask<String,Void,String> {

            @Override
            protected void onPostExecute(String data){

                try{
                    JSONArray ja=new JSONArray(data);
                    JSONObject jo=null;
                    holder.clear();
                    for (int i=0; i<ja.length(); i++){
                        jo=ja.getJSONObject(i);
                        String firstName=jo.getString("firstName");
                        String lastName=jo.getString("lastName");
                        String phone=jo.getString("phone");
                        String password=jo.getString("password");
                        String id=jo.getString("id");
                        String email=jo.getString("email");

                        holder.add(new User(firstName,lastName,phone,password,id,email));
                    }
                    ArrayAdapter<User> at=new ArrayAdapter<User>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    //lv.setAdapter(at);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url=new URL (strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer data=new StringBuffer();
                    String line;
                    while((line=br.readLine())!=null){
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }catch (Exception ex){
                    return ex.getMessage();
                }

            }
        }
        dbManager obj=new dbManager();
        obj.execute(apiurl);
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
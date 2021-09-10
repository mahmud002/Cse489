package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class AddMember extends AppCompatActivity {
    String apiurl="http://192.168.42.158/tourPlanner/read.php";
    String apiurl2="http://192.168.42.158/tourPlanner/insertMember.php";
    String event_id,snum;
    String user_id;
    ListView lv;
    ArrayList<User> holder = new ArrayList<>();
    @Override
    protected void onStart()
    {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        event_id=bundle.getString("Event_id");
        snum=bundle.getString("snum");
        System.out.println(event_id+snum+"_______________________________");
        fetchdata();

    }




    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addm);

    }
    private void fetchdata() {
        lv=(ListView)findViewById(R.id.lvadd);
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

                        if(snum.isEmpty()){
                            holder.add(new User(firstName,lastName,phone,password,id,email));
                        }else if(snum.equals(phone)){
                            holder.add(new User(firstName,lastName,phone,password,id,email));
                            break;
                        }


                    }
                    CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(),holder);
                    //ArrayAdapter<Event> at=new ArrayAdapter<Event>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
// String item = (String) parent.getItemAtPosition(position);
                            System.out.println(position);
                            user_id=holder.get(position).id;

                            insertData();
                            showDialog("User Added", "info", "OK", true);

                        }
                    });

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

    private void insertData() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, apiurl2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();



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


                params.put("user_id",user_id);
                params.put("tour_id",event_id);

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





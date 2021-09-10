package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ResourceBundle;


public class MyEventActivity extends AppCompatActivity {
    private static final String apiurl="http://192.168.42.158/tourPlanner/readMyTour.php";
    String durl="http://192.168.42.158/tourPlanner/deleteTour.php";
    String apiurl2;
    ListView lv;
    String t;
    ArrayList<Event> holder = new ArrayList<>();
    SharedPreferences.Editor prefsEditor;
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
        String id=sharedPreferences.getString("id",null);
        apiurl2="";
        apiurl2=apiurl+"?id="+id;
        fetchdata();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_tour);
        findViewById(R.id.btnCreate_new).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MyEventActivity.this,EventCreateActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                MyEventActivity.this.finish();
            }
        });


    }
    private void fetchdata() {
        lv=(ListView)findViewById(R.id.lvEvents);
        class dbManager extends AsyncTask<String,Void,String> {

            @Override
            protected void onPostExecute(String data){

                try{
                    JSONArray ja=new JSONArray(data);
                    JSONObject jo=null;
                    holder.clear();
                    for (int i=0; i<ja.length(); i++){
                        jo=ja.getJSONObject(i);
                        String event_id=jo.getString("tour_id");
                        String title=jo.getString("title");
                        String place=jo.getString("place");
                        String email=jo.getString("email");
                        String phone=jo.getString("phone");
                        String des=jo.getString("des");
                        String date=jo.getString("date");
                        String link=jo.getString("link");
                        System.out.println(event_id+"___________________event id______________________");
                        Event e = new Event(event_id,title,place,email,phone,des,date,link);
                        holder.add(e);
                    }
                    CustomEventAdapter adapter = new CustomEventAdapter(getApplicationContext(),holder);
                    //ArrayAdapter<Event> at=new ArrayAdapter<Event>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(adapter);
                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                             String message="Do you want to delete this event -  ?";

                            showDialog(message,"Delete Event", position);
                            System.out.println("Long press worked____________________________");

                            return true;
                        }
                    });
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
// String item = (String) parent.getItemAtPosition(position);
                            System.out.println(position);

                            Intent i = new Intent(MyEventActivity.this, MyEventShow.class);
                            //i.putExtra("EventKey", events[position].key);
                            i.putExtra("id", holder.get(position).id);
                            i.putExtra("title", holder.get(position).title);
                            i.putExtra("place", holder.get(position).place);
                            i.putExtra("email", holder.get(position).email);
                            i.putExtra("phone", holder.get(position).phone);
                            i.putExtra("date", holder.get(position).date);
                            i.putExtra("des", holder.get(position).des);
                            i.putExtra("link", holder.get(position).link);

                            startActivity(i);
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
        obj.execute(apiurl2);
    }
    private void showDialog(String message, String title, int position ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage(message);
        builder.setTitle(title);

////Setting message manually and performing action on button click
        builder.setCancelable(true)//false dile only back diye cancel hobe
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Util.getInstance().deleteByKey(UpcomingEventsActivity.this,events[position].key);
                        t=holder.get(position).id;

                        StringRequest stringRequest=new StringRequest(Request.Method.POST, durl,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(MyEventActivity.this,response,Toast.LENGTH_SHORT).show();



                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MyEventActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                                System.out.println(error.toString()+"______________________________");

                            }
                        }

                        ){
                            @Override
                            protected Map<String,String> getParams() throws AuthFailureError {

                                Map<String,String> params=new HashMap<String, String>();
                                params.put("id",t);


                                System.out.println("Working____________________________________________");


                                return params;
                            }
                        };
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        dialog.cancel();

                    }
                });


////Creating dialog box
        AlertDialog alert = builder.create();
////Setting the title manually
        alert.setTitle("Alert!");
        alert.show();
    }


}





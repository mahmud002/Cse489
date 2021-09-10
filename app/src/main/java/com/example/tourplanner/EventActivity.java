package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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


public class EventActivity extends AppCompatActivity {
    private static final String apiurl="http://192.168.42.158/tourPlanner/readAllTour.php";
    ListView lv;
    ArrayList<Event> holder = new ArrayList<>();
    @Override
    protected void onStart()
    {
        super.onStart();
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
        setContentView(R.layout.activity_tour);
        findViewById(R.id.btnCreate_new).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(EventActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btnmytour).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(EventActivity.this,MyEventActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                EventActivity.this.finish();
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
                        String id=jo.getString("id");
                        String title=jo.getString("title");
                        String place=jo.getString("place");
                        String email=jo.getString("email");
                        String phone=jo.getString("phone");
                        String des=jo.getString("des");
                        String date=jo.getString("date");
                        String link=jo.getString("link");
                        Event e = new Event(id,title,place,email,phone,des,date,link);
                        holder.add(e);
                    }
                    CustomEventAdapter adapter = new CustomEventAdapter(getApplicationContext(),holder);
                    //ArrayAdapter<Event> at=new ArrayAdapter<Event>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
// String item = (String) parent.getItemAtPosition(position);
                            System.out.println(position);

                            Intent i = new Intent(EventActivity.this, EventShow.class);
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
        obj.execute(apiurl);
    }

}





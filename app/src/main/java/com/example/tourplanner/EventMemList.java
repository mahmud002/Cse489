package com.example.tourplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class EventMemList extends AppCompatActivity {
    private static final String apiurl="http://192.168.42.158/tourPlanner/readTourMember.php?id=";
    TextView phoneTF;
    String apiurl2,sphone;
    ListView lv;
    String Event_id;
    ArrayList<User> holder = new ArrayList<>();
    SharedPreferences.Editor prefsEditor;
    @Override
    protected void onStart()
    {
        super.onStart();
        phoneTF=(EditText)findViewById(R.id.snum);
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("id");
        Event_id=id;
        apiurl2="";
        System.out.println(id+"___________ID________________");
        apiurl2=apiurl+id;
        fetchdata();



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members);

        findViewById(R.id.sbtn).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(EventMemList.this,AddMember.class);
                String snum=phoneTF.getText().toString();
                i.putExtra("Event_id",Event_id);
                i.putExtra("snum",snum);
                System.out.println(Event_id+"TourId___________________________________");

                startActivity(i);



            }
        });
    }
    private void fetchdata() {
        lv=(ListView)findViewById(R.id.lvmembers);
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
                        System.out.println(phone+"___________________________________________");
                        System.out.println(firstName+"___________________________________________");
                        holder.add(new User(firstName,lastName,phone,password,id,email));
                    }
                    CustomUserAdapter adapter = new CustomUserAdapter(getApplicationContext(),holder);
                    //ArrayAdapter<Event> at=new ArrayAdapter<Event>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(adapter);



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


}





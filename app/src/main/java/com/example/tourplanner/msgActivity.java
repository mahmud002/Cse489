package com.example.tourplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class msgActivity extends AppCompatActivity {
    private static final String apiurl="http://192.168.42.158/tourPlanner/read_msg.php?id=";
    private static final String apiurl2="http://192.168.42.158/tourPlanner/insert_message.php";
    ListView lv;
    EditText msgTF;
    String message,tour_id,apiurl3;
    ArrayList<Msg> holder = new ArrayList<>();
    @Override
    protected void onStart()
    {
        super.onStart();
        msgTF=(EditText)findViewById(R.id.msgField);
        Bundle bundle = getIntent().getExtras();

        tour_id=bundle.getString("tour_id");
        apiurl3=apiurl+tour_id;
        System.out.println(apiurl3);
        fetchdata();

    }


    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.sendMsg).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)

            {
                message=msgTF.getText().toString();
                if(message.isEmpty()){
                    showDialog("Message Can't empty", "warning", "OK", true);
                }
                else{
                    showDialog("Message Send", "Success", "OK", true);
                    insertData();
                }




            }
        });

    }

    private void insertData() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, apiurl2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();
                System.out.println(response+"_______________________________________");



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
                SharedPreferences sharedPreferences=getSharedPreferences("mySharedPref",MODE_PRIVATE);
                String email=sharedPreferences.getString("email",null);

                params.put("tour_id",tour_id);
                params.put("email",email);
                params.put("msg",message);

                System.out.println(tour_id+email+message+"Working____________________________________________");


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg);


    }
    private void fetchdata() {

        lv=(ListView)findViewById(R.id.lvmsg);

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
                        System.out.println(id+"___________________________");
                        String tour_id=jo.getString("tour_id");
                        System.out.println(id+tour_id+"___________________________");
                        String email=jo.getString("email");
                        System.out.println(id+tour_id+email+"___________________________");
                        String msg=jo.getString("msg");
                        System.out.println(id+tour_id+email+msg+"___________________________");


                        Msg e = new Msg(email,msg);

                        holder.add(e);
                    }
                    CustomMsgAdapter adapter = new CustomMsgAdapter(getApplicationContext(),holder);
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
        obj.execute(apiurl3);
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





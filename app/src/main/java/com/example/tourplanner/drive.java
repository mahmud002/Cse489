package com.example.tourplanner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


public class drive extends AppCompatActivity {
    String link;
    private WebView webview;
    @Override
    protected void onStart()
    {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        link=bundle.getString("link");
        System.out.println(link+"_________________________________________________________________");
        webview=(WebView) findViewById(R.id.web);
        WebSettings websettings=webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl("https://drive.google.com/drive/folders/1uZgAP2LmG_D5vtjZB-zL8z8n9MxV_oiF?usp=sharing");

    }







    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_view);

    }




}





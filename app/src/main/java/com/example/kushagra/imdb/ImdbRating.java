package com.example.kushagra.imdb;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ImdbRating extends AppCompatActivity {


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb_rating);
        broadcastReceiver receiver = new broadcastReceiver();
        registerReceiver(receiver, new IntentFilter("broadcastResult"));

        Intent intent = getIntent();
    }

    public final static String RESULT = "Result";

    public void getData(View view){

        String movie = ((EditText) findViewById(R.id.movieTitle)).getText().toString();
        String stringUrl = "http://www.omdbapi.com/?t=" + movie;
        Intent intent = new Intent(this,GetDataIntentService.class);
        intent.putExtra(GetDataIntentService.MOVIE_URL,stringUrl);
        Log.v("x",stringUrl);
        startService(intent);
    }

    public class broadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //You do here like usual using intent
            String res = intent.getStringExtra(RESULT);
            Log.v("a", res);
            ((TextView) findViewById(R.id.textView)).setText(res);
        }

    }

    }




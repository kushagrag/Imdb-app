package com.example.kushagra.imdb;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetDataIntentService extends IntentService {

    public static final String MOVIE_URL = "com.example.kushagra.imdb.Movie";

    public GetDataIntentService() {
        super("GetDataIntentService");
    }

    Intent broadcast = new Intent();

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            broadcast.setAction("broadcastResult");
            String stringUrl = intent.getStringExtra(MOVIE_URL);
            Log.v("y",stringUrl);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                InputStream is = null;

                try {
                    URL url = new URL(stringUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = streamReader.readLine();
                    JSONObject json;

                    try {
                        json = new JSONObject(response.toString());
                        String result ="Title: "  + json.getString("Title") + "\n" + "Year: " + json.getString("Year") +
                                "\n" + "Imdb Rating: " + json.getString("imdbRating") + "\nGenre: " + json.getString("Genre")
                                + "\nReleased: " + json.getString("Released");
                        Log.v("z",result);
                        broadcast.putExtra(ImdbRating.RESULT,result);
                    }catch (org.json.JSONException e)
                       {
                        Log.v("Exception", e.toString());
                       }

                }catch (java.io.IOException e){
                    Log.v("Exception", e.toString());
                    }
                }
            }
            this.sendBroadcast(broadcast);
        }
    }


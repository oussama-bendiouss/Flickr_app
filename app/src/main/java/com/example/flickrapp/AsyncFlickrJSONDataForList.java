package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myactivity;
    private MyAdapter myAdapter;

    public AsyncFlickrJSONDataForList(AppCompatActivity mainactivity, MyAdapter monadaptateur) {
        myactivity = mainactivity;
        myAdapter =monadaptateur;
    }
    @Override
    protected JSONObject doInBackground(String... strings) {
        final JSONObject[] resultat_json = {null};
        URL Lien = null;
        try {
            Lien = new URL(strings[0]);
            HttpURLConnection LienConnection = (HttpURLConnection) Lien.openConnection();
            try {
                InputStream input = new BufferedInputStream(LienConnection.getInputStream());
                String msg_read = readStream(input);
                input.close();
                int len_msg = msg_read.length();
                String msg_json = msg_read.substring("jsonFlickrFeed(".length(), len_msg - 1);

                try {
                    resultat_json[0] = new JSONObject(msg_json);
                } catch (JSONException err) {
                    Log.d("Erreur dans la création du fichier JSON", err.toString());
                }
            } finally {
                LienConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        return resultat_json[0];
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {



        try {
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)
            {
                JSONObject flickr_entry = items.getJSONObject(i);
                String urlimg = flickr_entry.getJSONObject("media").getString("m");
                myAdapter.dd(urlimg);
                Log.i("JFL", "URL ajouter: " + urlimg);



            }
            myAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private String readStream(InputStream input) throws IOException {
        StringBuilder Builder = new StringBuilder();
        BufferedReader Read = new BufferedReader(new InputStreamReader(input),1000);
        for (String line = Read.readLine(); line != null; line =Read.readLine()){
            Builder.append(line);
        }
        Read.close();
        return Builder.toString();
    }

}

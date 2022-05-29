package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

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

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myactivity;

    public AsyncFlickrJSONData(AppCompatActivity mainactivity) {
        myactivity = mainactivity;
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
        Log.d("Résultat : ", jsonObject.toString());
        try {
            String lien_image = jsonObject.getJSONArray("items").getJSONObject(1).getJSONObject("media").getString("m");

            AsyncBitmapDownloader action_sec = new AsyncBitmapDownloader(myactivity);
            action_sec.execute(lien_image);
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

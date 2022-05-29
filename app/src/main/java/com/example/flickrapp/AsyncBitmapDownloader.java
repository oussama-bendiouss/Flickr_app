package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    ImageView Image;
    private AppCompatActivity myactivity;
    public AsyncBitmapDownloader(AppCompatActivity mainactivity) {
        myactivity = mainactivity;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap elem = null;
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream input = new BufferedInputStream(urlConnection.getInputStream());
                elem = BitmapFactory.decodeStream(input);
                input.close();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elem;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Image = myactivity.findViewById(R.id.image);
        Image.setImageBitmap(bitmap);
    }



}


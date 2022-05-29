package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    Context context;
    Vector<String> vector = new Vector<>();
    Double Latitude;
    Double Longitude;

    public MyAdapter(Vector<String> vector, Context context) {
        this.vector = vector;
        this.context = context;
    }


    void dd(String url) {
        vector.add(url);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("JFL", "TODO");

        String currentString =(String) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout,parent, false);
        }

        RequestQueue queue = MySingleton.getInstance(convertView.getContext()).getRequestQueue();

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_list);


        ImageRequest imageRequest=new ImageRequest (currentString,new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);

            }
        },0,0, ImageView.ScaleType.CENTER_CROP,null, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("OUPS", "Rien ne va plus");

            }
        }
        );
        queue.add(imageRequest);

        return convertView;
    }
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();

            Log.i("Latitude", Latitude.toString());
            Log.i("Longitude", Longitude.toString());

        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }


}

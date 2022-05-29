package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Vector;

public class ListViewActivity extends AppCompatActivity {
    Double Latitude= Double.valueOf(0);
    Double Longitude= Double.valueOf(0);
    ListView listView;
    Vector<String> vector = new Vector<>();
    MyAdapter myAdapter = new MyAdapter(vector, this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        }
        LocationListener locListener = new MyLocationListener();
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locListener);


        setContentView(R.layout.activity_list_view);
        listView =  findViewById(R.id.list);
        int i = 0;
        listView.setAdapter(myAdapter);
        AsyncFlickrJSONDataForList ToDoList = new AsyncFlickrJSONDataForList(ListViewActivity.this,myAdapter);
        String urlToRequest = new String("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        ToDoList.execute(urlToRequest);
        // Affichage de Longitude et latitude
        Log.i("Longitude",Longitude.toString());
        Log.i("Latitude",Latitude.toString());

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
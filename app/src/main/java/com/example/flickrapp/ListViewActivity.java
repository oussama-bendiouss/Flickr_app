package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Vector;

public class ListViewActivity extends AppCompatActivity {
    ListView listView;
    Vector<String> vector = new Vector<>();
    MyAdapter myAdapter = new MyAdapter(vector, this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView =  findViewById(R.id.list);
        int i = 0;
        listView.setAdapter(myAdapter);
        AsyncFlickrJSONDataForList ToDoList = new AsyncFlickrJSONDataForList(ListViewActivity.this,myAdapter);
        String urlToRequest = new String("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        ToDoList.execute(urlToRequest);

    }
}
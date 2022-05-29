package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button Get_Image;
    Button Go_list_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        class GetImageOnClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                AsyncFlickrJSONData task_to_do = new AsyncFlickrJSONData(MainActivity.this);
                task_to_do.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
            }
            }
            Get_Image = findViewById(R.id.bt1);
        Go_list_View = findViewById(R.id.go_list);
            Get_Image.setOnClickListener(new GetImageOnClickListener());
            Go_list_View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent main_list_intent = new Intent(MainActivity.this, ListViewActivity.class);
                   startActivity(main_list_intent);
                }
            });
        }




}


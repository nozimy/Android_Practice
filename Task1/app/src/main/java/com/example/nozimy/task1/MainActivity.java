package com.example.nozimy.task1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Image> images = new ArrayList<Image>();
    BoxAdapter boxAdapter;

    private ListView mImageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillData();
        boxAdapter = new BoxAdapter(this, images);

        mImageListView = (ListView) findViewById(R.id.lv_main);
        mImageListView.setAdapter(boxAdapter);

//
//        // создаем адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, imageLinks);
//
//        mImageListView.setAdapter(adapter);
    }

    void fillData() {
        HashMap<String, String> imagesMap = Helper.getImages();
        for (Map.Entry<String, String> entry : imagesMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            images.add(new Image(value, key, R.drawable.photo)); //todo: "image id" instead of 1?
        }
    }

}

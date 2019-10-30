package com.example.memorableplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<Double> lat, lng;
    static ArrayList<String> places;
    static ArrayAdapter<String> arrayAdapter;


    public void goToMap(int position) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("pos",position);
        if(position!=0) {
            intent.putExtra("lat",lat.get(position-1));
            intent.putExtra("long",lng.get(position-1));
            intent.putExtra("place",places.get(position));
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        listView = findViewById(R.id.listview);
        places = new ArrayList <>();
        lng = new ArrayList <>();
        lat = new ArrayList <>();
        places.clear();
        lng.clear();
        lat.clear();
        places.add("Add a new place");
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Name", ObjectSerializer.serialize(places)));
            lat = (ArrayList<Double>) ObjectSerializer.deserialize(sharedPreferences.getString("Lat",ObjectSerializer.serialize(new ArrayList<Double>())));
            lng = (ArrayList<Double>) ObjectSerializer.deserialize(sharedPreferences.getString("Long",ObjectSerializer.serialize(new ArrayList<Double>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter <>(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > parent, View view, int position, long id) {
                goToMap(position);
            }
        });

    }
}

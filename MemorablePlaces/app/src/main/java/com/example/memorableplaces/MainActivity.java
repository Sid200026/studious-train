package com.example.memorableplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        listView = findViewById(R.id.listview);
        places = new ArrayList <>();
        places.add("Add a new place");
        arrayAdapter = new ArrayAdapter <>(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > parent, View view, int position, long id) {
                goToMap(position);
            }
        });
        lat = new ArrayList <>();
        lng = new ArrayList <>();
    }
}

package com.example.multiplicationtables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public void updateTable(int progress)
    {
        ListView listView = findViewById(R.id.listview);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i<10; i++)
        {
            arrayList.add(progress*i);
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        for(int i = 0; i<=10; i++)
        {
            arrayList2.add(progress+" * "+i+" = "+arrayList.get(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList2);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(20);
        updateTable(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTable(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

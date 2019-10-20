package com.pro.deepak.com.update;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class results_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_activity);

        Intent intent = getIntent();

        String subject = intent.getStringExtra("Subject");
        String deadline = intent.getStringExtra("Deadline");
        String description = intent.getStringExtra("Description");

        TextView deadTV = findViewById(R.id.deadlineResTV);
        deadTV.setText(deadline);

        TextView descTV = findViewById(R.id.descResTV);
        descTV.setText(description);

        setTitle(subject);
    }
}

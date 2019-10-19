package com.pro.deepak.com.update;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_data extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private EditText Subject , Description , Due_date;
    private String subject , description, due_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Intent addInt = getIntent();
        String title = addInt.getStringExtra("title");
        setTitle(title);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(title);

        Subject = findViewById(R.id.title_sub);
        Description = findViewById(R.id.desc_sub);
        Due_date = findViewById(R.id.due_date_sub);

        FloatingActionButton finishFAB = findViewById(R.id.finish);
        finishFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject = Subject.getText().toString();
                description = Description.getText().toString();
                due_date = Due_date.getText().toString();

                DatabaseReference element = mDatabaseReference.push();

                element.child("Subject").setValue(subject);
                element.child("Description").setValue(description);
                element.child("Due Date").setValue(due_date);
                finish();
            }
        });


    }
}

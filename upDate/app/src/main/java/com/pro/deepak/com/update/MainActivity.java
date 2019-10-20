package com.pro.deepak.com.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    String mUsername ;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;
    private String mPhotoUrl;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//            highlight highlightTAB1 = new highlight();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, highlightTAB1)
//                    .commit();

            switch (item.getItemId()) {
                case R.id.highlight_home: {
                    highlight highlightTAB = new highlight();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, highlightTAB)
                            .commit();
                    return true;
                }

                case R.id.navigation_notifications:
                    notification  notificationTAB = new notification();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, notificationTAB)
                            .commit();
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM");
        setTitle(sdf.format(calendar.getTime()));



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.highlight_home);

        mUsername = "Deepak";

    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d("Main Activity", "onConnectionFailed:" + connectionResult);
//        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
//    }



}

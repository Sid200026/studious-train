package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userloc;
    int pos;
    String places;

    public void updateList(String addr, LatLng latLng) {
        if(addr.length()>0) {
            MainActivity.places.add(addr);
            MainActivity.lat.add(latLng.latitude);
            MainActivity.lng.add(latLng.longitude);
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
    }

    public void updateLocation(Location location) {
        userloc = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userloc).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userloc));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);
        places = "You are here";
        if(pos==0) {
            userloc = new LatLng(-34, 151);
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updateLocation(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
        else {
            userloc = new LatLng(intent.getDoubleExtra("lat",0),intent.getDoubleExtra("long",0));
            places = intent.getStringExtra("place");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions().position(userloc).title(places));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc,18));
        if(pos==0) {
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    mMap.clear();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    String addr = "";
                    try {
                        List < Address > addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (addresses != null && addresses.size() > 0) {
                            if (addresses.get(0).getSubLocality() != null) {
                                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getSubLocality()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                addr = addresses.get(0).getSubLocality();
                            } else {
                                mMap.addMarker(new MarkerOptions().position(latLng).title("You have saved this location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
                                addr = date.format(new Date());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateList(addr, latLng);
                }
            });
        }
    }
}

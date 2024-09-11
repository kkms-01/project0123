package com.example.gps_0908;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Zoom extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap myMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Zoom.this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        MarkerOptions options=new MarkerOptions().position(sydney).title("Sydney");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        myMap.addMarker(options);

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);




    }


}

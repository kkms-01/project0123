package com.example.gps_0908;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class Result extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    private SearchView mapSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        mapSearchView=findViewById(R.id.mapSearch);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location=mapSearchView.getQuery().toString() ;
                List<Address> addressList=null;

                if(location !=null){

                    Geocoder geocoder=new Geocoder(Result.this);

                    try{
                        addressList  =geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());

                   // MarkerOptions options=new MarkerOptions().position(latLng).title(location);
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(Result.this);


    }




    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(Result.this);
                }
            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

       // myMap.addMarker(new MarkerOptions().position(latLng).title(location));
       // myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

        myMap = googleMap;
    //    LatLng sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); //현위치

       LatLng sydney = new LatLng(-34, 151);  //서치,줌

        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

       // myMap.addMarker(new MarkerOptions().position(sydney).title("my location"));


        MarkerOptions options=new MarkerOptions().position(sydney).title("Sydney");
       options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        myMap.addMarker(options);

       myMap.getUiSettings().setZoomControlsEnabled(true);
      myMap.getUiSettings().setCompassEnabled(true);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied, please allow the permission ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

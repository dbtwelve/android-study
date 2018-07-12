package com.example.jupiter.mylocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SupportMapFragment mapFragment;
    GoogleMap map;
    MarkerOptions marker;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("MainActivity", "GoogleMap is Ready.");

                map = googleMap;
                map.setMyLocationEnabled(true);
            }
        });

        MapsInitializer.initialize(this);

        textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (map != null) {
            map.setMyLocationEnabled(false);
        }
    }

    public void startLocationService() {
        long minTime = 10000;
        float minDistance = 0;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        currentLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                }

        );
    }

    public void currentLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng curPoint = new LatLng(latitude,longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
        locationMarker(location);

        textView.setText("Location : " + latitude + ", " + longitude);
    }

    private void locationMarker(Location location) {
        if (marker == null) {
            marker = new MarkerOptions();
            marker.position(new LatLng(latitude , longitude));
            marker.title("My Location.\n");
            marker.snippet("GPS Location");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinred));
            map.addMarker(marker);
        } else {
            marker.position(new LatLng(latitude , longitude ));
        }
    }
}

package com.mglabs.myfirstmap;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{

    boolean mapReady;
    GoogleMap mMap;
    MapFragment mapFragment;

    MarkerOptions renton;
    MarkerOptions kirkland;

    LatLng renton_position = new LatLng(47.489805, -122.120502);
    LatLng kirkland_position = new LatLng(47.7301986, -122.1768858);
    LatLng everett_position = new LatLng(47.978784, -122.202001);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the buttons
        Button btnMap = findViewById(R.id.btnMap);
        Button btnSatellite = findViewById(R.id.btnSatellite);
        Button btnHybrid = findViewById(R.id.btnHybrid);

        //Register the onClickListener
        btnMap.setOnClickListener(this);
        btnSatellite.setOnClickListener(this);
        btnHybrid.setOnClickListener(this);

        //Markers
        renton = new MarkerOptions()
                .position(new LatLng(47.489805, -122.120502))
                .title("Renton");

        kirkland = new MarkerOptions()
                .position(new LatLng(47.7301986, -122.1768858))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.linux))
                .title("Kirkland");

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mapReady = true;

        LatLng seattle = new LatLng(47.6204, -122.2491);
        CameraPosition target = CameraPosition.builder()
                .target(seattle)
                .bearing(0)     //straight down
                .zoom(14)
                .build();

        mMap.addMarker(renton);
        mMap.addMarker(kirkland);

        //Polylines
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(renton_position)
                .add(kirkland_position)
                .add(everett_position));

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target));
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnMap:
                if(mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btnSatellite:
                if(mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btnHybrid:
                if(mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

        }
    }
}

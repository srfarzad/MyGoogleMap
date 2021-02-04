package com.androidlearn.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    double latitude,longitude;


    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        getMyLocation();


        mapFragment.getMapAsync(this);


    }

    private void getMyLocation() {
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

        if(gpsTracker.canGetLocation){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.e("","");

            Geocoder geocoder = new Geocoder(getApplicationContext());
            try {
                List<Address> addressList= geocoder.getFromLocation(latitude,longitude,1);
                Log.e("","");
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            gpsTracker.showSettingsAlert();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng home = new LatLng(35.7122618,51.4097159);
        MarkerOptions shahsavanPointer = new MarkerOptions().position(home)
                .title("Shahsavan Home").snippet("sleep")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        googleMap.addMarker(shahsavanPointer);


        LatLng seifiHome= new LatLng(35.766987472854375, 51.334182622939686);

        MarkerOptions seifiPointer = new MarkerOptions()
                .position(seifiHome).title("Seifi")
                .snippet("Studying.....")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location));

        googleMap.addMarker(seifiPointer);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(seifiHome).zoom(15).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setMyLocationEnabled(true);


        googleMap.addPolyline(new PolylineOptions().add(home,seifiHome).
                color(Color.parseColor("#ff0000")).width(5));

        googleMap.addCircle(new CircleOptions().center(home).radius(1000)
                .fillColor(Color.parseColor("#66000000"))
                .strokeColor(Color.parseColor("#ffffff")));

    }
}
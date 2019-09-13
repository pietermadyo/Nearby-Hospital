package com.pam.pieter.tugas_besar_7923;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.googlemaproute.DrawRoute;
import com.github.polok.routedrawer.RouteDrawer;
import com.github.polok.routedrawer.RouteRest;
import com.github.polok.routedrawer.model.Routes;
import com.github.polok.routedrawer.model.TravelMode;
import com.github.polok.routedrawer.parser.RouteJsonParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,DrawRoute.onDrawRoute
{

    private GoogleMap mMap;
    LatLng latLng = new LatLng(-7.779221, 110.415055);
    LatLng jih = new LatLng(-7.758618, 110.403138);
    LatLng sarjito = new LatLng(-7.7687145, 110.3712917);
    LatLng panti_rapih = new LatLng(-7.776604, 110.376326);
    LatLng batesda = new LatLng(-7.7826375, 110.374008);

    Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        go = (Button) findViewById(R.id.btnJalan);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRoute(v);
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.getUiSettings().setZoomControlsEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        DecimalFormat df = new DecimalFormat("#.##");

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(new MarkerOptions().position(latLng).title("kamu disini!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ps)).rotation(360.0f));

        double JIH = Double.parseDouble(df.format(getDistance(latLng.latitude, latLng.longitude, jih.latitude, jih.longitude)));
        double Sarjito = Double.parseDouble(df.format(getDistance(latLng.latitude, latLng.longitude, sarjito.latitude, sarjito.longitude)));
        double PantiRapih = Double.parseDouble(df.format(getDistance(latLng.latitude, latLng.longitude, panti_rapih.latitude, panti_rapih.longitude)));
        double Batesda = Double.parseDouble(df.format(getDistance(latLng.latitude, latLng.longitude, batesda.latitude, batesda.longitude)));

        double min = Math.min(JIH, Math.min(Sarjito, Math.min(PantiRapih, Batesda)));
        TextView terdekat = (TextView) findViewById(R.id.txtRumahSakit);
        terdekat.setText("Jarak Rumah sakit terdekat: " + min + " km");

        mMap.addMarker(new MarkerOptions().position(jih)
                .title("JIH ").snippet("Jarak :" +
                        df.format(getDistance(latLng.latitude, latLng.longitude, jih.latitude, jih.longitude)) + " km")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hp))
                .rotation(360.0f));

        mMap.addMarker(new MarkerOptions().position(sarjito)
                .title("Rumah Sakit Sardjito ").snippet(" Jarak : " +
                        df.format(getDistance(latLng.latitude, latLng.longitude, sarjito.latitude, sarjito.longitude)) + " km")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hp))
                .rotation(360.0f));

        mMap.addMarker(new MarkerOptions().position(panti_rapih)
                .title("Rumah Sakit Panti Rapih ").snippet("Jarak :" +
                        df.format(getDistance(latLng.latitude, latLng.longitude, panti_rapih.latitude, panti_rapih.longitude)) + " km")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hp))
                .rotation(360.0f));

        mMap.addMarker(new MarkerOptions().position(batesda)
                .title("Rumah Sakit Bethesda ").snippet("Jarak :" +
                        df.format(getDistance(latLng.latitude, latLng.longitude, batesda.latitude, batesda.longitude)) + " km")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hp))
                .rotation(360.0f));

        CameraPosition myPosition = new CameraPosition.Builder().target(latLng).zoom(17).bearing(360).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
    }


    public void onRoute(View v)
    {
        final RouteDrawer routeDrawer = new RouteDrawer.RouteDrawerBuilder(mMap)
                .withColor(Color.BLUE)
                .withWidth(8)
                .withAlpha(0.5f)
                .withMarkerIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .build();

        RouteRest routeRest = new RouteRest();

        routeRest.getJsonDirections(new LatLng(latLng.latitude, latLng.longitude),
                new LatLng(jih.latitude, jih.longitude), TravelMode.DRIVING)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Routes>() {
                    @Override
                    public Routes call(String s) {
                        return new RouteJsonParser<Routes>().parse(s, Routes.class);
                    }
                })
                .subscribe(new Action1<Routes>() {
                    @Override
                    public void call(Routes r) {
                        routeDrawer.drawPath(r);
                    }
                });
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double latA = Math.toRadians(lat1);
        double lonA = Math.toRadians(lon1);
        double latB = Math.toRadians(lat2);
        double lonB = Math.toRadians(lon2);
        double cosAng = (Math.cos(latA) * Math.cos(latB) * Math.cos(lonB-lonA)) +
                (Math.sin(latA) * Math.sin(latB));
        double ang = Math.acos(cosAng);
        double dist = ang *6371;
        return dist;
    }

    @Override
    public void afterDraw(String result) {
        Log.d("response",""+result);
    }

    public interface RouteApi {
        Observable<String> getJsonDirections(final LatLng start, final LatLng end, final TravelMode mode);
    }



}

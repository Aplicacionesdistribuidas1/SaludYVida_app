package com.chemapeva.saludyvida;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Mapsgym extends FragmentActivity implements OnMapReadyCallback {
    private Marker mar;
    GoogleMap mMap;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsgym);

        // Getting reference to SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Creating GoogleMap from SupportMapFragment

        // Creating GoogleMap from SupportMapFragment
        //  mMap = fragment.;


        // Enabling MyLocation button for the Google Map
        // mMap.setMyLocationEnabled(true);

//        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Setting OnClickEvent listener for the GoogleMap
        /*mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
                addMarker(latlng);
                sendToServer(latlng);
            }
        });*/

        // Starting locations retrieve task
        new RetrieveTask().execute();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
    }

    private void agregarubicacion(double lat, double lon) {
        LatLng coor = new LatLng(lat, lon);
//        l2.setLatitude(lat);
        //      l2.setLongitude(lon);
        CameraUpdate miubi = CameraUpdateFactory.newLatLngZoom(coor, 16);
        if (mar != null) mar.remove();
        mar = mMap.addMarker(new MarkerOptions()
                .position(coor)
                .title("Posicion actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ubi)));
        mMap.animateCamera(miubi);
    }

    private void actUbi(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarubicacion(lat, lon);
        }

    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actUbi(location);
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


    private void miUbicacion() {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actUbi(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, locListener);

    }

    // Adding marker on the GoogleMaps
    private void addMarker(LatLng latlng, String Nombre, String Direccion) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(Nombre);
        markerOptions.snippet(Direccion);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.x));
        mMap.addMarker(markerOptions);
    }

    // Invoking background thread to store the touched location in Remove MySQL server
    private void sendToServer(LatLng latlng) {
        new SaveTask().execute(latlng);
    }


    // Background thread to save the location in remove MySQL server
    private class SaveTask extends AsyncTask<LatLng, Void, Void> {
        @Override
        protected Void doInBackground(LatLng... params) {
            String Latitud = Double.toString(params[0].latitude);
            String Longitud = Double.toString(params[0].longitude);
           // String Nombre = Double.toString(params[0].Nombre);
           // String Direccion = Double.toString(params[0].Direccion);
            String strUrl = "https://bware.000webhostapp.com/save.php";
            URL url = null;
            try {
                url = new URL(strUrl);

                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        connection.getOutputStream());

                outputStreamWriter.write("Latitud=" + Latitud + "&Longitud=" + Longitud);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                InputStream iStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
                iStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    // Background task to retrieve locations from remote mysql server
    private class RetrieveTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String strUrl = "https://bware.000webhostapp.com/ListarGimnasios.php";
            URL url = null;
            StringBuffer sb = new StringBuffer();
            try {
                url = new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream iStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
                iStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }

    }

    // Background thread to parse the JSON data retrieved from MySQL server
    private class ParserTask extends AsyncTask<String, Void, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... params) {
            MarkerGynJSONParser markerParser = new MarkerGynJSONParser();
            JSONObject json = null;
            try {
                json = new JSONObject(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<HashMap<String, String>> markersList = markerParser.parse(json);
            return markersList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            for (int i = 0; i < result.size(); i++) {
                HashMap<String, String> marker = result.get(i);
                Log.d("Parque1", marker.get("Latitud") + marker.get("Longitud"));
                LatLng latlng = new LatLng(Double.parseDouble(marker.get("Latitud")), Double.parseDouble(marker.get("Longitud")));
                addMarker(latlng, marker.get("Nombre"), marker.get("Direccion"));
            }
        }
    }
}

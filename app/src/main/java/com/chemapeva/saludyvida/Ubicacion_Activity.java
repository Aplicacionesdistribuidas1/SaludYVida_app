package com.chemapeva.saludyvida;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.Modelo.Respuesta;
import com.chemapeva.saludyvida.Modelo.Ubicacion;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Ubicacion_Activity extends AppCompatActivity implements OnMapReadyCallback,  OnTaskCompleted {


    private Marker mar;
    GoogleMap mMap;
    private double lat;
    private double lon;
    Button agreCoor;
    EditText Coorx, Coory, sector, direccion;
    ArrayList<Ubicacion> l = new ArrayList<Ubicacion>();
    String Nombre,fnacimiento,Cedula,Correo,password,username,dir,lati,longi,sec;
    private static final int SOLICITUD_IDCLIENTE = 1;
    private static final int SOLICITUD_GUARDAR_CLIENTE = 2;
    private boolean estadofb=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        // Getting reference to SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Coorx = (EditText) findViewById(R.id.txtCoorx);
        Coory = (EditText) findViewById(R.id.txtCoory);
        sector = (EditText) findViewById(R.id.txtSector);
        direccion = (EditText) findViewById(R.id.txtDireccion);
        agreCoor = (Button) findViewById(R.id.btnAgregarUbicacion);
        agreCoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 confirmarGuardarCliente();
            }
        });
        // addMarker();
        // Creating GoogleMap from SupportMapFragment

        // Creating GoogleMap from SupportMapFragment
        //mMap = fragment.;


        // Enabling MyLocation button for the Google Map
        // mMap.setMyLocationEnabled(true);

        //mMap.getUiSettings().setMyLocationButtonEnabled(true);

        /* Setting OnClickEvent listener for the GoogleMap
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng ) {
               // Coorx.setText(latlng.latitude+"");
                //Coory.setText(latlng.longitude+"");
            }
        });*/

        // Starting locations retrieve task
        //new RetrieveTask().execute();

    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud) {
            case SOLICITUD_GUARDAR_CLIENTE:
                if (result != null) {
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(this, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            Intent i=new Intent(new Intent(Ubicacion_Activity.this,MainActivity.class));
                            startActivity(i);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                } else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegi:
                confirmarGuardarCliente();
                break;
            default:
                break;
        }
    }
*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
    }

    private void agregarubicacion(double lat, double lon) {
        LatLng coor = new LatLng(lat, lon);
        // Coorx.setText(lat+"");
        //Coory.setText(lon+"");
//        l2.setLatitude(lat);
        //      l2.setLongitude(lon);
        CameraUpdate miubi = CameraUpdateFactory.newLatLngZoom(coor, 16);
        if (mar != null) mar.remove();
        mar = mMap.addMarker(new MarkerOptions()
                .position(coor)
                .title("Posicion actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ubi))
                .draggable(true));
        mMap.animateCamera(miubi);
    }

    private void actUbi(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            Coorx.setText(lat + "");
            Coory.setText(lon + "");
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
    private void confirmarGuardarCliente() {
        String pregunta = "Esto seguro de realizar guardar el cliente";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarCliente();
                               // consultaIdEmpleado();
                                //guardarUbicacion();
                            }
                        })
                .show();
    }

    private void guardarCliente(){
        int id;
        try {
            //  id = Integer.parseInt(((EditText) findViewById(R.id.txtIdEmp)).getText().toString());
        }catch (Exception e){
            Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            return;
        }

        try {
            String URL = Util.URL_SRV + "clientes/guardar";
            ClienteRest clienteRest = new ClienteRest(this);

            Nombre = getIntent().getExtras().getString("nombre");

            Cedula = getIntent().getExtras().getString("cedula");

            Correo = getIntent().getExtras().getString("correo");

            username = getIntent().getExtras().getString("username");

            password = getIntent().getExtras().getString("password");

            fnacimiento = getIntent().getExtras().getString("fnacimiento");

            Log.i("Ubicacion_Activity","Fecha de nacimiento: "+ fnacimiento);
            lati = Coorx.getText().toString();
            longi = Coory.getText().toString();
            sec = sector.getText().toString();
            Log.d("Ubicacion_Activity","Sector cliente"+sec);
            dir = direccion.getText().toString();
            Ubicacion u=new Ubicacion();
            //u.setCodigo("3");
            u.setDireccion(dir);
            u.setSector(sec);
            u.setVectorx(Double.parseDouble(lati));
            u.setVectory(Double.parseDouble(longi));
            l.add(u);

            Cliente cli = new Cliente();
            //cli.setCodigo(6);
            cli.setNombres(Nombre);
            cli.setCedula(Cedula);
            cli.setUsuario(username);
            cli.setContrasena(password);
            cli.setEmail(Correo);
            cli.setFechaNacimiento(fnacimiento);
            cli.setUbicacion(l);
            clienteRest.doPost(URL, cli, SOLICITUD_GUARDAR_CLIENTE, true);
            estadofb=true;
            guardarEstadofb();
            Intent i = new Intent(Ubicacion_Activity.this, Actividad_principal.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //cierra cola de actividades
            startActivity(i);
            finish();
        }catch(Exception e){
           Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    public void guardarEstadofb() {
        SharedPreferences preferences = getSharedPreferences("Registrofb", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("iniciado", estadofb).apply();

    }
    /*
    private void guardarUbicacion(){
        //int id;
        try {
          //  id = Integer.parseInt(((EditText) findViewById(R.id.txtCodigo)).getText().toString());
        }catch (Exception e){
            Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            return;
        }
        try {
            String URL = Util.URL_SRV + "ubicaciones/guardar";
            ClienteRest clienteRest = new ClienteRest(this);
            Ubicacion u=new Ubicacion();
            //u.setCodigo("1");
            u.setDireccion(dir);
            u.setSector(sec);
            Log.d("Ubicacion_Activity","Sector ubi"+sec);
            Log.d("Ubicacion_Activity","lati: "+ lati);
            u.setVectorx(Double.parseDouble(lati));
            u.setVectory(Double.parseDouble(longi));

            Nombre = getIntent().getExtras().getString("Nombre");

            Cedula = getIntent().getExtras().getString("Cedula");

            Correo = getIntent().getExtras().getString("Correo");

            username = getIntent().getExtras().getString("username");

            password = getIntent().getExtras().getString("password");

            fnacimiento = getIntent().getExtras().getString("fnacimiento");


            Cliente cli = new Cliente();

            cli.setCodigo(17);
            cli.setNombres(Nombre);
            cli.setCedula(Cedula);
            cli.setUsuario(username);
            cli.setContrasena(password);
            cli.setEmail(Correo);
            cli.setFechaNacimiento(fnacimiento);


            u.setCliente(cli);


            clienteRest.doPost(URL, u, SOLICITUD_GUARDAR_CLIENTE, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
*/
/*
    // Adding marker on the GoogleMaps
    private void addMarker(LatLng latlng, String Nombre, String Direccion) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(Nombre);
        markerOptions.snippet(Direccion);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.y));
        mMap.addMarker(markerOptions);

    }*/



    }

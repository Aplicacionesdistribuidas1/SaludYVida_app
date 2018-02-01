package com.chemapeva.saludyvida;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;




public class Actividad_principal extends Activity {
    private Button mRegister;
    private TextView log;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private boolean estado = false;
    private boolean estadofb = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_principal);
        if(obtenerEstado()){
            Intent i = new Intent(this, activity_seleccion.class);
        //    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        callbackManager = CallbackManager.Factory.create();
        log = (TextView) findViewById(R.id.txtLogin);
        Resources res = getResources();
        CharSequence styledText = res.getText(R.string.text_subrayado);
        log.setText(styledText, TextView.BufferType.SPANNABLE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            }
        }
        /*
       sesionActiva = obtieneValorSesion(getApplicationContext());

//Valida si muestra pantalla de Login o MainActivity1 desde una Splash Activity.
        if (sesionActiva) {
            Intent myIntent = new Intent(Actividad_principal.this, Login.class);
            startActivity(myIntent);
            //Abre pantalla principal.

        } else {
            //Abre pantalla para autenticarse.
            setContentView(R.layout.activity_principal);
        }*/
        mRegister = (Button) findViewById(R.id.btnRe);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Actividad_principal.this, ClienteActivity.class);
                startActivity(i);
                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Actividad_principal.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        // recuperar();
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                estado = true;
                Log.d("Actividad_principal","valor estado "+estado);
                goMainScreen();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goMainScreen() {
        guardarEstado();
        if(obtenerEstadofb()){
            Intent i = new Intent(this, activity_seleccion.class);
            //    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(this, ClienteActivity.class);
            //    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }

    }
    public void guardarEstado() {
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("iniciado", estado).apply();

    }
    public boolean obtenerEstado(){
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        return preferences.getBoolean("iniciado",false);
    }
    /*
    public void guardarEstadofb() {
        SharedPreferences preferences = getSharedPreferences("Registrofb", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("iniciado", estadofb).apply();

    }*/
    public boolean obtenerEstadofb(){
        SharedPreferences preferences = getSharedPreferences("Registrofb", Context.MODE_PRIVATE);
        return preferences.getBoolean("iniciado",false);
    }
}





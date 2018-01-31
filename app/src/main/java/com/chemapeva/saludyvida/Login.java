package com.chemapeva.saludyvida;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class Login extends AppCompatActivity {
    private static final int NOTIF_ALERTA_ID = 1;
    ImageButton btnGym;
    ImageButton btnPa;
    ImageButton btnCo;
    ImageButton btnNu;
    ImageButton btnCh;

    private boolean estado=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //  us=getIntent().getExtras().getString("username").toString();
        //Log.d("Usuario",us);
        addListenerOnButton();
        Actividad_principal a= new Actividad_principal();
        a.finish();

    }

    public void addListenerOnButton() {

        btnGym = (ImageButton) findViewById(R.id.btnGym1);
        btnGym.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent in = new Intent(Login.this, Mapsgym.class);
                                          startActivity(in);


                                      }
                                  }
        );
        btnNu = (ImageButton) findViewById(R.id.btnnu);
        btnNu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, MapsDoctors.class);
                startActivity(in);
            }

        });


    }


}

package com.chemapeva.saludyvida;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

public class activity_seleccion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView name, email;
    private ImageView imagen;
    private String username, correo,codigo,nombre;
    private boolean estado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ValidarFacebook();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_seleccion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.mnu_comp) {
            // Handle the camera action
            Intent i = new Intent(activity_seleccion.this, Mostrar_Menu_Activity.class);
            i.putExtra("username", username);
            i.putExtra("correo",correo);
            i.putExtra("codigo",codigo);
            startActivity(i);
        } else if (id == R.id.mnu_sitios) {
            Intent i = new Intent(activity_seleccion.this, Login.class);
            startActivity(i);
        } else if (id == R.id.mnu_Editar) {
            Intent i = new Intent(activity_seleccion.this, Actividad_EditarCliente.class);
            i.putExtra("username", username);
            i.putExtra("correo",correo);

            startActivity(i);

        } else if (id == R.id.mnuChat) {
            Intent i=new Intent(activity_seleccion.this,Chat.class);
            startActivity(i);
            i.putExtra("username", username);

        } else if (id == R.id.mnuListarfacturas) {

        } else if (id == R.id.mnuCerrar) {

            if (AccessToken.getCurrentAccessToken() != null) {
                //goLoginScreen();
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("¿Cerrar la sesión de SaludYVida?");
                dialogo1.setMessage("¿Quieres salir de la cuenta?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        guardarEstado();
                        Intent i = new Intent(activity_seleccion.this, Actividad_principal.class);
                        startActivity(i);
                        finish();
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogo1.show();
            } else {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("¿Cerrar la sesión de SaludYVida?");
                dialogo1.setMessage("¿Quieres salir de la cuenta?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aceptar();
                        guardarEstado();
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelar();
                    }
                });
                dialogo1.show();

            }
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }



    public void aceptar() {

        goLoginScreen();

    }

    public void cancelar() {

    }

    public void ValidarFacebook(){
        name=(TextView)findViewById(R.id.txtNombrePerfil);
        email=(TextView)findViewById(R.id.txtCorreoPerfil);
        imagen=(ImageView) findViewById(R.id.imgPerfil);
        // guardarEstado();
        if (AccessToken.getCurrentAccessToken() == null) {
           // us = getIntent().getExtras().getString("Usuario");
           // Log.d("Usuario",us);
        }
        if (Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
            username=profile.getFirstName() + " " + profile.getLastName();
            correo=profile.getName();
           // codigo=getIntent().getExtras().getString("codigo");
            //nombre=getIntent().getExtras().getString("nombre");
//            name.setText(username);
  //          email.setText(correo);
    //        imagen.setImageURI(profile.getProfilePictureUri(100,100));
            Log.d("activity_seleccion","ha ingresado"+username+" "+correo);
        } else {
            Log.d("activity_seleccion","no ingresa x fb");
            username=getIntent().getExtras().getString("usuario");
            correo=getIntent().getExtras().getString("correo");
            codigo=getIntent().getExtras().getString("codigo");
            nombre=getIntent().getExtras().getString("nombres");
      //      name.setText(username);
        }
    }
    private void goLoginScreen() {
        Intent i = new Intent(activity_seleccion.this, Actividad_principal.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
    public void guardarEstado() {
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("iniciado", estado).apply();

    }

}

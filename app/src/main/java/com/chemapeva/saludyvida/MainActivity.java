package com.chemapeva.saludyvida;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.Modelo.Empleado;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity implements OnTaskCompleted {
    private EditText user, pass;
    private Button mSubmit;
    private ProgressDialog pDialog;
    private TextView log;
    private String username;
    private String password;
    private String cod,co,us,no;
    private boolean estado=false;
    private static final int SOLICITUD_EMPLEADO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = (TextView) findViewById(R.id.txtOl);
        Resources res = getResources();
        CharSequence styledText = res.getText(R.string.olv_contrasena);
        log.setText(styledText, TextView.BufferType.SPANNABLE);

        // setup input fields
        user = (EditText) findViewById(R.id.txtUs1);
        pass =(EditText) findViewById(R.id.txtContra);
        log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecuperacionContra.class);
                startActivity(i);
            }
        });
        // setup buttons
        mSubmit = (Button) findViewById(R.id.btnIn);
        // register listeners
       // int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


        mSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                username = user.getText().toString();
                password = pass.getText().toString();
                if(username.equalsIgnoreCase("admin")&&password.equalsIgnoreCase("admin")){
                    Intent i=new Intent(MainActivity.this,Administrador.class);
                    startActivity(i);
                }
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nombre de usuario o contrasena incorrecta", Toast.LENGTH_LONG).show();

                } else {

                consultaEmpleado(username);

                }
            }
        });
    }

    protected void consultaEmpleado(String user) {
        try {
            String URL = Util.URL_SRV + "clientes/login";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?user=" + user, SOLICITUD_EMPLEADO, true);
        } catch (Exception e) {
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    private void goMainScreen(){

        Intent i = new Intent(MainActivity.this,activity_seleccion.class);
        i.putExtra("codigo",cod+"");
        i.putExtra("nombres",no+"");
        i.putExtra("usuario",us+"");
        i.putExtra("correo",co+"");

        //i.putExtra("",)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
        Log.d("Peche","Principal");
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity", "" + result);
        switch (idSolicitud){
            case SOLICITUD_EMPLEADO:
                if(result!=null){
                    try {
                        Cliente cli =  ClienteRest.getResult(result, Cliente.class);
                       // Util.showMensaje(this, cli.toString());
                        if(cli.getUsuario().equalsIgnoreCase(username)&&cli.getContrasena().equalsIgnoreCase(password)){
                            cod=cli.getCodigo().toString();
                            no=cli.getNombres();
                            co=cli.getEmail();
                            us=cli.getUsuario();
                            estado=true;
                            //guardarEstado();
                            goMainScreen();

                        }else{
                            Toast.makeText(MainActivity.this, "Nombre de usuario o contrasena incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }
    }
    public void guardarEstado() {
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("iniciado", estado).apply();

    }
}




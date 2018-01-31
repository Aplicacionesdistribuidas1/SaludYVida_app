package com.chemapeva.saludyvida;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.Modelo.Empleado;
import com.chemapeva.saludyvida.Modelo.Respuesta;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class ClienteActivity extends AppCompatActivity implements OnTaskCompleted {

    private Button sig;
    private String no,ap,co,us,con,re,fn,pregunta1,pregunta2;
    private Boolean n,a,c,u,contra,recontra,igua;
    private EditText mNombre, mFechaNacimiento, mCedula, mCorreo, mUsuario, mContrasena,mreContrasena,pre1,pre2;
    private static final int SOLICITUD_EMPLEADO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        sig=(Button)findViewById(R.id.btnRegi);
        mNombre = (EditText) findViewById(R.id.txtNombres);
        mCedula = (EditText) findViewById(R.id.txtCedulaCli);
        mCorreo = (EditText) findViewById(R.id.txtCorreo);
        mUsuario = (EditText) findViewById(R.id.txtUsuario);
        mContrasena = (EditText) findViewById(R.id.txtContrasena);
        mreContrasena = (EditText) findViewById(R.id.txtRecon);
        mFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimiento);
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                igua=false;
                n=false;
                a=false;
                c=false;
                u=false;
                contra=false;
                recontra=false;
                String Nombre = mNombre.getText().toString();
                String Cedula = mCedula.getText().toString();
                String Correo = mCorreo.getText().toString();
                String password = mContrasena.getText().toString();
                String username = mUsuario.getText().toString();
                String fnacimiento=mCedula.getText().toString();

                no = mNombre.getText().toString().trim();
                ap = mCedula.getText().toString().trim();
                co = mCorreo.getText().toString().trim();
                us = mUsuario.getText().toString().trim();
                con = mContrasena.getText().toString().trim();
                re = mreContrasena.getText().toString().trim();
                fn= mFechaNacimiento.getText().toString().trim();


                if(no.isEmpty() || no.length() == 0 || no.equals("") || no == null)
                {
                    //EditText is empty
                    EditText textNo = (EditText) findViewById(R.id.txtNombres);
                    textNo.setHintTextColor(RED);

                }else{
                    n=true;
                    EditText textNo = (EditText) findViewById(R.id.txtNombres);
                    textNo.setHintTextColor(WHITE);

                }
                if(ap.isEmpty() || ap.length() == 0 || ap.equals("") || ap == null)
                {
                    //EditText is empty
                    EditText textAp = (EditText) findViewById(R.id.txtCedulaCli);
                    textAp.setHintTextColor(RED);

                }else{
                    a=true;
                    EditText textAp = (EditText) findViewById(R.id.txtCedulaCli);
                    textAp.setHintTextColor(WHITE);


                }
                c=isEmailValid(co);
                if(co.isEmpty() || co.length() == 0 || co.equals("") || co == null||c==false)
                {
                    //EditText is empty
                    EditText textCo = (EditText) findViewById(R.id.txtCorreo);
                    textCo.setHintTextColor(RED);
                    textCo.setTextColor(RED);
                    textCo.setError("E-mail Invalido");

                }else{

                    EditText textCo = (EditText) findViewById(R.id.txtCorreo);
                    textCo.setHintTextColor(WHITE);

                }

                if(us.isEmpty() || us.length() == 0 || us.equals("") || us == null)
                {
                    //EditText is empty
                    EditText textUs = (EditText) findViewById(R.id.txtUsuario);
                    textUs.setHintTextColor(RED);

                }else{
                    u=true;
                    EditText textUs = (EditText) findViewById(R.id.txtUsuario);
                    textUs.setHintTextColor(WHITE);

                }
                if(con.isEmpty() || con.length() == 0 || con.equals("") || con == null|| con.length()<5)
                {
                    //EditText is empty
                    EditText textCon = (EditText) findViewById(R.id.txtContrasena);
                    textCon.setHintTextColor(RED);
                    mContrasena.setError("La contrasena debe tener minimo 5 caracteres y debe contener al menos un numero");


                }else {
                    contra=true;
                    EditText textCon = (EditText) findViewById(R.id.txtContrasena);
                    textCon.setHintTextColor(WHITE);

                }


                if (re.isEmpty() || re.length() == 0 || re.equals("") || re == null ) {
                    //EditText is empty
                    EditText textRecon = (EditText) findViewById(R.id.txtRecon);
                    textRecon.setHintTextColor(RED);

                } else {
                    recontra=true;
                    EditText textRec = (EditText) findViewById(R.id.txtRecon);
                    textRec.setHintTextColor(WHITE);


                }
                if(n && a && c && u && contra && recontra) {
                    if (con.equals(re)) {
                        igua=true;
                    } else {
                        Toast.makeText(ClienteActivity.this, "Las contrasenas no coinciden, Intentelo de nuevo", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ClienteActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
                }
                if(igua&& n && a && c && u && contra && recontra){
                    Intent i =new Intent(ClienteActivity.this,Ubicacion_Activity.class);
                    i.putExtra("nombre",Nombre);
                    i.putExtra("cedula",Cedula);
                    i.putExtra("correo", Correo);
                    i.putExtra("username", username);
                    i.putExtra("password",password);
                    i.putExtra("fnacimiento",fnacimiento);
                    startActivity(i);

                }else{

                }


            }

        });
    }


/*
    private void DatosCliente(){
        int id;
        try {
          //  id = Integer.parseInt(((EditText) findViewById(R.id.txtIdEmp)).getText().toString());
        }catch (Exception e){
            Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            return;
        }
        String Nombre = ((EditText) findViewById(R.id.txtNombres)).getText().toString();
        String username = ((EditText) findViewById(R.id.txtUsuario)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtContrasena)).getText().toString();
        String fnacimiento =  ((EditText) findViewById(R.id.txtFechaNacimiento)).getText().toString();
        String Cedula = ((EditText) findViewById(R.id.txtCedulaCli)).getText().toString();
        String Correo = ((EditText) findViewById(R.id.txtCorreo)).getText().toString();

        Intent i =new Intent(ClienteActivity.this,Ubicacion_Activity.class);
        i.putExtra("Nombre",Nombre);
        i.putExtra("Cedula",Cedula);
        i.putExtra("Correo", Correo);
        i.putExtra("username", username);
        i.putExtra("password",password);
        i.putExtra("fnacimiento",fnacimiento);
        startActivity(i);

    }*/

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
    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
    Log.i("MainActivity", "" + result);
    switch (idSolicitud){
        case SOLICITUD_EMPLEADO:
            if(result!=null){
                try {
                    Cliente cli =  ClienteRest.getResult(result, Cliente.class);
                    //Util.showMensaje(this, cli.toString());
                    if(cli.getUsuario()!=null){
                        EditText textUs = (EditText) findViewById(R.id.txtUsuario);
                        textUs.setHintTextColor(RED);
                        mUsuario.setError("El usuario seleccionado ya existe");
                    }else{
                        EditText textUs = (EditText) findViewById(R.id.txtUsuario);
                        textUs.setHintTextColor(WHITE);
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
    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;


        }
        return isValid;
    }

}

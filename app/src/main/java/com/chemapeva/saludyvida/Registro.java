package com.chemapeva.saludyvida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class Registro extends Activity {
    private EditText mNombre, mFechaNacimiento, mCedula, mCorreo, mUsuario, mContrasena,mreContrasena,pre1,pre2;
    private Button btnRegis;
    String no,ap,co,us,con,re,fn,pregunta1,pregunta2;
    Boolean n,a,c,u,contra,recontra,igua;
    //ids

    public Registro() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mNombre = (EditText) findViewById(R.id.txtNombres);
        mCedula = (EditText) findViewById(R.id.txtCedulaCli);
        mCorreo = (EditText) findViewById(R.id.txtCorreo);
        mUsuario = (EditText) findViewById(R.id.txtUsuario);
        mContrasena = (EditText) findViewById(R.id.txtContrasena);
        mreContrasena = (EditText) findViewById(R.id.txtRecon);
        mFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimiento);
        btnRegis = (Button) findViewById(R.id.btnRegi);


        btnRegis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Toast.makeText(Registro.this, "Las contrasenas no coinciden, Intentelo de nuevo", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(Registro.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }
        if(igua&& n && a && c && u && contra && recontra){
            Intent i =new Intent(Registro.this,Ubicacion_Activity.class);
            i.putExtra("Nombre",Nombre);
            i.putExtra("Cedula",Cedula);
            i.putExtra("Correo", Correo);
            i.putExtra("username", username);
            i.putExtra("password",password);
            i.putExtra("fnacimiento",fnacimiento);
            startActivity(i);

        }else{

        }


    }

        });
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


package com.chemapeva.saludyvida;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Editar extends AppCompatActivity {


    private String user,codigo,nombre,email,pass;
    private EditText u,n,e,p,rp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        n=(EditText)findViewById(R.id.txtName);
        u=(EditText)findViewById(R.id.editTextusuario);
        e=(EditText)findViewById(R.id.editTextCorreo);
        p=(EditText)findViewById(R.id.editTextcontra);
        rp=(EditText)findViewById(R.id.editTextrepContra);
        user=getIntent().getExtras().getString("usuario");;
        codigo=getIntent().getExtras().getString("codigo");;
        nombre=getIntent().getExtras().getString("nombres");
        email=getIntent().getExtras().getString("correo");
        n.setText(nombre);
        u.setText(user);
        e.setText(email);
        pass=p.getText().toString();
        user=u.getText().toString();
        nombre=n.getText().toString();
        email=e.getText().toString();
    }
}

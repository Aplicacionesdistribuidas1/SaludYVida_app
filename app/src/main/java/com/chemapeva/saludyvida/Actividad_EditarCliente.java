package com.chemapeva.saludyvida;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

public class Actividad_EditarCliente extends Activity  {
    private Button editar;
    private TextView edi,correo,nom;
    private String email,user,codigo,nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        edi=(TextView) findViewById(R.id.txtEditar);
        correo=(TextView)findViewById(R.id.txtCor);
        nom=(TextView)findViewById(R.id.txtNombresE);
        user=getIntent().getExtras().getString("usuario");;
        codigo=getIntent().getExtras().getString("codigo");;
        nombre=getIntent().getExtras().getString("nombres");
        email=getIntent().getExtras().getString("correo");
        correo.setText(email);
        nom.setText(nombre);
        Resources res = getResources();
        CharSequence styledText = res.getText(R.string.editar);
        edi.setText(styledText, TextView.BufferType.SPANNABLE);
        edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Actividad_EditarCliente.this, Editar.class);
                i.putExtra("usuario",user);
                i.putExtra("correo",email);
                i.putExtra("nombres",nombre);
                startActivity(i);
            }
        });

    }



}

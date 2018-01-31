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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;
import com.facebook.Profile;

public class Actividad_EditarCliente extends Activity  {

    private TextView edi,correo,nom,usuario;
    private String email,user,codig,nombre,us;
    private ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        edi=(TextView) findViewById(R.id.txtEditar);
        correo=(TextView)findViewById(R.id.txtCor);
        nom=(TextView)findViewById(R.id.txtNombresE);
        usuario=(TextView)findViewById(R.id.txtUsE);
        imagen=(ImageView)findViewById(R.id.imgPerf);
        ValidarFb();
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

    public void ValidarFb(){
        if (Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
            user=profile.getFirstName() + " " + profile.getLastName();
            email=profile.getName();
            // codigo=getIntent().getExtras().getString("codigo");
            //nombre=getIntent().getExtras().getString("nombre");
            nom.setText(user);
            correo.setText(email);
            imagen.setImageURI(profile.getProfilePictureUri(100,100));
        } else {
            Log.d("activity_seleccion","no ingresa x fb");
            user=getIntent().getExtras().getString("usuario");
            Log.d("Actividad_EditarCliente",user);
            email=getIntent().getExtras().getString("correo");
            codig=getIntent().getExtras().getString("codigo");
            nombre=getIntent().getExtras().getString("nombres");
            nom.setText(nombre);
            correo.setText(email);
            usuario.setText(user);

        }
    }
}



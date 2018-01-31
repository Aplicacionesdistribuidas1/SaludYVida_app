package com.chemapeva.saludyvida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmarCompraActivity extends AppCompatActivity {
    private Double subtotal,total,iva;
    private Button regresar;
    private TextView sub,tot,iv;
    private ArrayList<com.chemapeva.saludyvida.Modelo.Menu> men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_compra);
        total = Double.parseDouble(getIntent().getExtras().getString("total"));

        subtotal = Double.parseDouble(getIntent().getExtras().getString("subtotal"));

        iva =Double.parseDouble(getIntent().getExtras().getString("iva"));

        men = (ArrayList<com.chemapeva.saludyvida.Modelo.Menu>) getIntent().getSerializableExtra("miLista");

        regresar=(Button) findViewById(R.id.btnRegresarPan);

        sub=(TextView)findViewById(R.id.txtISub);

        tot=(TextView)findViewById(R.id.txtInTotal);

        iv=(TextView)findViewById(R.id.txtInIva);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ConfirmarCompraActivity.this,activity_seleccion.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void listarCompra(){


    }
}

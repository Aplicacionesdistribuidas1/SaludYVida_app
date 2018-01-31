package com.chemapeva.saludyvida;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MostrarCarritoActivity extends AppCompatActivity {
    private ListView listview;
    private TextView Total;
    private TextView Subtotal;
    private TextView Iva;
    private Button realizarp;
    ArrayList titulo = new ArrayList();
    ArrayList imagen = new ArrayList();
    ArrayList codigo = new ArrayList();
    ArrayList tipo = new ArrayList();
    ArrayList ingredientes = new ArrayList();
    ArrayList cantidad = new ArrayList();
    ArrayList precio = new ArrayList();
    ArrayList<com.chemapeva.saludyvida.Modelo.Menu> m = new ArrayList<>();
    private String username,correo,co;
    private Double tot = 0.00;
    private Double sub = 0.00;
    private Double iv = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_carrito);
        listview = (ListView) findViewById(R.id.listviewimagenca);
        Subtotal = (TextView) findViewById(R.id.txtSubtotalCarrito);
        Total = (TextView) findViewById(R.id.txtTotalCarrito);
        Iva = (TextView) findViewById(R.id.txtIvaCarrito);
        descargarImagen();
        username=getIntent().getExtras().getString("usuario");
        correo=getIntent().getExtras().getString("correo");
        co=getIntent().getExtras().getString("codigo");
        realizarp=(Button) findViewById(R.id.btnRealizarPedido);
        m = (ArrayList<com.chemapeva.saludyvida.Modelo.Menu>) getIntent().getSerializableExtra("miLista");
        for(int i=0;i<m.size();i++){
        tot+=m.get(i).getPrecio();
        }
        iv=tot*0.12;
        sub=tot-iv;
        Total.setText("$: "+tot);
        Subtotal.setText("$: "+sub);
        Iva.setText("$: "+ iv);

        if (m.isEmpty()) {
            mostrarMensaje();
        } else {
            obtener_datos();
        }

//        Log.d("MostrarCarritoActivity", "lista" + m.get(1).getCantidad());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mostrar_carrito, menu);
        return true;
    }

    private void descargarImagen() {
        titulo.clear();
        imagen.clear();
        codigo.clear();
        tipo.clear();
        ingredientes.clear();
        cantidad.clear();
        precio.clear();
        final ProgressDialog progreesdialog = new ProgressDialog(MostrarCarritoActivity.this);
        //progreesdialog.setMessage("CargandoDatos...");
        //progreesdialog.show();

    }

    public void obtener_datos() {

        try {

            for (int i = 0; i < m.size(); i++) {
                System.out.print(m.get(i).getImagen().toString());
                titulo.add(m.get(i).getNombre());
                precio.add(m.get(i).getPrecio());
                imagen.add(m.get(i).getImagen());
                Log.d("MostrarCarritoActivity", "Datos" + m.get(i).getImagen().toString());
                codigo.add(m.get(i).getCodigo());
                tipo.add(m.get(i).getTipo());
                ingredientes.add(m.get(i).getIngredientes());
                cantidad.add(m.get(i).getCantidad());
            }
            listview.setAdapter(new ImagenAdapter(getApplicationContext()));
            //listview.setAdapter(new ImagenAdapter(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje() {
        String pregunta = "Su carrito esta vacio. ¿Desea volver a ver los menus disponibles?";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_informacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MostrarCarritoActivity.this, Mostrar_Menu_Activity.class);
                                startActivity(i);
                            }
                        })
                .show();
    }
    private void confirmarCompra() {
        String pregunta = "¿Esta seguro de realizar su pedido?";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_pedido))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MostrarCarritoActivity.this, ConfirmarCompraActivity.class);
                                i.putExtra("miLista", m);
                                i.putExtra("total",tot+"");
                                i.putExtra("subtotal",sub+"");
                                i.putExtra("iva",iv+"");
                                i.putExtra("usuario",username+"");
                                i.putExtra("correo",correo+"");
                                i.putExtra("codigo",co+"");
                                startActivity(i);
                                finish();
                            }
                        })
                .show();
    }

    private class ImagenAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        private Button SumarCantidad;
        private Button RestarCantidad;
        TextView tvPrecio;
        TextView tvCantidad;
        TextView tvTotalca;


        public ImagenAdapter(Context applicationContext) {
            ctx = applicationContext;
            //    this.pro=pr;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagen.size();
        }

        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Log.d("MostrarCarritoActivity","AkI STY");
            View view = convertView;

            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) ctx
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_mostrar_carrito_item, parent, false);
            }
            //ViewGroup view =(ViewGroup) layoutInflater.inflate(R.layout.activity_mostrar_carrito_item,parent,false);
            smartImageView = (SmartImageView) view.findViewById(R.id.imca);
            tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);
            tvCantidad = (TextView) view.findViewById(R.id.tvCantidadca);
            tvTotalca = (TextView) view.findViewById(R.id.txtTotalca);
            SumarCantidad = (Button) view.findViewById(R.id.btnSumar);
            RestarCantidad = (Button) view.findViewById(R.id.btnRestar);

            String urlfinal = "http://bware.000webhostapp.com/ensaladas/" + imagen.get(position).toString();
            ;
            Rect rect = new Rect(smartImageView.getLeft(), smartImageView.getTop(), smartImageView.getRight(), smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal, rect);
            tvPrecio.setText("$" + m.get(position).getPrecio());
            tvCantidad.setText(m.get(position).getCantidad()+"");

            SumarCantidad.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    aumentarCantidad(position);
                    Log.d("MostrarCarritoActivity", "codigo carrito:" + position);
                }
            });
            RestarCantidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disminuirCantidad(position);
                    Log.d("MostrarCarritoActivity", "codigo carrito:" + position);
                }
            });
            realizarp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmarCompra();

                }
            });
            return view;
        }

        public void aumentarCantidad(int i) {

            // for(int i=0;i<m.size();i++){
            // if(idmenu==m.get(idmenu).getCodigo()){
            // tot+=m.get(i).getPrecio();
            m.get(i).setCantidad(m.get(i).getCantidad() + 1);
            Log.d("MostrarCarritoActivity","posicion en el array: " + i +"Cantidad actual: "+ m.get(i).getCantidad());
           // Log.d("MostrarCarritoActivity", "posicion en el array: " + i);
            tvCantidad.setText(m.get(i).getCantidad() + "");
            tvPrecio.setText("$:" + m.get(i).getPrecio());
            notifyDataSetChanged();
            tot += (m.get(i).getPrecio());
            iv = tot * 0.12;
            sub = tot - iv;
            Subtotal.setText("$: " + sub);
            Total.setText("$: " + tot);
            Iva.setText("$:" + iv);
        }

        public void disminuirCantidad(int i) {
            if (m.get(i).getCantidad() > 0) {
                tot-= m.get(i).getPrecio();
                Log.d("MostrarCarritoActivity","Cantidad actual: "+ m.get(i).getCantidad());
                m.get(i).setCantidad(m.get(i).getCantidad() - 1);
                if(m.get(i).getCantidad()==0){
                    m.remove(i);
                    notifyDataSetChanged();

                }
                tvCantidad.setText(m.get(i).getCantidad() + "");
                notifyDataSetChanged();
            }
        }
    }
}

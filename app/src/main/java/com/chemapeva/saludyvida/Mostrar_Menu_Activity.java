package com.chemapeva.saludyvida;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chemapeva.saludyvida.Modelo.Empleado;
import com.chemapeva.saludyvida.Modelo.MenuTemp;
import com.chemapeva.saludyvida.Modelo.Respuesta;
import com.chemapeva.saludyvida.Modelo.Ubicacion;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;

import cz.msebera.android.httpclient.Header;
public class Mostrar_Menu_Activity extends AppCompatActivity implements OnTaskCompleted {
    private ListView listview;
    ArrayList titulo = new ArrayList();
    ArrayList precio = new ArrayList();
    ArrayList imagen = new ArrayList();
    ArrayList codigo= new ArrayList();
    ArrayList tipo=new ArrayList();
    ArrayList ingredientes=new ArrayList();
    ArrayList cantidad=new ArrayList();
    //private static final int SOLICITUD_IDCLIENTE = 1;
    ArrayList<com.chemapeva.saludyvida.Modelo.Menu>men=new ArrayList<>();
    private String username,correo,co;
    private static final int SOLICITUD_GUARDAR_CLIENTE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__menu_);
        listview = (ListView) findViewById(R.id.listviewimagen);
        descargarImagen();
        username=getIntent().getExtras().getString("usuario");
        correo=getIntent().getExtras().getString("correo");
        co=getIntent().getExtras().getString("codigo");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_carrito, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mnu_carrito:
               // if (AccessToken.getCurrentAccessToken() != null) {
                    irCarrito();
                //}
        }
        return true;
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud) {
            case SOLICITUD_GUARDAR_CLIENTE:
                if (result != null) {
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(this, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            Toast.makeText(Mostrar_Menu_Activity.this,"Se a agregado el producto al carrito",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                } else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }
    }
    private void descargarImagen() {
        titulo.clear();
        precio.clear();
        imagen.clear();
        codigo.clear();
        tipo.clear();
        ingredientes.clear();
        final ProgressDialog progreesdialog = new ProgressDialog(Mostrar_Menu_Activity.this);
        progreesdialog.setMessage("CargandoDatos...");
        progreesdialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://192.168.0.101:8080/marketbit1/srv/menus/menus", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progreesdialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            titulo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            precio.add(jsonArray.getJSONObject(i).getString("precio"));
                            imagen.add(jsonArray.getJSONObject(i).getString("imagen"));
                            codigo.add(jsonArray.getJSONObject(i).getString("codigo"));
                            tipo.add(jsonArray.getJSONObject(i).getString("tipo"));
                            ingredientes.add(jsonArray.getJSONObject(i).getString("ingredientes"));
                            cantidad.add(jsonArray.getJSONObject(i).getString("cantidad"));
                        }
                        listview.setAdapter(new ImagenAdapter(getApplicationContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private class ImagenAdapter extends BaseAdapter{
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tvtitulo;
        TextView tvdescripcion;
        Button AgregarCarrito;
        /*
        ArrayList Codigo=new ArrayList();
        ArrayList Titulo=new ArrayList();
        ArrayList Precio=new ArrayList();
        ArrayList Imagen=new ArrayList();
        ArrayList Tipo=new ArrayList();
        ArrayList Ingredientes=new ArrayList();
*/
        public ImagenAdapter(Context applicationContext) {
        ctx = applicationContext;
        layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

    }

        @Override
        public int getCount () {
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

            ViewGroup view =(ViewGroup) layoutInflater.inflate(R.layout.activity_mostrar__menu_item,null);
            smartImageView=(SmartImageView) view.findViewById(R.id.im1);
            tvtitulo= (TextView) view.findViewById(R.id.tvTitulo);
            tvdescripcion= (TextView) view.findViewById(R.id.tvDescripcion);
            AgregarCarrito=(Button) view.findViewById(R.id.btnAgregarCarrito);


            String urlfinal="http://bware.000webhostapp.com/ensaladas/"+imagen.get(position).toString();
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);
            tvtitulo.setText(titulo.get(position).toString());
            tvdescripcion.setText(precio.get(position).toString());

            AgregarCarrito.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.i("Mostrar_Menu_Activity","Carrito codigo "+codigo.get(position).toString());


                   com.chemapeva.saludyvida.Modelo.Menu menus=new com.chemapeva.saludyvida.Modelo.Menu();
                   menus.setCodigo(Integer.parseInt(codigo.get(position).toString()));
                   menus.setPrecio(Double.parseDouble(precio.get(position).toString()));
                   menus.setIngredientes(ingredientes.get(position).toString());
                   menus.setImagen(imagen.get(position).toString());
                   menus.setTipo(tipo.get(position).toString());
                   menus.setNombre(titulo.get(position).toString());
                   menus.setCantidad(Integer.parseInt(cantidad.get(position).toString()));
                   AgregarAlCarrito(menus);
                   /*
                    Codigo.add(codigo.get(position).toString());
                    Titulo.add(titulo.get(position).toString());
                    Precio.add(precio.get(position).toString());
                    Imagen.add(imagen.get(position).toString());
                    Ingredientes.add(ingredientes.get(position).toString());
                    Tipo.add(tipo.get(position).toString());

                    Intent i =new Intent(Mostrar_Menu_Activity.this,MostrarCarritoActivity.class);
                    i.putExtra("lista",Codigo);
                    i.putExtra("lista",Titulo);
                    i.putExtra("lista",Descripcion);
                    i.putExtra("lista",Imagen);
                    i.putExtra("lista",Ingredientes);
                    i.putExtra("lista",Tipo);

                    startActivity(i);
*/
                }
            });
            return view;
        }



    }

    public void AgregarAlCarrito(com.chemapeva.saludyvida.Modelo.Menu menusca) {
        int po;
        if(men.contains(menusca)){
            po=men.indexOf(menusca);
            Log.d("Mostrar_Menu_Activity","menu encontrado en: "+po+"");
            men.get(po).setCantidad(men.get(po).getCantidad()+1);
            Toast toast1 =Toast.makeText(getApplicationContext(),"El producto ya esta agregado al carrito",Toast.LENGTH_LONG);
            toast1.show();
        }else{
            men.add(menusca);
            HashSet hs = new HashSet();
            hs.addAll(men);
            men.clear();
            men.addAll(hs);
            Toast toast1 =Toast.makeText(getApplicationContext(),"Producto agregado al carrito",Toast.LENGTH_LONG);
            toast1.show();
        }


    }
    public void irCarrito(){
        Log.d("Mostrar_Menu_Activity","Estamos aki");
        Intent i = new Intent(Mostrar_Menu_Activity.this, MostrarCarritoActivity.class);
        i.putExtra("miLista", men);
        i.putExtra("usuario",username+"");
        i.putExtra("codigo",co+"");
        i.putExtra("correo",correo+"");
        startActivity(i);
    }
        /*
        try {
             // id = Integer.parseInt(();
        } catch (Exception e) {
            Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            return;
        }

        try {
            String URL = Util.URL_SRV + "carrito/guardar1";
            ClienteRest clienteRest = new ClienteRest(this);

            clienteRest.doPost(URL, id, SOLICITUD_GUARDAR_CLIENTE, true);
        }catch(Exception e){
        Util.showMensaje(this, R.string.msj_error_clienrest);
        e.printStackTrace();
    }
*/
    }
    /*
    private void confirmarAgregarProducto(int idm) {
        String pregunta = "Su producto a sido agregado al carrito";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                AgregarAlCarrito(idm);
                                // consultaIdEmpleado();
                                //guardarUbicacion();
                            }
                        })
                .show();
    }
    */


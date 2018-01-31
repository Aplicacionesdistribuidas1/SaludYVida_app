package com.chemapeva.saludyvida;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chemapeva.saludyvida.Modelo.Menu;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CarritoActivity extends AppCompatActivity {
    private ListView lvC;
    ArrayList codigo=new ArrayList();
    ArrayList titulo = new ArrayList();
    ArrayList descripcion = new ArrayList();
    ArrayList imagen = new ArrayList();

    String nombre, ima;
    Double precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        lvC = (ListView) findViewById(R.id.lvCarrito);
        codigo = (ArrayList) getIntent().getStringArrayListExtra("codigo");
        titulo = (ArrayList) getIntent().getStringArrayListExtra("nombre");
        descripcion = (ArrayList) getIntent().getStringArrayListExtra("precio");
        imagen = (ArrayList) getIntent().getStringArrayListExtra("imagen");



        descargarImagen();
    }

    public void descargarImagen() {
        //titulo.clear();
        //descripcion.clear();
        //imagen.clear();
        //codigo.clear();
        //tipo.clear();
        //ingredientes.clear();
        //Log.i("Peche","error"+titulo.get(0).toString());
        final ProgressDialog progreesdialog = new ProgressDialog(CarritoActivity.this);
        progreesdialog.setMessage("CargandoDatos...");
        progreesdialog.show();
/*
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://192.168.0.102:8080/marketbit1/srv/menus/menus", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {*/
                    progreesdialog.dismiss();
                   try{
                    /*

                       // JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < titulo.size(); i++) {
                            titulo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            descripcion.add(jsonArray.getJSONObject(i).getString("precio"));
                            imagen.add(jsonArray.getJSONObject(i).getString("imagen"));
                            codigo.add(jsonArray.getJSONObject(i).getString("codigo"));
                            tipo.add(jsonArray.getJSONObject(i).getString("tipo"));
                            ingredientes.add(jsonArray.getJSONObject(i).getString("ingredientes"));
                        }*/
                        lvC.setAdapter(new CarritoActivity.ImagenAdapter(getApplicationContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



    private class ImagenAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tvtitulo;
        TextView tvdescripcion,subtotal,total,iva;
        Double t,s,i;

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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup view =(ViewGroup) layoutInflater.inflate(R.layout.activity_carrito_item,null);
            smartImageView=(SmartImageView) view.findViewById(R.id.im2);
            tvtitulo= (TextView) view.findViewById(R.id.tvTitul);
            tvdescripcion= (TextView) view.findViewById(R.id.tvDescripcio);
            total=(TextView)findViewById(R.id.txtTotal);
            subtotal=(TextView)findViewById(R.id.txtSubtotal);
            iva=(TextView)findViewById(R.id.txtIva);
            t=Double.parseDouble(descripcion.get(position).toString());
            i=(t*0.14);
            s=t-i;
            Log.i("CarritoActivity","error"+titulo.get(position).toString());
            String urlfinal="http://bware.000webhostapp.com/ensaladas/"+imagen.get(position).toString();
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);
            tvtitulo.setText(titulo.get(position).toString());
            subtotal.setText(s.toString());
            iva.setText(i.toString());
            return view;
        }



    }

}


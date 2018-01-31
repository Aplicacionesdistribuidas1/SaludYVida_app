package com.chemapeva.saludyvida;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chemapeva.saludyvida.Modelo.Cliente;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crist on 11/01/2018.
 */

public class ActividadPrincipalCliente extends AppCompatActivity implements OnTaskCompleted {
    private static final int SOLICITUD_CLIENTES = 1;
    private static final int SOLICITUD_CLIENTE = 2;

    private ListAdapterClientes clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
  //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarCrearCliente();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        consultaListadoClientes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Realiza la llamada al WS para consultar el listado de clientes
     */
    protected void consultaListadoClientes() {
        try {
            String URL = Util.URL_SRV + "clientes/listar";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_CLIENTES, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    //  * Realiza la llamada al WS para consultar el listado de Categorias

    protected void consultaCliente(int id) {
        try {
            String URL = Util.URL_SRV + "clientes/listarid";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?id="+id, SOLICITUD_CLIENTE, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("Actividadcliente", "" + result);
        switch (idSolicitud){
            case SOLICITUD_CLIENTES:
                if(result!=null){
                    try {
                        List<Cliente> res =  ClienteRest.getResults(result, Cliente.class);
                        mostrarClientes(res);
                    }catch (Exception e){
                        Log.i("ActividadPrincipalClien", "Error en carga de clientes", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;

            case SOLICITUD_CLIENTE:
                if(result!=null){
                    try {
                        Cliente cli =  ClienteRest.getResult(result, Cliente.class);
                        Util.showMensaje(this, cli.toString());
                    }catch (Exception e){
                        Log.i("PrincipCLiente1", "Error en carga de cliente por ID", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }
    }

    public void mostrarClientes(List<Cliente> list){
        ListView lista = (ListView) findViewById(R.id.lstClientes);
        clientes = new ListAdapterClientes(getApplicationContext(), new ArrayList<Cliente>(list));
        lista.setAdapter(clientes);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                mostrarCliente(position);
            }
        });
    }

    private void mostrarCliente(int position){
        Cliente cli = clientes.getItem(position);
        consultaCliente(cli.getCodigo());
    }

    private void llamarCrearCliente(){
        Intent intent = new Intent(this, ClienteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //cierra cola de actividades
        startActivity(intent);
    }
}

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

import com.chemapeva.saludyvida.EmpleadoActivity;
import com.chemapeva.saludyvida.ListAdapterEmpleados;
import com.chemapeva.saludyvida.Modelo.Empleado;
import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity implements OnTaskCompleted {

    private static final int SOLICITUD_EMPLEADOS = 1;
    private static final int SOLICITUD_EMPLEADO = 2;

    private ListAdapterEmpleados empleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        consultaListadoEmpleados();
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
     * Realiza la llamada al WS para consultar el listado de Categorias
     */
    protected void consultaListadoEmpleados() {
        try {
            String URL = Util.URL_SRV + "empleados/listar";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_EMPLEADOS, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

   //  * Realiza la llamada al WS para consultar el listado de Categorias

    protected void consultaEmpleado(int id) {
        try {
            String URL = Util.URL_SRV + "empleados/listarid";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?id="+id, SOLICITUD_EMPLEADO, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity1", "" + result);
        switch (idSolicitud){
            case SOLICITUD_EMPLEADOS:
                if(result!=null){
                    try {
                        List<Empleado> res =  ClienteRest.getResults(result, Empleado.class);
                        mostrarEmpleados(res);
                    }catch (Exception e){
                        Log.i("MainActivity1", "Error en carga de categorias", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;

            case SOLICITUD_EMPLEADO:
                if(result!=null){
                    try {
                        Empleado emp =  ClienteRest.getResult(result, Empleado.class);
                        Util.showMensaje(this, emp.toString());
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

    public void mostrarEmpleados(List<Empleado> list){
        ListView lista = (ListView) findViewById(R.id.lstEmpleados);
        empleados = new ListAdapterEmpleados(getApplicationContext(), new ArrayList <Empleado>(list));
        lista.setAdapter(empleados);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                mostrarEmpleado(position);
            }
        });
    }

    private void mostrarEmpleado(int position){
        Empleado emp = empleados.getItem(position);
        consultaEmpleado(emp.getCodigo());
    }

    private void llamarCrearEmpleado(){
        Intent intent = new Intent(this, EmpleadoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //cierra cola de actividades
        startActivity(intent);
    }
}

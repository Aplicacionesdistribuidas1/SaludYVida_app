package com.chemapeva.saludyvida;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chemapeva.saludyvida.utilidades.ClienteRest;
import com.chemapeva.saludyvida.utilidades.OnTaskCompleted;
import com.chemapeva.saludyvida.utilidades.Util;

import com.chemapeva.saludyvida.Modelo.Empleado;
import com.chemapeva.saludyvida.Modelo.Respuesta;

public class EmpleadoActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted {

    private static final int SOLICITUD_GUARDAR_EMPLEADO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        ((Button) findViewById(R.id.btnGuardarEmp)).setOnClickListener(this);
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case SOLICITUD_GUARDAR_EMPLEADO:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(this, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            finish();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(this,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this,R.string.msj_error_clienrest);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardarEmp:
                confirmarGuardarEmpleado();
                break;
            default:
                break;
        }
    }

    /**
     * Procedimiento para validar y confirmar la transaccion,
     */
    private void confirmarGuardarEmpleado() {
        String pregunta = "Esto seguro de realizar guardar la categoria?";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarEmpleado();
                            }
                        })
                .show();
    }

    private void guardarEmpleado(){
        int id;
        try {
            id = Integer.parseInt(((EditText) findViewById(R.id.txtIdEmp)).getText().toString());
        }catch (Exception e){
            Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            return;
        }
        String nombre = ((EditText) findViewById(R.id.txtNombreEmp)).getText().toString();
        String usuario = ((EditText) findViewById(R.id.txtUsuarioEmp)).getText().toString();
        String clave = ((EditText) findViewById(R.id.txtContrasenaEmp)).getText().toString();
        String cargo = ((EditText) findViewById(R.id.txtCargoEmp)).getText().toString();
        String cedula = ((EditText) findViewById(R.id.txtCedulaEmp)).getText().toString();
        try {
            String URL = Util.URL_SRV + "empleados/guardar";
            ClienteRest clienteRest = new ClienteRest(this);
            Empleado emp = new Empleado();
            emp.setCodigo(id);
            emp.setNombre(nombre);
            emp.setCedula(cedula);
            emp.setUsuario(usuario);
            emp.setContrasena(clave);
            emp.setCargo(cargo);
            clienteRest.doPost(URL, emp, SOLICITUD_GUARDAR_EMPLEADO, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
}

package com.chemapeva.saludyvida.utilidades;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cris on 31/12/2017.
 */

public class Util {

    //URL del Sitio Web primario de los WS para la aplicacion
    public static final String URL_SRV = "http://saludyvida.cristiantimbi.info/marketbit1/srv/";

    /**
     * Permite mostrar un mensaje Toast en pantalla,
     * @param id    ID de recurso String.xml
     */
    public static void showMensaje(Context context, int id){
        String mensaje = context.getResources().getString(id);
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Permite mostrar un mensaje Toast en pantalla,
     * @param mensaje    Texto del mensaje a mostrar
     */
    public static void showMensaje(Context context, String mensaje){
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}

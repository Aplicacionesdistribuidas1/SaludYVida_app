package com.chemapeva.saludyvida;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chemapeva.saludyvida.Modelo.Empleado;

import java.util.ArrayList;


/**
 * Created by crist
 */
public class ListAdapterEmpleados extends ArrayAdapter<Empleado> {

    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterEmpleados(Context context, ArrayList<Empleado> items) {
        super(context, R.layout.item_empleado, items);
        this.context = context;
    }

    /**
     * View a presentar en pantalla correspondiente a un item de ListView
     *
     * @param position    //Indice del ListItem
     * @param convertView //Contexto o contenedor de View
     * @param parent      //Contendor padre
     * @return  Objeto View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_empleado, null);
        }

        //Objeto Empleado a visualizar segun position
        Empleado item = getItem(position);
        if (item != null) {
            // Recupera los elementos de vista y setea en funcion de valores de objeto
            TextView titulo = (TextView) view.findViewById(R.id.txtEmpleado);

            if (titulo != null) {
                String e=item.getCedula()+" "+ item.getNombre()+" "+item.getCargo();
                titulo.setText(e);
                //titulo.setText(item.getCedula());
                //titulo.setText(item.getUsuario());
                //titulo.setText(item.getCargo());
            }
        }
        return view;
    }
}

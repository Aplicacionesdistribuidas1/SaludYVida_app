package com.chemapeva.saludyvida;

/**
 * Created by crist on 24/05/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarkerNutricionistaJSONParser {

    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){

        JSONArray jDoctores = null;
        try {
            /** Retrieves all the elements in the 'markers' array */
            jDoctores = jObject.getJSONArray("Doctores");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getMarkers with the array of json object
         * where each json object represent a marker
         */
        return getMarkers(jDoctores);
    }


    private List<HashMap<String, String>> getMarkers(JSONArray jDoctores){
        int markersCount = jDoctores.length();
        List<HashMap<String, String>> DoctoresList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> Doctores = null;

        /** Taking each Doctores, parses and adds to list object */
        for(int i=0; i<markersCount;i++){
            try {
                /** Call getMarker with Doctores JSON object to parse the Doctores */
                Doctores = getMarker((JSONObject)jDoctores.get(i));
                DoctoresList.add(Doctores);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return DoctoresList;
    }

    /** Parsing the Marker JSON object */
    private HashMap<String, String> getMarker(JSONObject jDoctores){

        HashMap<String, String> Doctores = new HashMap<String, String>();
        String Latitud = "-NA-";
        String Longitud ="-NA-";
        String Nombre = "-NA-";
        String Direccion ="-NA-";

        try {
            // Extracting latitude, if available
            if(!jDoctores.isNull("Latitud")){
                Latitud = jDoctores.getString("Latitud");
            }

            // Extracting longitude, if available
            if(!jDoctores.isNull("Longitud")){
                Longitud = jDoctores.getString("Longitud");
            }
            if(!jDoctores.isNull("Nombre")){
                Nombre = jDoctores.getString("Nombre");
            }

            // Extracting longitude, if available
            if(!jDoctores.isNull("Direccion")){
                Direccion = jDoctores.getString("Direccion");
            }

            Doctores.put("Latitud", Latitud);
            Doctores.put("Longitud", Longitud);
            Doctores.put("Nombre", Nombre);
            Doctores.put("Direccion", Direccion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Doctores;
    }
}

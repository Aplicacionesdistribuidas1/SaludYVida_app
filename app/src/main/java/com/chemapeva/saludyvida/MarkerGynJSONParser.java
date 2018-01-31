package com.chemapeva.saludyvida;

/**
 * Created by crist on 20/12/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarkerGynJSONParser {

    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){

        JSONArray jGimnasios = null;
        try {
            /** Retrieves all the elements in the 'markers' array */
            jGimnasios = jObject.getJSONArray("Gimnasios");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getMarkers with the array of json object
         * where each json object represent a marker
         */
        return getMarkers(jGimnasios);
    }


    private List<HashMap<String, String>> getMarkers(JSONArray jGimnasios){
        int markersCount = jGimnasios.length();
        List<HashMap<String, String>> GimnasiosList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> Gimnasios = null;

        /** Taking each Gimnasios, parses and adds to list object */
        for(int i=0; i<markersCount;i++){
            try {
                /** Call getMarker with Gimnasios JSON object to parse the Gimnasios */
                Gimnasios = getMarker((JSONObject)jGimnasios.get(i));
                GimnasiosList.add(Gimnasios);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return GimnasiosList;
    }

    /** Parsing the Marker JSON object */
    private HashMap<String, String> getMarker(JSONObject jGimnasios){

        HashMap<String, String> Gimnasios = new HashMap<String, String>();
        String Latitud = "-NA-";
        String Longitud ="-NA-";
        String Nombre = "-NA-";
        String Direccion ="-NA-";

        try {
            // Extracting latitude, if available
            if(!jGimnasios.isNull("Latitud")){
                Latitud = jGimnasios.getString("Latitud");
            }

            // Extracting longitude, if available
            if(!jGimnasios.isNull("Longitud")){
                Longitud = jGimnasios.getString("Longitud");
            }
            if(!jGimnasios.isNull("Nombre")){
                Nombre = jGimnasios.getString("Nombre");
            }

            // Extracting longitude, if available
            if(!jGimnasios.isNull("Direccion")){
                Direccion = jGimnasios.getString("Direccion");
            }

            Gimnasios.put("Latitud", Latitud);
            Gimnasios.put("Longitud", Longitud);
            Gimnasios.put("Nombre", Nombre);
            Gimnasios.put("Direccion", Direccion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Gimnasios;
    }
}

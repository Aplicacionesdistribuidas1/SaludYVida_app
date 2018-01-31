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

public class MarkerJSONParser {

    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){

        JSONArray jParques = null;
        try {
            /** Retrieves all the elements in the 'markers' array */
            jParques = jObject.getJSONArray("Parques");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getMarkers with the array of json object
         * where each json object represent a marker
         */
        return getMarkers(jParques);
    }


    private List<HashMap<String, String>> getMarkers(JSONArray jParques){
        int markersCount = jParques.length();
        List<HashMap<String, String>> ParquesList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> Parques = null;

        /** Taking each Parques, parses and adds to list object */
        for(int i=0; i<markersCount;i++){
            try {
                /** Call getMarker with Parques JSON object to parse the Parques */
                Parques = getMarker((JSONObject)jParques.get(i));
                ParquesList.add(Parques);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return ParquesList;
    }

    /** Parsing the Marker JSON object */
    private HashMap<String, String> getMarker(JSONObject jParques){

        HashMap<String, String> Parques = new HashMap<String, String>();
        String Latitud = "-NA-";
        String Longitud ="-NA-";
        String Nombre = "-NA-";
        String Direccion ="-NA-";

        try {
            // Extracting latitude, if available
            if(!jParques.isNull("Latitud")){
                Latitud = jParques.getString("Latitud");
            }

            // Extracting longitude, if available
            if(!jParques.isNull("Longitud")){
                Longitud = jParques.getString("Longitud");
            }
            if(!jParques.isNull("Nombre")){
                Nombre = jParques.getString("Nombre");
            }

            // Extracting longitude, if available
            if(!jParques.isNull("Direccion")){
                Direccion = jParques.getString("Direccion");
            }

            Parques.put("Latitud", Latitud);
            Parques.put("Longitud", Longitud);
            Parques.put("Nombre", Nombre);
            Parques.put("Direccion", Direccion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Parques;
    }
}

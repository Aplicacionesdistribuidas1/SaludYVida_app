package com.chemapeva.saludyvida;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecuperacionLo extends Activity {
    Button acep;
    EditText pre1;
    EditText pre2;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacionlo);
        pre1=(EditText) findViewById(R.id.txtPre1);
        pre2=(EditText) findViewById(R.id.txtPre2);
        acep=(Button) findViewById(R.id.btnA);

        acep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pregunta1 = pre1.getText().toString();
                String Pregunta2 = pre2.getText().toString();
                String Correo = getIntent().getExtras().getString("Correo");
        if (Pregunta1.isEmpty() || Pregunta2.isEmpty()) {
            Toast.makeText(RecuperacionLo.this, "Las respuestas no coinciden", Toast.LENGTH_LONG).show();

        } else {

            new AsyncLogin().execute(Correo,Pregunta1,Pregunta2);

        }
    }
});
        }


    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(RecuperacionLo.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://bware.000webhostapp.com/recuperarcontrasena.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("Correo", params[0])
                        .appendQueryParameter("Pregunta1", params[1])
                        .appendQueryParameter("Pregunta2", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());
                    //return(u.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            Log.d("Result",result+"");
            String cs =result;
            String[] separated = cs.split(",");
            String r=separated[0];
            String u=separated[1];
            String c=separated[2];
            //String n=separated[3];
          //  Log.d("Result",c);
           //

            if(r.equalsIgnoreCase("true"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecuperacionLo.this);
                builder.setTitle("Recuperacion de contrasena")
                        .setMessage("Sus datos de  login son:\n Login: "+u+" Contraseña: "+c)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(RecuperacionLo.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                finish();
                                startActivity(i);

                            }

                        })
                        .create()
                        .show();
            }else if (result.equalsIgnoreCase("false")){
                // If username and password does not match display a error message
                Toast.makeText(RecuperacionLo.this, "La respuestas no coinciden", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(RecuperacionLo.this, "OOPs!  Problemas de conexion.", Toast.LENGTH_LONG).show();

            }
        }

    }
    private void Notificacion(String username, String password){
        //Toast.makeText(RecuperacionLo.this, username+" "+password , Toast.LENGTH_LONG).show();


                        //RecuperacionLo.this.finish();


    }
}

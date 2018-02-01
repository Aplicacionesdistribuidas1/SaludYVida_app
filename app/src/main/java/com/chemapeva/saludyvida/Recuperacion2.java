package com.chemapeva.saludyvida;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recuperacion2 extends Activity {
    Button acep;
    EditText pre1, pre2;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    // JSONParser jsonParser = new JSONParser();
    json jsonParser = new json();

    //si lo trabajan de manera local en xxx.xxx.x.x va su ip local
    // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/cas/register.php";

    //testing on Emulator:
    private static String URL = "https://bware.000webhostapp.com/Re.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion2);
        pre1 = (EditText) findViewById(R.id.txtPre1);
        pre2 = (EditText) findViewById(R.id.txtPre2);
        acep = (Button) findViewById(R.id.btnA);
        acep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // registerUser();
                //          new CreateUser().execute();
                String Nombre = getIntent().getExtras().getString("Nombre");
                ;
                String Apellidos = getIntent().getExtras().getString("Apellidos");
                ;
                String Correo = getIntent().getExtras().getString("Correo");
                ;
                String username = getIntent().getExtras().getString("username");
                ;
                String password = getIntent().getExtras().getString("password");
                String Pregunta1 = pre1.getText().toString();
                String Pregunta2 = pre2.getText().toString();
                Log.d("Valores",Nombre+" "+Apellidos+" "+Correo+" "+username+" "+password+" "+Pregunta1+" "+Pregunta2);

               new AttemptLogin().execute(Nombre, Apellidos, Correo, username, password, Pregunta1, Pregunta2);
            }
        });
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String Nombre = args[0];
            String Apellidos = args[1];
            String Correo = args[2];
            String username = args[3];
            String password = args[4];
            String Pregunta1 = args[5];
            String Pregunta2 = args[6];
            Log.d("Valores1",Nombre+" "+Apellidos+" "+Correo+" "+username+" "+password+" "+Pregunta1+" "+Pregunta2);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Nombre", Nombre));
            params.add(new BasicNameValuePair("Apellidos", Apellidos));
            params.add(new BasicNameValuePair("Correo", Correo));

            params.add(new BasicNameValuePair("username",username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("Pregunta1", Pregunta1));
            params.add(new BasicNameValuePair("Pregunta2", Pregunta2));
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            Log.d("res",json+"");


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    if(result.getString("success").equals("1")){
                        Intent intent = new Intent(Recuperacion2.this,MainActivity.class);
                        startActivity(intent);
                        // intent.putExtra("username", username);
                        Recuperacion2.this.finish();
                    }else{
                       
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se pueden recuperar los datos del servidor", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}






package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResponsableListaHallazgos extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView titular_session;
    String Planta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable_lista_hallazgos);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Hallazgos Abiertos");
        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        Planta= preferences.getString("Planta","No existe Planta");
        String numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");
        titular_session=(TextView)findViewById(R.id.textViewSession);
        titular_session.setText("Responsable:  "+nombreAuditor+" ("+numeroNomina+") "+Planta);
        //buscarHallazgos("https://vvnorth.com/5sGhoner/",numeroNomina);
    }

    private void buscarHallazgos(String URL, final String numeroNomina)
    {
        StringRequest jsonArrayRequest= new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)

            public void onResponse(String response) {
                Log.e("Consultado Plantas","Planta"+response);

                try {
                    JSONArray arrayRespuesta = new JSONArray(response);
                    for (int i = 0; i < arrayRespuesta.length(); i++) {
                        JSONObject jsonObjectPlantas = arrayRespuesta.getJSONObject(i);

                        String planta = jsonObjectPlantas.getString("Planta");
                       // boton(planta, numeroNomina);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("NumeroNomina", numeroNomina);
                params.put("Planta", Planta);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
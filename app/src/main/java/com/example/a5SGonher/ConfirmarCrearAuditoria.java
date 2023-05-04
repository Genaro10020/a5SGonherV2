package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ConfirmarCrearAuditoria extends AppCompatActivity {
    String numeroNomina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_crear_auditoria);
        TextView dato = (TextView)findViewById(R.id.mostrandoR);
        final String recibiendosubArea = getIntent().getStringExtra("subArea");
        dato.setText("Crear auditoría en "+recibiendosubArea+" ?");

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        numeroNomina = preferences.getString("NumeroNomina","No existe Número de nómina");
        Log.e("Numoer",":"+numeroNomina);

        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Crear Auditoría");

        TextView btncrear = (TextView)findViewById(R.id.text_crear);
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearAuditoria("https://vvnorth.com/5sGhoner/crearAuditoria_v2.php",recibiendosubArea);

            }
        });
    }


    public void cancelar(View view){
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }

        private void crearAuditoria(String URL, final String recibiendosubArea){
            StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);
                    Toast.makeText(getApplicationContext(),"Creada con éxito",Toast.LENGTH_SHORT).show();
                    retornaraMenu();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parametros =new HashMap<String,String>();
                    parametros.put("NombreSubArea",recibiendosubArea);
                    parametros.put("NumeroNomina",numeroNomina);
                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


    public void retornaraMenu(){

        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);

    }
}
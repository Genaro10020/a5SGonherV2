package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AgregarArea extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView tView, titulo_barra;
    String numeroNomina, Planta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        //numeroNomina = getIntent().getStringExtra("NUMERO_NOMINA_AUDITOR");

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        numeroNomina = preferences.getString("NumeroNomina", "No existe número de nómina");
        Planta = preferences.getString("Planta", "No existe número de Planta");
        Log.e("Planta",":" +Planta);


        setContentView(R.layout.activity_agregar_area);
        tView=(TextView)findViewById(R.id.textViewArea);
        tView.setText("Planta: "+nombrePlanta+" / Seleccione Área");
        titulo_barra = (TextView)findViewById(R.id.titulo_toolbar);
        titulo_barra.setText("Crear Auditoría");


       buscarProducto("https://vvnorth.com/buscar_area.php?Nomina="+numeroNomina+"&Planta="+Planta+"",nombrePlanta);
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent =new Intent(this,AgregarPlanta.class);
        startActivity(intent);
    }

    public void Cambiar(final String nombrePlanta)
    {
        Intent intent =new Intent(this,MenuCambioArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }
    public void Eliminar(final String nombrePlanta)
    {
        Intent intent =new Intent(this,EliminarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }
    public void formularioArea(String nPlanta)
    {
        Intent intent =new Intent(this,FormularioArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nPlanta);

        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void boton(final String nombreArea, int numeroEmpresa, final String nombrePlanta)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreArea);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutHallazgosR);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        Typeface fuenteitem = getResources().getFont(R.font.mitre);
        myButton3.setTypeface(fuenteitem);
        //myButton3.setBackgroundColor(Color.rgb(150, 0, 0));
        myButton3.setBackgroundResource(R.drawable.nuevos_botones_listado);
        myButton3.setTextColor(Color.rgb(236, 236, 236));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                agregarS(nombrePlanta,nombreArea);

            }
        });
    }
    public void agregarS(String nombrePlanta,String nArea)
    {
        Intent intent =new Intent(this,AgregarSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("NUMERO_NOMINA_AUDITOR",numeroNomina);

        startActivity(intent);
    }


    private void buscarProducto(String URL,final String nombrePlanta)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String area;
                        jsonObject = response.getJSONObject(i);
                        Log.e("Consultado","Areas: "+response);
                        // editT.setText(jsonObject.getString("Planta"));
                        area=jsonObject.getString("Area");
                       boton(area,i,nombrePlanta);


                    } catch (JSONException e) {
                    //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}

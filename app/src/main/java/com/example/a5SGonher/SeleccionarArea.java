package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class SeleccionarArea extends AppCompatActivity {


    RequestQueue requestQueue;
    TextView tView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        setContentView(R.layout.activity_agregar_area);
        tView=(TextView)findViewById(R.id.textViewArea);

        tView.setText(nombrePlanta);


        buscarProducto("https://vvnorth.com/buscar_area.php?Planta="+nombrePlanta +"",nombrePlanta);
    }
    public void formularioArea(String nPlanta)
    {
        Intent intent =new Intent(this,FormularioArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nPlanta);

        startActivity(intent);
    }
    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutHallazgosR);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        myButton3.setBackgroundColor(Color.rgb(70, 80, 90));
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                agregarS(nombrePlanta,nombreBoton);

            }
        });
    }
    public void agregarS(String nombrePlanta,String nArea)
    {
        Intent intent =new Intent(this,SeleccionarS.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }
    private void buscarProducto(String URL,final String nombrePlanta)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("NombreArea");
                        boton(nombre,i,nombrePlanta);


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

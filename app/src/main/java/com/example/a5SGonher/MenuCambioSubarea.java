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

public class MenuCambioSubarea extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView tView,tView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cambio_subarea);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
      //  tView=(TextView)findViewById(R.id.textvSPlanta);
       // tView2=(TextView)findViewById(R.id.textvSArea);

       // tView.setText(nombrePlanta);
      //  tView2.setText(nombreArea);




        buscarProducto("https://vvnorth.com/buscar_subarea.php?area="+nombreArea +"&planta="+ nombrePlanta+"" ,nombrePlanta,nombreArea);

    }

    public void Cambiar(final String nombrePlanta,final String nombreArea)
    {
        Intent intent =new Intent(this,MenuCambioSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
        startActivity(intent);
    }
    public void Eliminar(final String nombrePlanta,final String nArea)
    {
        Intent intent =new Intent(this,EliminarSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }

    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea)
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
                        nombre=jsonObject.getString("SubArea");
                        boton(nombre,i,nombrePlanta,nombreArea);


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

    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                agregarS(nombrePlanta,nombreArea,nombreBoton);

            }
        });
    }
    public void agregarS(String nombrePlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,CambioSubarea2.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        startActivity(intent);
    }
    public void formularioS(String nPlanta,String nArea)
    {
        Intent intent =new Intent(this,FormularioSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID", nPlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);


        startActivity(intent);
    }
}

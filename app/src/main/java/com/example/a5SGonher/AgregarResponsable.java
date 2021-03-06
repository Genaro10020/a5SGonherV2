package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AgregarResponsable extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText editT;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_responsable);
        // editT=(EditText)findViewById(R.id.editT);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton = new Button(this);
        myButton.setText("Agregar Responsable");

        LinearLayout ll = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.addView(myButton, lp);
        /////////////////////////
        myButton.setBackgroundColor(Color.rgb(108, 31, 31));
        myButton.setTextColor(Color.rgb(179, 179, 179));
      lp.setMargins(0, 0, 0, 10);
        myButton.setLayoutParams( lp);
        /////////////////////////////////////////

        // myButton = ((Button) findViewById(id_));
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                formularioPlanta();

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton2 = new Button(this);
        myButton2.setText("Eliminar Responable");

        LinearLayout ll2 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll2.addView(myButton2, lp2);

        myButton2.setBackgroundColor(Color.rgb(108, 31, 31));
        myButton2.setTextColor(Color.rgb(179, 179, 179));
        lp2.setMargins(0, 0, 0, 10);
        myButton2.setLayoutParams( lp2);

        myButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EliminarUsuario();

            }
        });

        ////////////////////////////////////////////////////////////////



        buscarProducto("https://vvnorth.com/buscar_responsable.php");

        //////////////////////////////
    }

    public void boton(final String nombreBoton, int numeroEmpresa, final String nombreAuditor)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreAuditor+" "+nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(150, 0, 0));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                //  agregarArea(nombreBoton);
                CambiarResponsable(nombreBoton);

            }
        });
    }

    public void CambiarResponsable(final String nombreUsuario)
    {
        Intent intent =new Intent(this,CamboarResponsable.class);
        intent.putExtra("EXTRA_SESSION_ID", nombreUsuario);
        startActivity(intent);


    }
    public void EliminarUsuario()
    {
        Intent intent =new Intent(this,EliminarResponsable.class);
        startActivity(intent);
    }

    public void formularioPlanta()
    {
        Intent intent =new Intent(this,FormularioResponsable.class);
        startActivity(intent);
    }

    public void agregarArea(String nombrePlanta)
    {
        Intent intent =new Intent(this,AgregarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent =new Intent(this,AjustesMenu.class);
        startActivity(intent);
    }

    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        String nombreAuditor;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("auditor");
                        nombreAuditor=jsonObject.getString("NombreAuditor");
                        boton(nombre,i,nombreAuditor);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
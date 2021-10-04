package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarSubarea extends AppCompatActivity  implements ExampleDialog.ExampleDialogListener {
    RequestQueue requestQueue;
    TextView tView,tView2;
    String DeleteThis;
    String GNombrePlanta;
    String GNombreArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_subarea);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        tView=(TextView)findViewById(R.id.textvSPlanta);
       // tView2=(TextView)findViewById(R.id.textvSArea);

        tView.setText("Eliminar Subarea");
        //tView2.setText(nombreArea);




        buscarProducto("https://vvnorth.com/buscar_subarea.php?area="+nombreArea +"&planta="+ nombrePlanta+"" ,nombrePlanta,nombreArea);

    }


    private void ejecutarservicio(String URL,final String nombreBoton)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
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
                // parametros.put("NombrePlanta",edtEmpresa.getText().toString());
                parametros.put("NombreSubArea",nombreBoton);
                //parametros.put("NMail",edtMail.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

                DeleteThis=nombreBoton;
                GNombreArea=nombreArea;
                GNombrePlanta=nombrePlanta;
                OpenDialog();


            }
        });
    }
    public void Eliminar(final String nombrePlanta,final String nArea)
    {
        Intent intent =new Intent(this,AgregarSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }

    public void agregarS(String nombrePlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,AgregarS.class);
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
    public void OpenDialog( )
    {

        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(),"example dialog");

    }


    @Override
    public void onYesClicked() {
        ejecutarservicio("https://vvnorth.com/eliminar_subarea.php",DeleteThis);
        Eliminar(GNombrePlanta,GNombreArea);


    }
}

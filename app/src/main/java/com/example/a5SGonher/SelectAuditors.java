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

public class SelectAuditors extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText editT;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_auditors);
        // editT=(EditText)findViewById(R.id.editT);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombreSubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        final String nombreS = getIntent().getStringExtra("EXTRA_SESSION_ID4");



        buscarProducto("https://vvnorth.com/buscar_auditor.php",nombreSubArea,nombrePlanta,nombreArea);

        //////////////////////////////
    }

    public void boton(final String nombreBoton,int numeroEmpres, final String nombreS, final String nombrePlanta , final String nombreArea,final String nombreAuditor)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreAuditor + " " + nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        myButton3.setBackgroundColor(Color.rgb(70, 80, 90));
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {


              //  ejecutarservicio("http://192.168.15.3:8080/Ghoner/insertar_imagens.php");
                ejecutarservicio("https://vvnorth.com/insertar_imagens.php",nombreBoton,nombreS,nombrePlanta,nombreArea);
              //  agregarArea(nombreBoton);

            }
        });
    }

    public void RegresarAS(String nombrePlanta,String nombreArea,String nombreSubArea)
    {
        Intent intent =new Intent(this,AgregarS.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
        intent.putExtra("EXTRA_SESSION_ID3", nombreSubArea);
        startActivity(intent);
    }
    public void formularioPlanta()
    {
        Intent intent =new Intent(this,FormularioUsuario.class);
        startActivity(intent);
    }

    public void agregarArea(String nombrePlanta)
    {
        Intent intent =new Intent(this,AgregarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }

    private void buscarProducto(String URL,final String nombreS,final String nombrePlanta,final String nombreArea)
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
                        boton(nombre,i,nombreS,nombrePlanta,nombreArea,nombreAuditor);


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

    private void ejecutarservicio(String URL,final String nombreBoton, final String nombreS,final String nombrePlanta,final String nombreArea)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ejecutarservicio2("https://vvnorth.com/CrearAuditoria.php",nombreS,nombreBoton);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
            //    parametros.put("ss",edtRquermiento.getText().toString());
             //   parametros.put("datos",edtDatos.getText().toString());
             //  parametros.put("nombrea","Prueba");
               // parametros.put("nombres","Pruebita");
                parametros.put("NombreS",nombreS);
                parametros.put("NombreAuditor",nombreBoton);
                parametros.put("NombrePlanta",nombrePlanta);
                parametros.put("NombreArea",nombreArea);

              //  parametros.put("NombrenombreArea",NSubArea);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ejecutarservicio2(String URL, final String nombreSubarea,final String user)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Dialog7 dialog7 = new Dialog7();
                dialog7.show(getSupportFragmentManager(),"example Dialog2");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                parametros.put("NombrePlanta",nombreSubarea);
                parametros.put("Usuario",user);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
package com.example.a5SGonher.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.a5SGonher.DialogOptions3;
import com.example.a5SGonher.FormularioUsuario;
import com.example.a5SGonher.MainMenu;
import com.example.a5SGonher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListosEnviar extends AppCompatActivity implements DialogOptions3.DialogOptions1Listener{
    RequestQueue requestQueue;
    EditText editT;
    String numeroAuditoria;
    String GlobalSubarea;
    String GlobalUser;
    String GlobalNumeroAuditoria;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listos_enviar);
        // editT=(EditText)findViewById(R.id.editT);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        GlobalUser=user;

        buscarProducto("https://vvnorth.com/buscar_auditoriasN.php?Auditor=" + user + "",user);

        //////////////////////////////
    }
    private void ejecutarservicio(String URL,final String numeroAudi)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              ejecutarservicio2("https://vvnorth.com/excel/mailBien.php",GlobalNumeroAuditoria);
            //  ejecutarservicio2("https://vvnorth.com/excel/mailBienAuditor.php",GlobalNumeroAuditoria);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
            parametros.put("NumeroAuditoria",numeroAudi);
                GlobalNumeroAuditoria=numeroAudi;


                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ejecutarservicio2(String URL, final String numeroAudi)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);    CREO QUE NO SIRVE
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                parametros.put("SubArea",GlobalSubarea);
                parametros.put("User",GlobalUser);
                parametros.put("NumeroAuditoria",numeroAudi);

                //   parametros.put("NombrePlanta",NPlanta);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openDialog()
    {
        DialogOptions3 dialogOptions3 = new DialogOptions3();
        dialogOptions3.show(getSupportFragmentManager(),"example Dialog2");

    }

    @Override
    public void onYesClicked() {

        ejecutarservicio("https://vvnorth.com/excel/excel2.php",numeroAuditoria);
       // agregarArea();

    }

    @Override
    public void onNoClicked() {
    }
    public void onBackPressed() {
        Intent intent =new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    public void boton(final String nombrePlanta, final String nombreArea,final String nombreSubarea,final String nombreAuditor,final String user,final String nombreSub,final String calificacion)
    {
        Button myButton3 = new Button(this);
        if(nombreSubarea.equals("100")) {
            myButton3.setText(user + "      " + nombreSubarea + "% Terminado"+" Calificación=" +calificacion);

            LinearLayout ll3 = (LinearLayout) findViewById(R.id.layoutSPlanta);
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll3.addView(myButton3, lp3);

            /////////////////////////
            myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
            myButton3.setTextColor(Color.rgb(179, 179, 179));
            lp3.setMargins(0, 0, 0, 10);
            myButton3.setLayoutParams(lp3);
            /////////////////////////////////////////
            myButton3.setOnClickListener(new View.OnClickListener() {
                // String NomPlanta =nFinal;
                public void onClick(View view) {
                    numeroAuditoria=user;
                    GlobalSubarea=  nombreSub;
                    //      ejecutarservicio("https://vvnorth.com/CrearAuditoria.php",user,user);       SI ALGO SALE MAL PUEDE SER PORQUE COMENTE ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                 //   agregarArea(nombrePlanta, nombreArea, user, nombreAuditor);
                    openDialog();

                }
            });

        }
    }

    public void formularioPlanta()
    {
        Intent intent =new Intent(this, FormularioUsuario.class);
        startActivity(intent);
    }
    private void buscarProducto2(String URL, final String user,final String nombreSubarea)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        String calificacion;
                        String nombreAuditor="nada";
                      //  String nombreSubarea="nada";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        int valor;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("porcentaje");
                        calificacion=jsonObject.getString("Calificacion");

                        boton(nombrePlanta,nombreArea,nombre,nombreAuditor,user,nombreSubarea,calificacion);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarProducto3(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String calificacion;
                        String nombreAuditor="nada";
                        //  String nombreSubarea="nada";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        int valor;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        calificacion=jsonObject.getString("porcentaje");

                       // boton(nombrePlanta,nombreArea,nombre,nombreAuditor,user,nombreSubarea);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void agregarArea()
    {
        Intent intent =new Intent(this, MainMenu.class);

        startActivity(intent);
    }

    private void buscarProducto(String URL, final String user)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombreAuditor="nada";
                        String nombreSubarea="nada";
                        String nombreSubarea2="nadita";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombreSubarea=jsonObject.getString("CodigoAuditoria");
                        nombreSubarea2=jsonObject.getString("SubArea");

                        buscarProducto2("https://vvnorth.com/porcentaje_auditoria.php?numeroAuditoria="+nombreSubarea +"&planta="+ nombreSubarea+"&subarea="+ nombreSubarea +"&Estado="+"1" + "",nombreSubarea,nombreSubarea2);



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
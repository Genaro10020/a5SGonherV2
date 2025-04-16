package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class AuAudit extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText editT;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_au_audit);
        // editT=(EditText)findViewById(R.id.editT);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");



        buscarProducto("https://vvnorth.com/buscar_auditor2.php?Auditor=" + user + "",user);

        //////////////////////////////
    }
    private void ejecutarservicio(String URL, final String nombreSubarea,final String user)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
              parametros.put("NombrePlanta",nombreSubarea);
                parametros.put("Usuario",user);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void boton(final String nombrePlanta, final String nombreArea,final String nombreSubarea,final String nombreAuditor,final String user)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreSubarea);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
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

                ejecutarservicio("https://vvnorth.com/CrearAuditoria.php",nombreSubarea,user);
               agregarArea(nombrePlanta,nombreArea,nombreSubarea,nombreAuditor);

            }
        });
    }
    public void formularioPlanta()
    {
        Intent intent =new Intent(this,FormularioUsuario.class);
        startActivity(intent);
    }

    public void agregarArea(String nombrePlanta,String nombreArea,String nombreSubarea,String nombreAuditor)
    {
        Intent intent =new Intent(this,MenuAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
        intent.putExtra("EXTRA_SESSION_ID3", nombreSubarea);
        intent.putExtra("EXTRA_SESSION_ID4", nombreAuditor);
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
                        String nombreAuditor;
                        String nombreSubarea;
                        String nombreArea;
                        String nombrePlanta;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombrePlanta=jsonObject.getString("Planta");
                        nombreArea=jsonObject.getString("Area");
                        nombreSubarea=jsonObject.getString("SubArea");
                        nombreAuditor=jsonObject.getString("Auditor");

                        boton(nombrePlanta,nombreArea,nombreSubarea,nombreAuditor,user);


                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("Catcado:",e.getMessage());
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
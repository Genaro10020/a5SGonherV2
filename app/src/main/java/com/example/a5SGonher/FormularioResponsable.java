package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class FormularioResponsable extends AppCompatActivity implements DialogOptions5.DialogOptions1Listener{
    Button btnAgregar;
    EditText edtEmpresa,edtPassword,edtMail,edtNombre;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_responsable);
        btnAgregar=(Button) findViewById(R.id.botonFPlanta);
        edtEmpresa=(EditText)findViewById(R.id.empresa);
        edtPassword=(EditText)findViewById(R.id.password);
        edtMail=(EditText)findViewById(R.id.Mail);
        edtNombre=(EditText)findViewById(R.id.nombreAuditor);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("https://vvnorth.com/insertar_responsable.php");

            }
        });

    }
    public void openActivity(final String estado)
    {
        if(estado.equals("true"))
        {
            DialogOptions5 dialogOptions5 = new DialogOptions5();
            dialogOptions5.show(getSupportFragmentManager(),"example Dialog2");
        }
        else{
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(),"example dialog");

        }

    }

    public void onYesClicked() {
        Intent intent =new Intent(this,AgregarResponsable.class);

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
                        String estado;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        estado=jsonObject.getString("estado");

                      openActivity(estado);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void ejecutarservicio(String URL)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                buscarProducto("https://vvnorth.com/comparacion_auditorf.php");
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
                parametros.put("NombrePlanta",edtEmpresa.getText().toString());
                parametros.put("NPassword",edtPassword.getText().toString());
                parametros.put("NMail",edtMail.getText().toString());
                parametros.put("NombreAuditor",edtNombre.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





}

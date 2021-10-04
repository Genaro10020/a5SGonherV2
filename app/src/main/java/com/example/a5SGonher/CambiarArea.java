package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CambiarArea extends AppCompatActivity {
    TextView tView;
    Button btnAgregarArea;
    EditText edtArea;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_area);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        tView=(TextView)findViewById(R.id.nombreArea);
        tView.setText(nombreArea);
        btnAgregarArea=(Button) findViewById(R.id.botonFormularioArea);
        edtArea=(EditText)findViewById(R.id.textArea);

        btnAgregarArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("https://vvnorth.com/cambiar_area.php",nombrePlanta,nombreArea);
                //   openActivity(nombrePlanta);
            }
        });

    }

    public void openActivity(final String nombrePlanta,final String estado)
    {

        if(estado.equals("true"))
        {

            Intent intent =new Intent(this,AgregarArea.class);
            intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
            startActivity(intent);
        }
        else{
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(),"example dialog");

        }

    }
    private void buscarProducto(String URL,final String nombrePlanta)
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

                        openActivity(nombrePlanta,estado);


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

    private void ejecutarservicio(String URL,final String NPlanta,final String cambio)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);
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
                parametros.put("NombreArea",edtArea.getText().toString());
                parametros.put("NombrePlanta",NPlanta);
              parametros.put("Cambio",cambio);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}


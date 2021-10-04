package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

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

public class Pruebas extends AppCompatActivity {
    EditText edtCodig,edtProduct,edtPreci,edtFabricant;
    Button btnAgreg;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        edtCodig=(EditText)findViewById(R.id.edtCodig);
        edtProduct=(EditText)findViewById(R.id.edtProduct);
        edtPreci=(EditText)findViewById(R.id.edtPreci);
        edtFabricant=(EditText)findViewById(R.id.edtFabricant);
        btnAgreg=(Button) findViewById(R.id.btnAgreg);


        btnAgreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ejecutarservicio("http://192.168.15.8:8080/developeru/insertar_producto.php");
                buscarProducto("https://vvnorth.com/developeru/buscar_producto.php?codigo=0");

            }
        });
    }
    private void ejecutarservicio(String URL)
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
                parametros.put("codigo",edtCodig.getText().toString());
                parametros.put("producto",edtProduct.getText().toString());
                parametros.put("precio",edtPreci.getText().toString());
                parametros.put("fabricante",edtFabricant.getText().toString());
                return parametros;
            }
        };
         requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscarProducto(String URL)
    {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(3);
                        edtProduct.setText(jsonObject.getString("producto"));
                        edtPreci.setText(jsonObject.getString("precio"));
                        edtFabricant.setText(jsonObject.getString("fabricante"));

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
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}

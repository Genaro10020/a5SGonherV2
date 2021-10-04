package com.example.a5SGonher.ui.main;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a5SGonher.CustomAuditoria;
import com.example.a5SGonher.R;

import java.util.HashMap;
import java.util.Map;

public class CrearAuditoria extends AppCompatActivity {
    EditText edtAuditoria,edtDatos;

    Button btnAgregarS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_auditoria);


        edtAuditoria=(EditText)findViewById(R.id.editTextAudi);
        btnAgregarS=(Button) findViewById(R.id.buttonAudi35);

        btnAgregarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarservicio("https://vvnorth.com/insertar_AuditoriaCustom.php");
                CrearAuditoria();

               // Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void CrearAuditoria()
    {
        Intent intent =new Intent(this, CustomAuditoria.class);
       intent.putExtra("EXTRA_SESSION_ID", edtAuditoria.getText().toString());
       // intent.putExtra("EXTRA_SESSION_ID", "hola");
        startActivity(intent);

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




                parametros.put("datos", edtAuditoria.getText().toString());

              //  parametros.put("NombreAreaSS",strI);




                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
